# Top-n-Email-Domains

This Java program reads from an input.txt file, checks the number of times a particular domain appears in the input.txt file, and outputs the data to the terminal. The input.txt file contains one email per line.

#### Overview 

I chose to create a Maven project with the Spring framework due to its versatility. The program is written in Java, and the project's structure was generated using the [Spring initilizr](https://start.spring.io/).

The main codebase is located in the [src/main/java/markus/TopEmailDomains](src/main/java/markus/TopEmailDomains) directory. You can launch the program using the [TopEmailDomainsApplication.java](src/main/java/markus/TopEmailDomains/TopEmailDomainsApplication.java) main method or Maven commands.

#### Building and Running the program
The program uses Maven for building and running. To get started, ensure you are in the root directory, which is the same directory as this README.md and the pom.xml file.

To build the program, run the following command: `mvn install`. This will build the project and execute all tests.

If you want to run the program, simply execute `mvn javafx:run`. This command will run the main method inside[TopEmailDommainsApplication.java](src/main/java/markus/TopEmailDomains/TopEmailDomainsApplication.java). To run only the tests, use the command `mvn test`

Also, if you want to randomly generate an input file as I do in the test class, simply execute `mvn exec:java`. This command will run the main method inside the [GenerateInputData.java](src/test/java/markus/TopEmailDomains/GenerateInputData.java) file.

#### Requirements

- **Java 17** : This program is built using Java 17.
- **Spring Boot 3.1.5** : The program utilizes the Spring framework.

#### Directory Structure
The project's directory structure is organized as follows:

- src
  - main
    - java/markus/TopEmailDomains
      - Sorce location for the application
    - test/java/markus/TopEmailDomains
      - Source location for all tests
