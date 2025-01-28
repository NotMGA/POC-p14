# README.md

## Overview

This project uses MySQL as the relational database to store messages. Follow the steps below to install and configure the database properly.

---

## Prerequisites

Before starting, ensure you have the following installed:

- **MySQL Server** (version 8.0 or later recommended)
- **MySQL Workbench** (optional, for GUI-based management)
- **Java JDK** (version 17 or later)
- **Maven** (for backend dependency management)

---

## Step 1: Install MySQL

### Download MySQL:
1. Download and install **MySQL Community Server** from the [official MySQL website](https://dev.mysql.com/downloads/).

### Start the MySQL Server:
1. Ensure the MySQL service is running.
2. Use MySQL Workbench or the terminal to manage the database.

---

## Step 2: Create the Database

### Access MySQL CLI or Workbench:
1. Open your terminal or MySQL Workbench.
2. Log in using your credentials:
   ```bash
   mysql -u root -p
   ```

### Create the Database:
1. Execute the following SQL command to create the database:
   ```sql
   CREATE DATABASE chatdb;
   ```

---

## Step 3: Configure the Backend to Connect to MySQL

### Modify `application.properties`:
Ensure the `application.properties` file in the backend is updated with your database credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/chatdb
spring.datasource.username=root
spring.datasource.password=your_password
```

---

## Start the Server

### Backend:
Run the backend server using Maven:
```bash
mvn spring-boot:run
```

### Frontend:
Run the frontend Angular application:
```bash
ng serve
```


