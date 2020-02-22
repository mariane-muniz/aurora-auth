FROM openjdk:8-jdk
ADD target/auth.jar auth.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "auth.jar"]