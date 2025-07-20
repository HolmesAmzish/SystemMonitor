# Introduction to the Project

This project was originally create to monitor the status of my server, as it used to shut down unexpectedly.
I am now continuously expanding its functionality.
Feel free to leave an issue or star the project if you're interested.

# Setup Instructions

1. Clone the repository

2. Create the database
   You need to create a database named 'system_log' in postgres.

3. Set keys for Email and Database
   Rename the example-keys.yaml file to keys.yaml and fill in the required details for email and database configuration.

4. Run the project
   mvn spring-boot:run