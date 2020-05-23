FROM openjdk:11
ADD build/libs/SpringTest-0.0.1.jar SpringTest-0.0.1.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "SpringTest-0.0.1.jar"]

# docker build -t spring-test .
# docker run -d -p 8089:8089 --name spring-test spring-test
# docker container logs spring-test
# <test>
# docker stop spring-test
# docker rm spring-test
