# Introduction to the Project

This project was originally create to monitor the status of my server, as it used to shut down unexpectedly.
I am now continuously expanding its functionality.

The project is built using Java with Spring Boot framework and uses PostgreSQL and Redis. Using common C/S architecture,
the client will send system monitoring data to the server and stored in the database. You can check the data through
server's web interface. The default port is 8080, and you can config by yourself like other spring projects.

Feel free to leave an issue or star the project if you're interested.

# Features

- Client sends system monitoring data to server.
- Server stores data in PostgreSQL database.
- Server provides a web interface to view the monitoring data.

# Setup Instructions

1. Setup server
   - Install PostgreSQL and Redis on your server.
   - Configure database connection in keys.properties, I have given an example file in the project.
   - Run the project using `mvn spring-boot:run` or build a jar file and run it with `java -jar your-jar-file.jar`.

   ```bash
   cd server
   mvn spring-boot:run
   ```

2. Setup client
    - Install Java on your client machine.
    - Build the client using `mvn clean package` and run it with `java -jar your-client-jar-file.jar`.
    - The client will automatically connect to the server and start sending monitoring data.

    ```bash
    cd client
    mvn spring-boot:run
    ```