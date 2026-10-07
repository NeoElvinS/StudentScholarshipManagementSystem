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

### Application
- Main.java
- module-info.java

### Controllers
- StudentController.java
- ScholarshipController.java
- ApplicationController.java

### Database
- DBConnection.java

### Models
- Student.java
- Scholarship.java
- ScholarshipApplication.java

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

## License

This project is developed for educational and academic purposes.
