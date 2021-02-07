# Vehicle-Rental-Backend
Vehicle-Rental - a web application that is designed to support the work of the vehicle rental company. It enables employees to manage rental resources faster, and customers - convenient and easy access to offers. Its backend was made in Java Spring Boot, and its frontend in React.js. It was a final application for the design part of subjects related to databases, programming engineering, object-oriented technologies and programming in JAVA at my university - it was made in cooperation with [krystian4](https://github.com/krystian4) ([front-end part](https://github.com/krystian4/Vehicle-Rental-Frontend)).

<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#database">Database</a></li>
        <li><a href="#server">Server</a></li>
        <li><a href="#built-with">Built With</a></li>
        <li><a href="#tools-used">Tools Used</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

## About The Project

![Main Page](/images/main.png)

### Database
The database was created in the MySQL technology. The database communicates with the server using Spring Data JPA, which is used by Spring Boot for easy data access. There are no database triggers or procedures used in the project, because all changes are made via repositories. This approach greatly simplifies the process of modifying data stored in the database. With PhpMyAdmin, it is easy to control all changes in the structure of the database and perform simple tests to check the correctness of the program.

### Server
The server was made entirely in JAVA and the Spring Boot framework. Data is accessed through repositories. Spring Boot allows for the automatic implementation of written interfaces of these components.The server uses the Code First approach here, which means that the entire database structure is generated from entity classes (they are located in [models](https://github.com/PawelPabianczyk/Vehicle-Rental-Backend/tree/main/src/main/java/pl/vehiclerental/restapi/models)). In these classes, you can freely define the fields by adding appropriate markings that affect the generated tables (name of the entire table or individual columns). In addition, you can freely select the relationship between individual entities, which naturally reflects the operation of the application.
Another issue is the queries that can also be created in the Code First approach. It is enough to declare the chosen methods in the repository interface. As a result, by sticking to the appropriate syntax, it is easy to get less complicated queries to the database, such as accessing data about all customers who provided their driving license number and were not verified by the rental employees. When it comes to more advanced queries that involve joining tables in many-to-many relationships (the base is normalized to 3F, therefore such relationships are represented by an additional table containing only the id of records from two tables), then we use native SQL commands that are executed by EntityManager. Any data modification or adding new records is also done by the repositories to which the objects are saved.<br/>

Communication with the client application takes place via the controllers. Thanks to this, it is easy to control the data flow between the database and the client. The server receives the request from the client application and then routes it to the appropriate controller. There, it is subject to authentication and authorization - it is checked whether a given user exists in the database and whether he has access to a given part of the controller (to appropriate resources). This is possible thanks to the use of tokens, and more specifically the JWT TOKEN technology. When the user logs into the system, he sends a request to the server, which returns him the appropriate token. Then, if the user sends a request to the server for resources that require such a token, the server will check whether the token sent by the user matches the one generated when logging into the server. This means that resources that require proper authorization will be secured.

### Built With
* [Java](https://www.java.com/pl/)
* [Spring Boot (with Spring Security, Spring Web, Spring Data JPA)](https://spring.io/projects/spring-boot)
* [MySQL](https://www.mysql.com/)
* [Maven](https://maven.apache.org/)
* [JUnit 5](https://junit.org/junit5/)


### Tools Used
* [IntelliJ IDEA](https://www.jetbrains.com/idea/)
* [Postman](https://www.postman.com/)
* [XAMPP](https://www.apachefriends.org/pl/index.html)

## Getting Started
At the very beginning, you need to download MySQL. Next you have to create user:
```
CREATE USER 'rental'@'localhost' IDENTIFIED BY 'rental';
GRANT ALL PRIVILEGES ON * . * TO 'rental'@'localhost';
FLUSH PRIVILEGES;
SHOW GRANTS FOR 'rental'@'localhost';

```
You can use any IDE that supports MySQL (e.g. PHPMyAdmin) or directly via the MySQL console. Then clone the repository and run it in the selected Java-enabled IDE or using the console.

## Usage
To use the application fully, you must download the frontend application [Vehicle-Rental-Frontend](https://github.com/krystian4/Vehicle-Rental-Frontend).<br/><br/>
Sample screenshots from Postman:
![Main Page](/images/01.png)

An example of a successful login.<br/><br/>

![Main Page](/images/02.png)

An example of a failed login.<br/><br/>

![Main Page](/images/03.png)

An example of accessing data of all employees.<br/><br/>

![Main Page](/images/04.png)

Example of no access to data (no authentication).<br/><br/>

![Main Page](/images/05.png)

Example of adding a new FAQ.<br/><br/>

## Contributing

Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

Distributed under the MIT License. See `LICENSE` for more information.

## Contact

Email - pawel.pabianczyk1999@gmail.com

LinkedIn - [Pawel Pabianczyk](https://www.linkedin.com/in/pawe%C5%82-pabia%C5%84czyk-a32693171/)

Project Link: [https://github.com/PawelPabianczyk/Vehicle-Rental-Backend](https://github.com/PawelPabianczyk/Vehicle-Rental-Backend)
