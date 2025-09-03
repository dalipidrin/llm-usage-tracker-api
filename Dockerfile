FROM openjdk:17-jdk-slim
WORKDIR /app

COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .
COPY src ./src

RUN chmod +x ./gradlew
RUN ./gradlew build -x test

EXPOSE 9000
CMD ["java", "-jar", "build/libs/llm-usage-tracker-0.0.1-SNAPSHOT.jar"]
