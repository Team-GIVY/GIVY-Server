# 빌드 스테이지
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Gradle wrapper와 설정 파일 먼저 복사 (캐시 최적화)
COPY gradle/ ./gradle/
COPY gradlew ./
COPY build.gradle settings.gradle ./

# Gradle 실행 권한 부여
RUN chmod +x ./gradlew

# 의존성 다운로드 (소스 변경 없이 캐시 활용)
RUN ./gradlew dependencies --no-daemon || true

# 소스 코드 복사
COPY src/ ./src/

# 빌드 실행 (QueryDSL 생성 포함)
RUN ./gradlew clean bootJar --no-daemon

# 실행 스테이지
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=build /app/build/libs/givy-0.0.1-SNAPSHOT.jar app.jar

# 8080 포트 개방
EXPOSE 8080

# 헬스체크 추가 (선택사항)
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# 실행 환경 설정
ENTRYPOINT ["java", "-jar", "app.jar"]