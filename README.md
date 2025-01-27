# README
This project uses MySQL as the relational database to store messages. Follow the steps below to install and configure the database properly.

## Prerequisites
Before starting, ensure you have the following installed:

MySQL Server (version 8.0 or later recommended)
MySQL Workbench (optional, for GUI-based management)
Java JDK (version 17 or later)
Maven (for backend dependency management)
Step 1: Install MySQL
Download MySQL:

Download and install MySQL Community Server from the official MySQL website.
Start the MySQL Server:

Ensure the MySQL service is running.
Use the MySQL Workbench or terminal to manage the database.
Step 2: Create the Database
Access MySQL CLI or Workbench:

Open your terminal or MySQL Workbench and log in using your credentials:
bash
mysql -u root -p
Create the Database:

Execute the following SQL command to create the database:
sql

CREATE DATABASE chatdb;
Step 3: Configure the Backend to Connect to MySQL
Modify application.properties:

Ensure the application.properties  file in the backend is updated with your database credentials:
properties
spring.datasource.url=jdbc:mysql://localhost:3306/chatdb
spring.datasource.username=root
spring.datasource.password=your_password

## Start the server 

backend: 
mvn spring-boot:run 

frontend: 
ng serve
