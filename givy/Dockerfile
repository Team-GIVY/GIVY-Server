# 빌드 스테이지
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# 전체 소스 복사
COPY . .

# Gradle 실행 권한 부여 및 빌드 (QueryDSL 생성 포함)
RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar

# 실행 스테이지
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# 빌드된 JAR 파일 복사 (버전 정보 포함된 이름 대응)
COPY --from=build /app/build/libs/givy-0.0.1-SNAPSHOT.jar app.jar

# 8080 포트 개방
EXPOSE 8080

# 실행 환경 설정 (Spring Boot 3.x 대응)
ENTRYPOINT ["java", "-jar", "app.jar"]