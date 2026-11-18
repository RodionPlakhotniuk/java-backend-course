# Java Backend Course

Homework from the backend part of my Java course (Jun – Aug 2025): from plain SQL and JDBC to Spring Boot and Spring Security.
Each folder is a standalone Maven project (except the SQL ones and the calculator).

These used to be separate repositories; they were merged here with their full commit history.

| Folder | What's inside | Stack |
|---|---|---|
| [27-sql-basics](27-sql-basics) | Create a database, CRUD queries | MySQL |
| [28-sql-queries](28-sql-queries) | `LIMIT`, `GROUP BY`, aggregate functions | MySQL |
| [29-jdbc](29-jdbc) | Employee DAO over plain JDBC | JDBC, MySQL |
| [30-jpa-hibernate](30-jpa-hibernate) | Generic DAO for a `Student` entity | Hibernate, JPA, JUnit 5 |
| [31-library-app](31-library-app) | Library: add and remove books | Java, JUnit 5 |
| [32-coffee-shop-order-queue](32-coffee-shop-order-queue) | Order queue for a coffee shop | Java, Logback |
| [33-client-server-sockets](33-client-server-sockets) | Simple client and server | Java sockets |
| [34-web-calculator](34-web-calculator) | Calculator in the browser | HTML, CSS, JS |
| [37-servlet-api](37-servlet-api) | CRUD servlet for orders (GET/POST/PUT/DELETE) | Servlet API, Jackson, Mockito |
| [38-online-store-cart](38-online-store-cart) | Shopping cart with Spring beans | Spring Context |
| [39-spring-jdbc](39-spring-jdbc) | Customer DAO with `JdbcTemplate` | Spring JDBC, MySQL |
| [40-spring-mvc](40-spring-mvc) | Order REST API | Spring Boot Web, Lombok |
| [41-spring-boot](41-spring-boot) | Order service with a database | Spring Boot, Spring Data JPA, MySQL |
| [42-spring-data-jpa](42-spring-data-jpa) | Users and posts (one-to-many) | Spring Data JPA, MySQL / H2 |
| [43-spring-security](43-spring-security) | Registration and login with roles | Spring Security, Thymeleaf, MySQL |

## Running

Projects that use MySQL expect a local database. Set your own credentials in `application.properties`
(or `persistence.xml` / the DAO config) — the value `your_password` is a placeholder.

```bash
cd 43-spring-security
./mvnw spring-boot:run
```
