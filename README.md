# Build

## Build & Quality check

- Run `gradle clean build` or `gradle clean test`
- Following quality checks will be executed
    - Unit & Integration tests
        - Report is available in [build/reports/tests/test/index.html](file://./build/reports/tests/test/index.html)
        - Make sure the tests are 100% successful
    - Jacoco code coverage 
        - Report is available in [build/reports/jacoco/test/html/index.html](file://./build/reports/jacoco/test/html/index.html)
        - Sources with *Generated* annotations will be ignored from code coverage
        - Gradle configuration for `jacocoTestCoverageVerification` is set at 0.9 (90%)
        - Make sure the code coverage is closer to 100%
    - Checkstyle
        - Report is available in [build/reports/checkstyle/main.html](file://./build/reports/checkstyle/main.html)
        - Configuration & report style are present in [config/checkstyle](file://./config/checkstyle)
        - Make sure there are no warnings or errors
    - Spotbugs
        - Report is available in [build/reports/spotbugs/main.html](file://./build/reports/spotbugs/main.html)
        - Make sure there are no warnings or errors

## Build only (exclude tests & QA checks)

- Run `gradle clean build -PskipTest=Y -PskipCheck=Y`

# Testing & Debugging

## API Testing using HTTP Client

- Create an HTTP client file - `http-client/test-apis.http`
- Create an environment file using the 'Add environment file' option - `http-client/http-client.env.json`
- Select `Run with environment` option to run one or all tests.

## API Testing using Postman

#### Setup API tests

- Add API tests in Postman.
- The tests can be run via **Postman Runner** on the collection.

#### Execute API tests via newman

- The tests can also be run via **newman**
    - Export the collection as `*_collection.json`
    - Export the environment as `*_environment.json`
    - Install *newman* globally via `npm install -g newman`
    - Run 10 iterations
        - `newman run src/main/resources/postman/spring-test.postman_collection.json -e src/main/resources/postman/local-8080.postman_environment.json -n 10`

## Remote Debugging

- Run the remote application with debug options enabled
    - `java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=127.0.0.1:5005 spring-test/build/libs/spring-test-0.0.1.jar`
- Create a remote Run configuration mapping to the debug-socket port
- Setup break points in the application
- Execute tests on the remote API

# Deploy & Run

- OpenAPI documentation will be available under [http://localhost:8080](http://localhost:8080)

## Deploy in Tomcat

- Deploy the war under Tomcat 9 (not Tomcat 10)

## Run with docker

- Run `docker-compose -f docker-compose-boot.yml up --build`
- Run `docker-compose -f docker-compose-boot.yml up`

## Run with docker

- Run `docker-compose -f docker-compose-boot.yml up --build`
- Run `docker-compose -f docker-compose-boot.yml up`
