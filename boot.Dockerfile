FROM gradle:latest AS build
COPY . /home/gradle/app
WORKDIR /home/gradle/app
RUN gradle clean build --no-daemon -PskipTest=Y -PskipCheck=Y

FROM adoptopenjdk:11-jre-hotspot as builder
WORKDIR application
ARG JAR_FILE=target/*.jar
COPY --from=build /home/gradle/app/build/libs/*.jar application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM adoptopenjdk:11-jre-hotspot
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]

#FROM openjdk:11
#COPY --from=build /home/gradle/app/build/libs/*.jar spring-test-0.0.1.jar
#CMD ["java", "-jar", "spring-test-0.0.1.jar"]

# docker build -t spring-test .
# docker run -d -p 8089:8089 --name spring-test spring-test
# docker container logs spring-test
# <test>
# docker stop spring-test
# docker rm spring-test
