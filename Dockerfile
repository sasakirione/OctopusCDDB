FROM amazoncorretto:17

ARG _DB_PATH
ARG _DB_USER
ARG _DB_PASS

ENV DB_PATH ${_DB_PATH}
ENV DB_USER ${_DB_USER}
ENV DB_PASS ${_DB_PASS}

COPY build/libs/cddb-all.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]