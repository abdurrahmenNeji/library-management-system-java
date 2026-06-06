# Library Management System

A simple web-based library management system built with Java Servlets, JSP, PostgreSQL, and Apache Tomcat. The application allows users to browse books, view book details, borrow books, download available files, and manage their personal profile. It also includes an admin area for managing books, authors, categories, loans, and user activity.

## Features

### User Features

- User login and session management
- Browse available books
- Search books by title, category, or author
- View book details
- Add books to favorites
- Borrow books
- View borrowed books
- Download available book files
- View user profile and reading/download history

### Admin Features

- Admin dashboard
- Manage books
- Add, update, and delete books
- Manage authors
- Assign authors to books
- Manage categories
- Manage loans
- View active users and activity statistics
- Upload book files

## Technologies Used

- Java 17
- Java Servlets
- JSP
- JDBC
- PostgreSQL
- Apache Tomcat 9
- HTML
- CSS
- Eclipse IDE

## How to Run

1. Install Java 17.
2. Install Apache Tomcat 9.
3. Install PostgreSQL.
4. Create a PostgreSQL database named `library_management`.
5. Import the project into Eclipse as a Dynamic Web Project.
6. Add the PostgreSQL JDBC driver to the project build path.
7. Update the database username and password in `DatabaseConnection.java`.
8. Run the project on Tomcat.

Example local URL:

http://localhost:8080/library_management/

##Important Note

This project was created for learning and academic purposes. For production use, password security, role protection, validation, and environment-based configuration should be improved.

##Author

Developed by Abdurrahmen Neji.
