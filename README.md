# Student Management System

A full-stack, desktop-based University Management Application built with Java. This system demonstrates the implementation of Object-Oriented Programming (OOP) principles, the MVC architecture, and secure relational database management using MySQL.

## Core Features

* **Student Management:** Register new students, search profiles by Registration Number, and generate dynamically calculated Official Result Slips.
* **Lecturer Management:** Search for lecturers by Staff Number and view their allocated courses in a clean, tabular interface.
* **Library System:** A real-time "Search-As-You-Type" library portal that instantly displays available and borrowed book counts.
* **Dual Persistence:** Demonstrates flexibility by saving data to both a MySQL Database and a local `.txt` file using the DAO (Data Access Object) pattern.
* **Modern GUI:** Built with Java Swing, featuring clean layouts, `JTable` data grids, and native OS styling.

## 🛠️ Tech Stack

* **Language:** Java (JDK 8+)
* **Database:** MySQL
* **GUI Framework:** Java Swing / AWT
* **Architecture:** MVC (Model-View-Controller) & DAO (Data Access Object)
* **Driver:** MySQL Connector/J (JDBC)

---

## Getting Started

Follow these instructions to set up and run the project on your local machine.

### 1. Prerequisites
* Java Development Kit (JDK) installed.
* MySQL Server & MySQL Workbench installed.
* An IDE like IntelliJ IDEA, Eclipse, or VS Code.
* Download the [MySQL JDBC Driver (Connector/J)](https://dev.mysql.com/downloads/connector/j/) `.jar` file.

### 2. Database Setup
1. Open MySQL Workbench.
2. Create a new schema named `university_db` (or a name of your choice).
3. Run the following SQL script to generate the required tables:

```sql
CREATE TABLE Person (
    person_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_no VARCHAR(15) UNIQUE
);

CREATE TABLE Student (
    Student_id INT PRIMARY KEY,
    Reg_no VARCHAR(20) UNIQUE NOT NULL,
    Programme VARCHAR(100),
    FOREIGN KEY (Student_id) REFERENCES Person(person_id)
);

CREATE TABLE Lecturer (
    lecturer_id INT PRIMARY KEY,
    Staff_number INT UNIQUE NOT NULL,
    Department VARCHAR(100),
    FOREIGN KEY (lecturer_id) REFERENCES Person(person_id)
);

CREATE TABLE Book (
    isnn VARCHAR(20) PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    edition VARCHAR(20),
    version VARCHAR(20),
    year_published INT
);

CREATE TABLE Book_copy (
    copy_id INT PRIMARY KEY,
    isnn VARCHAR(20),
    Status ENUM('Available', 'Borrowed', 'Lost') DEFAULT 'Available',
    FOREIGN KEY (isnn) REFERENCES Book(isnn)
);

CREATE TABLE Course (
    course_code VARCHAR(20) PRIMARY KEY,
    title VARCHAR(100) NOT NULL
);

CREATE TABLE Lecturer_Course (
    staff_number INT,
    course_code VARCHAR(20),
    PRIMARY KEY (staff_number, course_code),
    FOREIGN KEY (staff_number) REFERENCES Lecturer(Staff_number),
    FOREIGN KEY (course_code) REFERENCES Course(course_code)
);
```
## 3. Secure Configuration (Important)
### To protect your database credentials, this project uses a .properties file.

In the root directory of the project, create a file named database.properties.

### Add your MySQL credentials:

Properties
db.url=jdbc:mysql://localhost:3306/university_db
db.username=root
db.password=your_mysql_password
Security Check: Ensure database.properties is added to your .gitignore file before pushing to GitHub!

## 4. Link the JDBC Driver
IntelliJ: Go to File > Project Structure > Libraries > + > Select the mysql-connector-j-X.X.X.jar file you downloaded.

## 5. Run the Application
Locate Main.java inside the com.system package and run it. The Main Dashboard GUI should appear!

### Project Architecture
The codebase strictly follows the MVC Pattern to separate data logic from the visual interface:

### Plaintext
src/
* └── com/system/
*   ├── model/          # The Data Blueprints (Student, Course, Score, Book)
*    ├── dao/            # Database Communicators (StudentDAO, LibraryDAO)
*   ├── view/           # Java Swing GUI Windows (MainDashboard, ResultSlipView)
*   ├── util/           # Utilities (DatabaseConnection)
*   └── Main.java       # Application Entry Point 

Troubleshooting
"No suitable driver found": You forgot to add the MySQL JDBC .jar file to your project libraries.

"Cannot connect to database": Check your database.properties file. Ensure the URL, username, and password exactly match your local MySQL setup.

"No student found" (But they are in the DB): Ensure the Student_id in the Student table exactly matches their person_id in the Person table. If they don't match, the SQL JOIN query will fail.

Author: Kelvin Kipchumba





