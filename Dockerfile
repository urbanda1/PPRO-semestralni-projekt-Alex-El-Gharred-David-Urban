FROM openjdk:11
EXPOSE 8080
ADD target/blog-api-docker.jar blog-api-docker.jar
ENTRYPOINT ["java","-jar","blog-api-docker.jar"]