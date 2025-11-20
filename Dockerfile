# --- Stage 1: Build ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Копіюємо лише pom.xml спочатку, щоб закешувати залежності
COPY pom.xml .
# Завантажуємо залежності (цей крок буде закешовано, якщо pom.xml не змінився)
RUN mvn dependency:go-offline -B

# Копіюємо вихідний код
COPY src ./src

# Збираємо проект, пропускаючи тестування (для швидкості збірки в докері)
RUN mvn clean package -DskipTests

# --- Stage 2: Runtime ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Копіюємо зібраний jar файл з попереднього етапу
# Spring Boot автоматично називає файл на основі artifactId та version
COPY --from=build /app/target/SquidGameMS-0.0.1-SNAPSHOT.jar app.jar

# Відкриваємо порт
EXPOSE 8080

# Запускаємо додаток
ENTRYPOINT ["java", "-jar", "app.jar"]