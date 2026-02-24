# 📘 Personal Diary Management System

## 📌 Project Overview

The **Personal Diary Management System** is a desktop application developed using **Java Swing** and **MySQL** that allows users to securely store and manage personal notes.

Each user can create an account, log in, and access their **private diary**, ensuring data privacy and organization.

---

## 🎯 Objectives

* Provide a secure login & registration system
* Allow users to maintain personal diary notes
* Store notes safely in a database
* Enable multi-user support
* Offer a simple and user-friendly interface

---

## 🛠 Technologies Used

| Technology   | Purpose                 |
| ------------ | ----------------------- |
| Java (Swing) | GUI development         |
| MySQL        | Database storage        |
| JDBC         | Database connectivity   |
| Eclipse IDE  | Development environment |

---

## ✨ Features

### 🔐 User Authentication

✔ User Registration
✔ Login System
✔ Forgot Password
✔ Change Password
✔ Remember Me option

### 📒 Diary Management

✔ Add Notes
✔ Update Notes
✔ Delete Notes
✔ User-specific private diary
✔ Real-time note search

### 🎨 User Interface

✔ Modern GUI design
✔ Sidebar note titles
✔ Dark mode toggle 🌙
✔ Welcome dashboard
✔ Logout system

---

## 🧩 System Workflow

1. User registers an account
2. User logs in with username & password
3. System loads personal diary notes
4. User can add, update, delete notes
5. Notes are stored per user
6. User can logout securely

---

## 🗄 Database Structure

### 📌 Database Name:

```
diarydb
```

### 📌 users table

```sql
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(50)
);
```

### 📌 notes table

```sql
CREATE TABLE notes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    content TEXT,
    user_id INT
);
```

---

## 🔗 How Pages Are Connected

* Login → Diary Page
* Login → Registration Page
* Diary → Change Password
* Diary → Logout → Login

Navigation is handled using:

```java
new PageName();
dispose();
```

---

## 🚀 How to Run the Project

### ✅ Step 1: Import Project

1. Open Eclipse
2. File → Import → Existing Projects into Workspace
3. Select project folder

### ✅ Step 2: Setup Database

1. Open MySQL Workbench
2. Run provided SQL scripts
3. Ensure database name is `diarydb`

### ✅ Step 3: Configure Database Connection

Update if needed:

```java
jdbc:mysql://localhost:3306/diarydb
username: root
password: root
```

### ✅ Step 4: Add MySQL Connector

* Add **mysql-connector-j.jar** to project build path

### ✅ Step 5: Run Application

Run:

```
frame1.java
```

---

## 📂 Project Structure

```
Dairy/
│
├── frame1.java           (Login Page)
├── Register.java         (User Registration)
├── DiaryDBGU.java        (Diary Dashboard)
├── ChangePassword.java   (Password Change)
├── ModernMessageBox.java (Custom Dialogs)
```

---

## 🔐 Security Notes

* PreparedStatement used to prevent SQL injection
* Each note linked with user_id for privacy
* Multi-user data separation implemented

---

## 🎓 Learning Outcomes

Through this project, the following concepts were learned:

* Java Swing GUI development
* JDBC database connectivity
* MySQL database design
* User authentication system
* Multi-user data handling
* Event-driven programming

---

## 🔮 Future Enhancements

* Password encryption (BCrypt)
* Note categories & tags
* Export notes to PDF
* Cloud backup
* Mobile app version
* Profile picture support

---


