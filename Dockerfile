FROM openjdk:8
EXPOSE 8080
COPY target/test-task.jar test-task-docker.jar
ENTRYPOINT ["java","-jar","/test-task-docker.jar"]