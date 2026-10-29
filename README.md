# 🎓 Campus Lost and Found System

A desktop application built with Java, JavaFX, Maven, and MySQL to streamline the reporting, tracking, and claiming of lost items across a university campus.

## ✨ Features

* **User Login**: Account registration and login backed by the database.
* **Report Lost/Found Items**: Submit item descriptions, categories, and locations.
* **Search & Claim**: Search for items and submit ownership proof for claims.
* **My Reports**: View the items you have reported.
* **Admin Management**: Review submitted claims and approve or reject them.

## 📋 Prerequisites

* Java Development Kit (JDK) 17 or higher
* Apache Maven 3.8 or higher
* MySQL Server 8.0 or higher (MySQL Workbench is recommended for running the setup script)

## 📁 Project Structure

```text
CampusLostFoundSystem/
├── pom.xml
├── README.md
├── LICENSE
├── src/main/java/
│   ├── app/          App.java, Launcher.java       (startup and login)
│   ├── ui/           Dashboard, Register, ReportItem, SearchItems,
│   │                 SubmitClaim, MyReports, AdminClaims, Theme
│   ├── dao/          Data-access classes (JDBC queries)
│   ├── model/        User.java
│   └── util/         DatabaseConnection.java
└── src/main/resources/
    ├── schema.sql        Database and table setup
    ├── config.properties Database connection settings
    └── style.css         Application styling
```

## ⚙️ Setup & Installation

### 1. Clone the Repository

```bash
git clone https://github.com/DevSaboor/CampusLostFoundSystem.git
cd CampusLostFoundSystem
```

### 2. Create the Database

Run the SQL script `src/main/resources/schema.sql` in MySQL Workbench, or from the terminal:

```bash
mysql -u root -p < src/main/resources/schema.sql
```

The script creates the `campus_lost_found` database, its tables, and a set of default item categories. It is safe to run again. If you run it a second time, you may see a "duplicate entry" error for the categories, which you can ignore.

### 3. Configure the Database Connection

Open `src/main/resources/config.properties` and set your MySQL username and password:

```properties
db.url=jdbc:mysql://localhost:3306/campus_lost_found?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
db.user=your_mysql_username
db.password=your_mysql_password
```

Do not commit your real password to GitHub. Keep your local changes out of commits, or revert the file before pushing.

## ▶️ How to Run

### Option 1: Maven Plugin (Recommended for Development)

Run this from the project root (the folder that contains `pom.xml`):

```bash
mvn clean javafx:run
```

### Option 2: Standalone Executable JAR

Build the self-contained JAR:

```bash
mvn clean package
```

Run the generated file:

```bash
java -jar target/CampusLostFoundSystem-1.0-SNAPSHOT.jar
```

## 👤 Creating an Admin Account

The application has no admin sign-up screen. Create a normal account first, then give it the admin role in the database.

1. Start the application and click **Create Account**. Register with the email you want to use for the admin.
2. Connect to MySQL and run:

```sql
USE campus_lost_found;

UPDATE users SET role = 'ADMIN' WHERE email = 'your_admin_email';
```

3. Restart the application and log in. The **Manage Claims** button appears only for admin accounts.

The role value must be uppercase `ADMIN`.

## 🛠️ Troubleshooting

| Problem | Solution |
|---|---|
| "Database connection failed" or a login error | Confirm MySQL is running and that `config.properties` has the correct username and password. |
| `Unknown database 'campus_lost_found'` | Run `schema.sql` again (step 2). |
| `Access denied for user` | Check `db.user` and `db.password` in `config.properties`. |
| `Unable to find config.properties` | Make sure you're running Maven from the project root and that the file exists in `src/main/resources/`. |
| Can't see "Manage Claims" | The account isn't an admin yet. Run the `UPDATE` query in the admin section, then log out and back in. |
| Maven can't download dependencies | Check your internet connection. Maven downloads JavaFX and the MySQL driver the first time you build. |

## ⚠️ Security Note

Passwords are currently stored and compared as plain text in the database. This is suitable for learning and local testing only. Before using this application with real user data, passwords should be hashed (for example with BCrypt).