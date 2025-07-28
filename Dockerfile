FROM openjdk:17-jdk-slim
VOLUME /tmp

# 실제 jar 이름으로 정확히 지정
COPY build/libs/outline-0.0.1-SNAPSHOT.jar app.jar

# 환경 변수에서 profile 사용
ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "/app.jar"]
