Library System (Java)
Overview

This is a plain Java console application for managing a library. It supports user authentication, book management, and persistent storage so all changes remain across program runs.

Admin users can manage books and users.

Members can search, borrow, and return books.

New users can register and log in.

Features
Admin

Add, remove, and edit books.

View all users.

Add or remove users.

See who has borrowed a specific book.

Change their own username and password.

Member

Search and view available books.

Borrow and return books.

View their borrowed books.

Change their password.

General

Persistent data storage (library.dat) ensures all books, users, and borrow records are saved between runs.

User-friendly console interface.

Getting Started
Requirements

Java JDK 8 or higher.

Files

LibraryApp.java – main program.

Library.java – manages books, users, and borrowing.

Book.java – book class.

User.java – user class with roles.

AuthService.java – handles authentication.

How to Run

Open a terminal in the project folder.

Compile all Java files:

javac \*.java

Run the application:

java LibraryApp

At startup, choose:

1. Login – enter existing credentials.

2. Register – create a new member account.

Default Users
Username Password Role
admin admin123 ADMIN

Newly registered users are saved and remain available on subsequent runs.

Notes

Only admins can manage books and view all users.

Members can only manage their own borrowed books and password.

All data is stored in library.dat using Java serialization.
