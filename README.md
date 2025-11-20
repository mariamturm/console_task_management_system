Task Management System (Java Console App)

A simple console-based Task Management System written in Java.
This project was created to practice OOP, inheritance, file handling, HashMap, and console menus.

The program allows users to create, update, view, delete, and store
tasks in a text file so data is saved even after closing the
application.



Features

Three types of tasks

1. BasicTask - simple task with name & definition
2. LimitedTimeTask - task with a deadline (LocalDateTime)
3. RepeatableTask - task that repeats (timesToDo + when)

Menu options

1.   Create a task
2.   View all tasks
3.   View a specific task
4.   Update a task
5.   Delete a task
6.   Save & load tasks from a file ('tasks.txt')

Data persistence

Tasks are saved into a simple text file.


Concepts Used

1.   Java OOP (classes, inheritance, abstract class, overriding)
2.   Polymorphism (each task updates itself)
3.   HashMap for storage
4.   Scanner for console input
5.   File I/O (read/write text files)
6.   LocalDateTime parsing + formatting
7.   Loops, conditions, switch statements



How to Run

1. Open the project in IntelliJ IDEA
(or any Java IDE)
          

3. Run the program

Open 'Main.java' and press the green Run button.

4. Follow the console menu

Example:

    Enter your username:
    mariam
    ==== Task Management System ====
    1. Save task
    2. Take all tasks
    3. Update specific task
    4. Delete specific task
    5. Take specific task
    0. Exit



Example of tasks.txt

    BASIC|homework|Finish math exercises|mariam|||
    LIMITED|project|Finish Java project|mariam|25/11/2025 18:30||
    REPEATABLE|gym|Leg day|mariam||3|weekly


Possible Future Improvements

-   Search tasks by keyword\
-   Sort tasks (by name, by deadline, etc.)\
-   Colorful console output\
-   Better error messages\
-   JSON or XML data saving

About

This project was created while learning Java basics and OOP.
It is beginner-friendly, and focuses on clean
structure and understanding core concepts.
