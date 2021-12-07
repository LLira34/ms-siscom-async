FROM openjdk:8-jdk-slim
COPY "./target/ms-siscom-async-0.0.1-SNAPSHOT.jar" "siscom-async.jar"
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "siscom-async.jar"]
