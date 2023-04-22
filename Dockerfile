FROM openjdk:11
ADD /target/demo-hillel-app.jar demo-hillel-app.jar
ENTRYPOINT ["java", "-jar", "demo-hillel-app.jar"]