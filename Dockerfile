FROM openjdk:17
COPY rest/target/zoostore-rest.jar zoostore.jar
EXPOSE 8080
CMD ["java", "-jar", "zoostore.jar"]