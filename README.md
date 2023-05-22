## Employee Spring Boot App

### About

---

A project I developed while taking the Java Enterprise course at Hillel IT School.
The idea of the project is a CRUD API for managing employees.
The project is written in the REST architectural style.

The project includes:

- 5 entities with all types of relationships.
- DTO pattern is used.
- Database versioning is supported.
- Docker and Docker Compose are configured.
- Work with emails and file saving.
- OpenApi documentation.
- Implemented jdbcAuth by Spring Security.

### Documentation

---
The documentation is written in OpenApi and uses SwaggerUI.
To read it, launch the project and follow the link: [Documentation](http://localhost:8087/swagger-ui.html).

### Quick start

---
Quick guide to run app.

1. Install Docker. [Docker Docs](https://docs.docker.com/get-docker/).
2. Install Maven. [Download Maven](https://maven.apache.org/download.cgi).
3. Run Docker (required for maven plugin to work).
4. Clone the repository `git clone https://github.com/makurohashami/employeesSpringBoot.git`.
5. Open file `application.yml` and set `create` to field `spring: jpa: hibernate: ddl-auto:` for the first run. Next
   time you run it, put `update`.
6. To work with e-mail, configure the SMPT server you need and configure it in the MailConfig.java file. P.S. If you
   won't use this functionality, you can leave it as it is.
7. Open bash/cmd in project directory.
8. Run `mvn clean install -DskipTests=true`
9. Run `docker-compose up -d`
10. Use [app](http://localhost:8087/) or go to docs with [link](http://localhost:8087/swagger-ui.html).
