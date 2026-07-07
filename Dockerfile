FROM amazoncorretto:25-al2023

RUN yum update -y && \
    yum install -y fontconfig && \
    yum clean all

ENV TZ='America/Lima'
RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime

EXPOSE 8080

ADD target/ms-matricula-back.jar ms-matricula-back.jar

ENTRYPOINT ["java", "-jar", "ms-matricula-back.jar"]