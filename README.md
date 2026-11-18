# Student CRUD API

A Java and Spring Boot REST API for creating, viewing, updating, and deleting student records. This portfolio project is a rebuild of an earlier Spring Boot learning project.

## Run locally

Requires Java 17+ and Maven. From the project directory, run:

```sh
mvn spring-boot:run
```

The API listens at http://localhost:8080. It uses an in-memory H2 database, so records reset on restart and no database credentials are needed.

| Method | Route | Success |
| --- | --- | --- |
| GET | /students | 200 with all students |
| GET | /students/{id} | 200 with one student |
| POST | /students | 201 with the created student and a Location header |
| PUT | /students/{id} | 200 with the updated student |
| DELETE | /students/{id} | 204 |

An unknown student ID returns 404. Invalid request bodies return 400. Both POST and PUT require a first name, last name, and valid email.

## Try it in Postman

Create a POST request to http://localhost:8080/students and select **Body → raw → JSON**.

```json
{
  "firstName": "Maya",
  "lastName": "Patel",
  "email": "maya@example.com"
}
```

Send the request and copy the generated ID from the response. Use it for GET, PUT, and DELETE requests. For PUT, send all three fields with the values you want to keep.

## How a POST request works

Spring MVC uses Jackson to convert the JSON request into a StudentInput record. Bean Validation checks the input before the controller runs. The controller calls StudentDao, which persists a Student entity through JPA's EntityManager. The controller returns the saved student and the URL for retrieving it.

H2 makes the project easy to run on a fresh checkout. The JPA entity and DAO show how the API interacts with a relational database.
