# Dockerfile

FROM openjdk:17-jdk-slim
WORKDIR /app

# JAR 파일 복사 (GitHub Actions에서 EC2에 복사된 최신 JAR)
COPY build/libs/outline-0.0.1-SNAPSHOT.jar app.jar

# 환경 변수 설정
ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "app.jar"]
