FROM bellsoft/liberica-runtime-container:jdk-17-musl AS builder
WORKDIR /app
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline
COPY src src
RUN ./mvnw clean package -DskipTests

# Runtime stage (Minimal JRE image)
FROM bellsoft/liberica-runtime-container:jre-17-musl
WORKDIR /app
COPY --from=builder /app/target/file-upload-service-0.0.1-SNAPSHOT.jar file-upload-service-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "file-upload-service-0.0.1-SNAPSHOT.jar"]