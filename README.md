# Personal Diary Management System
The **Personal Diary Management System** is a desktop application developed using **Java Swing** and **MySQL** that allows users to securely store and manage personal notes.

Each user can create an account, log in, and access their **private diary**, ensuring data privacy and organization.


## Objectives

* Provide a secure login & registration system
* Allow users to maintain personal diary notes
* Store notes safely in a database
* Enable multi-user support
* Offer a simple and user-friendly interface



## Technologies Used

| Technology   | Purpose                 |
| ------------ | ----------------------- |
| Java (Swing) | GUI development         |
| MySQL        | Database storage        |
| JDBC         | Database connectivity   |
| Eclipse IDE  | Development environment |



##  Features

## User Authentication

✔ User Registration
✔ Login System
✔ Forgot Password
✔ Change Password
✔ Remember Me option

### Diary Management

✔ Add Notes
✔ Update Notes
✔ Delete Notes
✔ User-specific private diary
✔ Real-time note search

### 📌 Database Name:

```
diarydb
```


## How Pages Are Connected

* Login → Diary Page
* Login → Registration Page
* Diary → Change Password
* Diary → Logout → Login

##  How to Run the Project

## Step 1: Import Project

1. Open Eclipse
2. File → Import → Existing Projects into Workspace
3. Select project folder

## Step 2: Setup Database

1. Open MySQL Workbench
2. Run provided SQL scripts
3. Ensure database name is `diarydb`

## Step 3: Configure Database Connection

Update if needed:

```java
jdbc:mysql://localhost:3306/diarydb
username: root
password: root
```

## Step 4: Add MySQL Connector

* Add **mysql-connector-j.jar** to project build path

## Step 5: Run Application

Run:
