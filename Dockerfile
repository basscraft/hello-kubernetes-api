# Build stage
FROM gradle:8.5-jdk21 AS builder
WORKDIR /app

# 의존성 캐싱을 위해 설정 파일만 먼저 복사
COPY build.gradle settings.gradle ./
# 소스 없이 의존성만 다운로드 (실패할 수 있으므로 || true 처리하거나, --no-daemon으로 실행)
# Gradle은 소스 없이 dependencies만 다운로드하는 명시적인 명령어가 없어서
# 보통은 이 단계를 건너뛰거나, 빈 소스 디렉토리를 만드는 트릭을 씁니다.
# 여기서는 가장 간단하게 전체 복사 후 캐시 마운트를 활용하는 방식을 추천합니다.

# 다시 전체 복사 (소스 코드 포함)
COPY . .

# 실행 권한 부여
RUN chmod +x gradlew

# BuildKit 캐시 마운트를 활용하여 Gradle 캐시 유지
# --mount=type=cache,target=/root/.gradle
# 이 옵션은 docker buildx 사용 시에만 유효합니다.
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew clean bootJar --no-daemon -x test

# Run stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/HelloKubernetesApplication.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
