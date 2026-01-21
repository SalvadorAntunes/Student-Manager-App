# Student Manager App
A Java console application that allows teachers to manage students, subjects, and grades.
This project was built for academic purposes and to showcase skills in **Object-Oriented Programming** and **Algorithms and Data Structures**.
---
## Overview

The Student Manager App lets teachers:

- Create and customize subjects
- Define evaluation methods using components and assignments
- Register students
- Submit and check grades
- Automatically calculate final grades
- Handle attendance and approval rules
- Export and import subjects using file serialization

Everything is done through a command-line interface using structured commands.

---
## Tech Stack

Language: Java

Paradigm: Object-Oriented Programming

Concepts used:

- Interfaces & Abstract Design
- Custom Exceptions
- Serialization
- Iterators & Predicates
- Custom Data Structures
- Sorting and Filtering

---
## Main Features

### Create a subject with:

- Multiple components
- Custom weights
- Minimum grade per component
- Mandatory attendance components
- Assignments per component with individual weights

### Student management:

- Add students by number and name
- Search by number or name

### Grading:

- Submit grades for assignments
- Change or correct grades

### Automatic calculation of:

- Component grades
- Final grade
- Attendance and approval

### Listings:

- Full grade sheet
- Assignment grade sheet
- Students who weren't approved but had attendance
- Sorted by number or alphabetically

### Persistence:

- Save subjects to file
- Load subjects from file

---
## How to Run

Compile:

```bash
javac Main.java
```
Run:
```bash
java Main
```
The program will start waiting for commands.

---
## Available Commands

```md
Command	Description
subject	Defines a new subject and its evaluation method
save	Saves the current subject to file
load	Loads a subject from file
student	Adds a student
grade	Submits a student’s grade
check	Checks a student’s grades
list	Lists grades (full sheet or assignment)
exam	Shows students automatically registered for exam (not approved with attendance)
help	Shows available commands and their usage
exit	Saves and exits program
```
### Sorting Options

When listing students, you can choose:

By number: N or number

Alphabetically: A, name or alphabetic

---
## File Storage

Subjects are saved using Java serialization

Files are stored as:

subject_name.ser

Automatically saved on:

exit

Creating a new subject if one already exists

---
## Learning Goals

This project demonstrates:

- Clean OOP design with interfaces and implementations
- Error handling using custom exceptions
- Building and using custom data structures
- Data persistence with serialization

---
## Author

Salvador Lourenço Antunes

Email: salvadorantunes06@gmail.com

LinkedIn: https://www.linkedin.com/in/salvadornlantunes/

Project developed for academic and portfolio purposes.