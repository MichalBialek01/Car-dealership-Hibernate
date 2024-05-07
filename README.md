The project presents a car dealership project with functionalities for purchasing cars and servicing them. The relationships between entities are represented using an ERD diagram made in UML notation.  
![Car dealership ERD Diagram ]( https://github.com/MichalBialek01/W16-ORM-and-Hibernate/blob/master/src/main/resources/Car_dealership_project.jpg) 
The project was written using Hibernate, without using Spring functionality - somewhat masochistically.
The program operates by retrieving data from the traffics-simulation.md file located in the project's resources directory.
This file has a specific structure, which is then mapped using mapping classes to database entities, which are saved in a PostgreSQL database.

In the future, I plan to create a version using Spring JPA and Spring Web MVC for user interactivity - in progress.
