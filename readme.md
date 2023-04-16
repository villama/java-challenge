### How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)
- Format the code with: `mvn formatter:format`

Application (with the embedded H2 database) is ready to be used ! You can access the url below for testing it :

- Swagger UI : http://localhost:8080/swagger-ui.html
  - Login with user: "user", password: "user123"
- H2 UI : http://localhost:8080/h2-console

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.

### Changes made

- Added controller and service tests
- Added builder and constructors to Employee entity
- Added Javadoc comments
- Added Spring Security
- Bug fix for saveEmployee request body
- Added and applied auto formatting (https://github.com/google/styleguide/blob/gh-pages/eclipse-java-google-style.xml)

### Experience with Java

I have 3 years experience with Java and Spring Boot, as well as experience with other Java frameworks like Play and Micronaut. I also have experience using gRPC for Java microservices.
