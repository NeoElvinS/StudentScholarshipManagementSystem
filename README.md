# Student Scholarship Management System

A desktop-based scholarship management application developed using Java, JavaFX, JDBC, and MySQL. The system helps manage student records, scholarship details, eligibility verification, and scholarship applications through a simple graphical interface.

## Features

- Student record management
  - Add student details
  - Update student details
  - Delete student records
  - View student records

- Scholarship management
  - Add scholarships
  - Update scholarship details
  - Delete scholarship details
  - View scholarship records

- Scholarship eligibility checking
  - Checks minimum CGPA requirement
  - Checks maximum family income requirement
  - Displays eligibility status

- Scholarship applications
  - Select student and scholarship
  - Submit applications
  - Prevent duplicate applications
  - Store application date
  - Track application status

- Application management
  - View submitted applications
  - Approve applications
  - Reject applications
  - Refresh application records

## Technology Stack

- Java
- JavaFX
- JDBC
- MySQL
- Eclipse

## Project Structure

StudentScholarshipSystem
│
├── src
│   ├── application
│   │   └── Main.java
│   │
│   ├── application.controller
│   │   ├── ApplicationController.java
│   │   ├── ScholarshipController.java
│   │   └── StudentController.java
│   │
│   ├── application.database
│   │   └── DBConnection.java
│   │
│   ├── application.model
│   │   ├── Scholarship.java
│   │   ├── ScholarshipApplication.java
│   │   └── Student.java
│   │
│   └── module-info.java

The application uses MySQL with three main tables:

1. students
2. scholarships
3. applications

### Students Table

Stores:

- Student ID
- Name
- Department
- Year
- CGPA
- Family Income
- Email

### Scholarships Table

Stores:

- Scholarship ID
- Scholarship Name
- Provider
- Minimum CGPA
- Maximum Family Income
- Scholarship Amount
- Deadline

### Applications Table

Stores:

- Application ID
- Student ID
- Scholarship ID
- Application Date
- Status

## Eligibility Logic

A student is considered eligible when:

Student CGPA >= Minimum Required CGPA

AND

Student Family Income <= Maximum Allowed Income

If both conditions are satisfied, the student is eligible for the scholarship.

## Application Workflow

Start
↓
Dashboard
↓
Select Student
↓
Select Scholarship
↓
Check Eligibility
↓
Eligible?
├── No → Application Not Allowed
│
└── Yes
    ↓
Submit Application
    ↓
Pending
    ↓
Approve / Reject

## How to Run

### 1. Clone the Repository

git clone https://github.com/NeoElvinS/StudentScholarshipSystem.git

### 2. Open in Eclipse

Import the project into Eclipse as an existing Java project.

### 3. Configure MySQL

Create the database:

CREATE DATABASE scholarship_db;

Then create the required tables:

- students
- scholarships
- applications

### 4. Configure Database Connection

Open:

application.database → DBConnection.java

Update the MySQL credentials:

private static final String URL =
        "jdbc:mysql://localhost:3306/scholarship_db";

private static final String USER = "root";

private static final String PASSWORD =
        "your_password";

Replace "your_password" with your local MySQL password.

Do not upload your actual database password to GitHub.

### 5. Add MySQL Connector

Add the MySQL Connector/J library to the Eclipse project and ensure the JDBC module is available to the application.

### 6. Run the Application

Open:

application → Main.java

Right-click Main.java and select:

Run As → Java Application

## Project Objective

The main objective of this project is to provide a simple database-driven system for managing student scholarship information, checking eligibility, processing applications, and maintaining application status efficiently.

## Advantages

- Simple and user-friendly desktop interface
- Centralized scholarship information
- Automated eligibility checking
- Reduces manual record management
- Prevents duplicate scholarship applications
- Provides organized application tracking
- Uses relational database connectivity
- Stores application data permanently in MySQL

## Future Enhancements

- Admin login and role-based access
- Student login
- Scholarship search and filtering
- Document upload for scholarship applications
- Email notifications
- Scholarship report generation
- Advanced dashboard and statistics

## Author

Neo Elvin S

B.Tech Artificial Intelligence and Data Science

## License

This project is developed for educational and academic purposes.
