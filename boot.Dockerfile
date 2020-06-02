FROM gradle:latest AS build
COPY . /home/gradle/app
WORKDIR /home/gradle/app
RUN gradle clean build --no-daemon -PskipTest=Y -PskipCheck=Y

FROM openjdk:11
COPY --from=build /home/gradle/app/build/libs/*.jar spring-test-0.0.1.jar

# docker build -t spring-test .
# docker run -d -p 8089:8089 --name spring-test spring-test
# docker container logs spring-test
# <test>
# docker stop spring-test
# docker rm spring-test
