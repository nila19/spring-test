## API Testing using HTTP Client

- Create an HTTP client file - `test-apis.http`
- Create an environment file using the 'Add environment file' option - `http-client.env.json`
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
