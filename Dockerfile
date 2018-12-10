FROM openjdk:10-jdk AS builder
COPY . /opt/reviews
WORKDIR /opt/reviews
RUN ./gradlew build

FROM openjdk:10-jre
COPY --from=builder /opt/reviews/build/libs/*.jar /app/app.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]
