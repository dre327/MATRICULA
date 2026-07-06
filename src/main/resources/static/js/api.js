// ══════════════════════════════════════════════════════════════════
//  api.js — cliente HTTP + gestión de sesión + permisos
// ══════════════════════════════════════════════════════════════════

const BASE = '/api';

// ── UTILIDAD: leer cookie por nombre ─────────────────────────────
function getCookie(name) {
    const match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
    return match ? decodeURIComponent(match[2]) : null;
}

// ── CLIENTE HTTP ─────────────────────────────────────────────────
// - credentials:'include' → manda cookies (JSESSIONID + XSRF-TOKEN).
// - Header X-XSRF-TOKEN → requerido por Spring Security en mutaciones.
// - Handlers globales de 401/403/409/400.
async function request(method, url, body) {
    const headers = { 'Accept': 'application/json' };
    if (body !== undefined) headers['Content-Type'] = 'application/json';

    // CSRF token para mutaciones
    if (['POST', 'PUT', 'DELETE', 'PATCH'].includes(method)) {
        const xsrf = getCookie('XSRF-TOKEN');
        if (xsrf) headers['X-XSRF-TOKEN'] = xsrf;
    }

    const res = await fetch(BASE + url, {
        method,
        credentials: 'include',
        headers,
        body: body !== undefined ? JSON.stringify(body) : undefined
    });

    // ── 204 No Content ──
    if (res.status === 204) return null;

    // ── OK 2xx ──
    if (res.ok) {
        return res.json().catch(() => ({}));
    }

    // ── Errores ──
    const err = await res.json().catch(() => ({ error: 'DESCONOCIDO', message: 'Error inesperado' }));

    // 401: sesión caducada / no autenticado → login
    if (res.status === 401) {
        session.clearCache();
        if (!location.pathname.endsWith('login.html')) {
            toast('Sesión expirada, redirigiendo…', 'error');
            setTimeout(() => location.href = 'login.html', 800);
        }
        throw new HttpError(res.status, err);
    }

    // 403: sin permiso
    if (res.status === 403) {
        toast(err.message || 'No tiene permiso para esta operación', 'error');
        throw new HttpError(res.status, err);
    }

    // 409: optimistic lock → obligar recarga
    if (res.status === 409) {
        alert(err.message || 'El registro fue modificado por otro usuario. Se recargarán los datos.');
        location.reload();
        throw new HttpError(res.status, err);
    }

    // 400 de validación: mostrar los campos
    if (res.status === 400 && err.error === 'VALIDACION' && err.campos) {
        const detalles = Object.entries(err.campos)
            .map(([campo, msg]) => `${campo}: ${msg}`)
            .join(' · ');
        toast(err.message + ' — ' + detalles, 'error');
        throw new HttpError(res.status, err);
    }

    // Cualquier otro error: mostrar el mensaje del backend
    toast(err.message || 'Error en la operación', 'error');
    throw new HttpError(res.status, err);
}

class HttpError extends Error {
    constructor(status, payload) {
        super(payload?.message || 'HTTP ' + status);
        this.status = status;
        this.payload = payload;
    }
}

const api = {
    get:  (url)        => request('GET',    url),
    post: (url, body)  => request('POST',   url, body ?? {}),
    put:  (url, body)  => request('PUT',    url, body ?? {}),
    del:  (url)        => request('DELETE', url)
};

// ══════════════════════════════════════════════════════════════════
//  SESIÓN + PERMISOS
// ══════════════════════════════════════════════════════════════════
//  Backend guarda la sesión en cookie JSESSIONID. Aquí solo cacheamos
//  usuario+permisos en sessionStorage para renderizar UI sin re-consultar.
//  Se refresca llamando /api/auth/me.

const session = {

    // ── Cache local ─────────────────────────────────────────────
    _cache: null,

    getUsuario: () => {
        if (session._cache) return session._cache.usuario;
        const raw = sessionStorage.getItem('auth');
        if (!raw) return null;
        session._cache = JSON.parse(raw);
        return session._cache.usuario;
    },

    getPermisos: () => {
        if (!session._cache) session.getUsuario();
        return session._cache?.permisos || [];
    },

    setAuth: (auth) => {
        session._cache = auth;
        sessionStorage.setItem('auth', JSON.stringify(auth));
    },

    clearCache: () => {
        session._cache = null;
        sessionStorage.removeItem('auth');
    },

    // ── Refrescar desde backend ─────────────────────────────────
    // Se llama al inicio de cada página. Si el backend responde 401,
    // el request() global redirige a login.
    refresh: async () => {
        const auth = await api.get('/auth/me');
        session.setAuth(auth);
        return auth;
    },

    // ── Rol ─────────────────────────────────────────────────────
    nombreRol: () => session.getUsuario()?.rol?.nombre || '',
    esSuperUsuario: () => session.nombreRol() === 'SUPERUSUARIO',

    // ── Checks de permiso ───────────────────────────────────────
    // Los permisos vienen con booleanos: puedeVer/Insertar/Editar/Eliminar/Imprimir.
    // El SUPERUSUARIO tiene todo por convención (no chequeamos permisos para él).
    // El match se hace por url de funcionalidad (ej: "alumnos.html").

    _permisoDe: (url) => {
        const permisos = session.getPermisos();
        return permisos.find(p => p.url === url);
    },

    puedeVer:       (url) => session.esSuperUsuario() || !!session._permisoDe(url)?.puedeVer,
    puedeInsertar:  (url) => session.esSuperUsuario() || !!session._permisoDe(url)?.puedeInsertar,
    puedeEditar:    (url) => session.esSuperUsuario() || !!session._permisoDe(url)?.puedeEditar,
    puedeEliminar:  (url) => session.esSuperUsuario() || !!session._permisoDe(url)?.puedeEliminar,
    puedeImprimir:  (url) => session.esSuperUsuario() || !!session._permisoDe(url)?.puedeImprimir
};

// ══════════════════════════════════════════════════════════════════
//  LOGOUT
// ══════════════════════════════════════════════════════════════════
async function logout() {
    try { await api.post('/auth/logout'); } catch (e) { /* ignorar */ }
    session.clearCache();
    location.href = 'login.html';
}

// ══════════════════════════════════════════════════════════════════
//  PROTEGER PÁGINA + ARMAR SIDEBAR
// ══════════════════════════════════════════════════════════════════
//  Se llama al inicio de cada página protegida. Si no hay sesión,
//  redirige a login. Si hay sesión pero el usuario no tiene puedeVer
//  sobre esta página, redirige al dashboard.

async function protegerPagina() {
    const page  = location.pathname.split('/').pop();
    const libre = ['login.html', 'index.html', ''];

    try {
        await session.refresh();
    } catch (e) {
        return false;   // request() ya redirigió a login si fue 401
    }

    if (!libre.includes(page) && !session.puedeVer(page)) {
        toast('Sin permiso para acceder a este módulo', 'error');
        setTimeout(() => location.href = 'index.html', 800);
        return false;
    }

    loadSidebarUser();
    aplicarPermisosUI();
    return true;
}

// ── SIDEBAR DINÁMICO ─────────────────────────────────────────────
function buildSidebar() {
    const nav = document.querySelector('.sidebar-nav');
    if (!nav) return;

    const esSU = session.esSuperUsuario();

    const secciones = [
        { titulo: 'Principal',   items: [
            { label: 'Dashboard', url: 'index.html', icon: '🏠', libre: true }
        ]},
        { titulo: 'Mantenimiento', items: [
            { label: 'Años Académicos', url: 'anios.html',     icon: '📅' },
            { label: 'Alumnos',         url: 'alumnos.html',   icon: '👤' },
            { label: 'Aulas',           url: 'aulas.html',     icon: '🏫' },
            { label: 'Conceptos',       url: 'conceptos.html', icon: '📋' }
        ]},
        { titulo: 'Operaciones', items: [
            { label: 'Matrícula', url: 'matricula.html', icon: '📝' },
            { label: 'Pagos',     url: 'pagos.html',     icon: '💰' }
        ]},
        { titulo: 'Seguridad', items: [
            { label: 'Usuarios', url: 'usuarios.html', icon: '👥' },
            { label: 'Permisos', url: 'permisos.html', icon: '🔐' }
        ]},
        { titulo: 'Reportes', items: [
            { label: 'Reportes', url: 'reportes.html', icon: '📊' }
        ]}
    ];

    nav.innerHTML = '';
    const pageActual = location.pathname.split('/').pop();

    secciones.forEach(sec => {
        const visibles = sec.items.filter(item =>
            item.libre || esSU || session.puedeVer(item.url));

        if (!visibles.length) return;

        const secDiv = document.createElement('div');
        secDiv.className = 'nav-section';
        secDiv.textContent = sec.titulo;
        nav.appendChild(secDiv);

        visibles.forEach(item => {
            const a = document.createElement('a');
            a.href = item.url;
            a.className = (pageActual === item.url) ? 'active' : '';
            a.innerHTML = `<span class="icon">${item.icon}</span> ${item.label}`;
            nav.appendChild(a);
        });
    });
}

// ── CARGAR INFO DE USUARIO EN SIDEBAR ────────────────────────────
function loadSidebarUser() {
    const u = session.getUsuario();
    if (!u) return;
    const av = document.getElementById('sidebar-avatar');
    const nm = document.getElementById('sidebar-name');
    const rl = document.getElementById('sidebar-rol');
    if (av) av.textContent = (u.nombreCompleto || 'U').charAt(0).toUpperCase();
    if (nm) nm.textContent = u.nombreCompleto || '';
    if (rl) rl.textContent = u.rol?.nombre || '';
    buildSidebar();
}

// ── APLICAR PERMISOS EN UI DE LA PÁGINA ──────────────────────────
// Convención: los botones de acción tienen data-permiso="insertar|editar|eliminar|imprimir".
// Se ocultan si el usuario no tiene el permiso correspondiente.
function aplicarPermisosUI() {
    const page = location.pathname.split('/').pop();
    const map  = {
        'insertar': session.puedeInsertar(page),
        'editar':   session.puedeEditar(page),
        'eliminar': session.puedeEliminar(page),
        'imprimir': session.puedeImprimir(page)
    };
    document.querySelectorAll('[data-permiso]').forEach(el => {
        const req = el.getAttribute('data-permiso');
        if (map[req] === false) el.style.display = 'none';
    });
}

// ══════════════════════════════════════════════════════════════════
//  TOAST
// ══════════════════════════════════════════════════════════════════
function toast(msg, tipo = 'success') {
    let t = document.getElementById('toast');
    if (!t) {
        t = document.createElement('div');
        t.id = 'toast';
        document.body.appendChild(t);
    }
    t.textContent = msg;
    t.className = `show ${tipo}`;
    setTimeout(() => t.className = '', 3000);
}
