FROM openjdk:15
EXPOSE 8080
ARG JAR_FILE=/target/barbershop-spring-web-app.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
