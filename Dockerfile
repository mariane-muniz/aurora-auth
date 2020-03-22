FROM maven:3.5.2-jdk-8-alpine
COPY pom.xml /maven-build/
COPY src/ /maven-build/src/
WORKDIR /maven-build/
RUN mvn package

FROM openjdk:8-jdk
WORKDIR /root/
COPY --from=0 /maven-build/target/auth.jar .
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "auth.jar"]