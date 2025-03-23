FROM maven:3.9.9 AS builder
WORKDIR /application
COPY . .
RUN --mount=type=cache,target=/root/.m2  mvn clean install -pl :spring-petclinic-vets-service -Dmaven.test.skip

FROM bellsoft/liberica-openjre-alpine:23 AS layers
WORKDIR /application
COPY --from=builder /application/target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM bellsoft/liberica-openjre-alpine:23
VOLUME /tmp
RUN adduser -S spring-user
USER spring-user
COPY --from=layers /application/dependencies/ ./
COPY --from=layers /application/spring-boot-loader/ ./
COPY --from=layers /application/snapshot-dependencies/ ./
COPY --from=layers /application/application/ ./

ENV JAVA_ERROR_FILE_OPTS="-XX:ErrorFile=/tmp/java_error.log"
ENV JAVA_HEAP_DUMP_OPTS="-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp"
ENV JAVA_ON_OUT_OF_MEMORY_OPTS="-XX:+CrashOnOutOfMemoryError"
ENV JAVA_NATIVE_MEMORY_TRACKING_OPTS="-XX:NativeMemoryTracking=summary -XX:+UnlockDiagnosticVMOptions -XX:+PrintNMTStatistics"
ENV JAVA_GC_LOG_OPTS="-Xlog:gc*,safepoint:/tmp/gc.log::filecount=10,filesize=100M"

ENTRYPOINT java \
    $JAVA_HEAP_DUMP_OPTS \
    $JAVA_ON_OUT_OF_MEMORY_OPTS \
    $JAVA_ERROR_FILE_OPTS \
    $JAVA_NATIVE_MEMORY_TRACKING_OPTS \
    $JAVA_GC_LOG_OPTS \
    org.springframework.boot.loader.launch.JarLauncher
