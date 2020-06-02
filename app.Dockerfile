FROM tomcat:9.0
COPY build/libs/spring-test-0.0.1.war /usr/local/tomcat/webapps/spring-test.war
