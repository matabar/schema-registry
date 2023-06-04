FROM openjdk:11
COPY target/schema-registry-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","$JAVA_OPTS","-jar","/app.jar"]