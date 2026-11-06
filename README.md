# 🎓 Campus Lost & Found System

A JavaFX-based desktop application designed to simplify the process of reporting, searching for, and recovering lost belongings within a university campus. Built with Java 17 and MySQL, featuring role-based access and an administrator-managed claim approval workflow.

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-orange?logo=openjdk" alt="Java 17">
  <img src="https://img.shields.io/badge/JavaFX-17.0.2-blue" alt="JavaFX">
  <img src="https://img.shields.io/badge/Maven-3.8%2B-C71A36?logo=apachemaven" alt="Maven">
  <img src="https://img.shields.io/badge/MySQL-8.0%2B-4479A1?logo=mysql&logoColor=white" alt="MySQL">
  <img src="https://img.shields.io/badge/Status-Academic%20Project-purple" alt="Academic Project">
</p>

---

## 📌 Overview

Lost belongings are a common problem in university environments. Traditional lost-and-found processes often rely on word of mouth, paper records, or scattered communication, making it difficult to report and recover items efficiently.

The **Campus Lost & Found System** provides a centralized desktop application where students and staff can report lost or found belongings, search available records, and submit ownership claims.

An administrator reviews submitted claims and approves or rejects them to help ensure that belongings are returned to their rightful owners.

The project was developed as part of a Database Management Systems (DBMS) academic course project.

## ✨ Features

### 👤 User Features

- **Account Registration:** Create an account using your name, email, password, and optional phone number.
- **User Authentication:** Log in using registered credentials.
- **Report Lost Items:** Submit details of belongings that have been lost.
- **Report Found Items:** Register belongings found around campus.
- **Item Search:** Search records using keywords matching item titles, descriptions, and locations.
- **Search Filters:** Filter results by item type and current status.
- **Submit Ownership Claims:** Provide a description explaining why you believe an item belongs to you.
- **My Reports:** View items reported through your account.
- **Logout:** Return to the login screen.

### 🛡️ Administrator Features

- **Pending Claims Management:** View submitted ownership claims awaiting review.
- **Claim Approval:** Approve legitimate claims through a transactional database operation.
- **Claim Rejection:** Reject claims that cannot be verified.

### 🔐 Claim Validation

The application implements several checks before accepting an ownership claim:

- Users cannot claim their own reported items.
- Only items with an `ACTIVE` status can be claimed.
- A user cannot submit another pending claim for the same item.
- Administrators can approve or reject pending claims.

When a claim is approved, the application updates both the claim status and the corresponding item status within a single database transaction.

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Java 17 | Core application development |
| JavaFX 17.0.2 | Desktop graphical user interface |
| FXML | JavaFX UI support |
| MySQL 8.0+ | Relational database management |
| JDBC | Database connectivity |
| MySQL Connector/J 8.3.0 | MySQL JDBC driver |
| Apache Maven | Dependency management and build automation |
| CSS | Application interface styling |
| MySQL Workbench | Database administration and SQL execution |

The application uses plain JDBC and SQL for database operations. No ORM or backend framework is involved.

## 🏗️ Architecture

The application follows a simple three-layer architecture:

```text
┌──────────────────────────────────┐
│          Presentation Layer      │
│                                  │
│         JavaFX Application       │
│       Login, Dashboard, UI       │
└────────────────┬─────────────────┘
                 │
                 ▼
┌──────────────────────────────────┐
│          Data Access Layer       │
│                                  │
│         DAO Classes              │
│    CRUD Operations & Validation  │
└────────────────┬─────────────────┘
                 │
                 ▼
┌──────────────────────────────────┐
│          Database Layer          │
│                                  │
│             MySQL                │
│       Users, Items, Claims       │
└──────────────────────────────────┘
```

### Project Structure

```text
CampusLostFoundSystem/
│
├── pom.xml
├── README.md
│
└── src/
    └── main/
        ├── java/
        │   ├── app/
        │   │   ├── App.java
        │   │   └── Launcher.java
        │   │
        │   ├── dao/
        │   │   ├── AdminClaimDAO.java
        │   │   ├── ClaimDAO.java
        │   │   ├── ItemDAO.java
        │   │   ├── MyReportsDAO.java
        │   │   ├── RegisterDAO.java
        │   │   ├── SearchDAO.java
        │   │   └── UserDAO.java
        │   │
        │   ├── model/
        │   │   └── User.java
        │   │
        │   ├── ui/
        │   │   ├── AdminClaims.java
        │   │   ├── Dashboard.java
        │   │   ├── MyReports.java
        │   │   ├── Register.java
        │   │   ├── ReportItem.java
        │   │   ├── SearchItems.java
        │   │   ├── SubmitClaim.java
        │   │   └── Theme.java
        │   │
        │   └── util/
        │       └── DatabaseConnection.java
        │
        └── resources/
            ├── config.properties
            ├── schema.sql
            ├── sample_data.sql
            ├── queries.sql
            └── style.css
```

### Directory Responsibilities

- `app/` — Application initialization and login entry point.
- `dao/` — Database operations and business-related data validation.
- `model/` — Application data models.
- `ui/` — JavaFX screens and navigation.
- `util/` — Shared database connection utility.
- `resources/` — Database scripts, configuration, and styling.

## 🗄️ Database Design

The application uses MySQL with four primary tables:

| Table | Description |
|---|---|
| `users` | Stores registered users and their roles. |
| `categories` | Stores available item categories. |
| `items` | Stores lost and found item reports. |
| `claims` | Stores ownership claims submitted by users. |

### Entity Relationships

- One user can report multiple items.
- One category can be associated with multiple items.
- One item can have multiple ownership claims.
- One user can submit multiple claims.

### Item Categories

The database initializes six categories:

1. Electronics
2. Documents
3. Accessories
4. Clothing
5. Books
6. Other

### Status Values

**Item statuses:**
- `ACTIVE`
- `CLAIMED`
- `RETURNED` (available in the search filter, not currently assigned by the application)
- `CLOSED` (available in the search filter, not currently assigned by the application)

**Claim statuses:**
- `PENDING`
- `APPROVED`
- `REJECTED`

## ⚙️ Installation & Setup

### Prerequisites

Make sure you have the following installed:

- [Java JDK 17 or higher](https://www.oracle.com/java/technologies/downloads/)
- [Apache Maven 3.8 or higher](https://maven.apache.org/download.cgi)
- [MySQL Server 8.0 or higher](https://dev.mysql.com/downloads/mysql/)
- [MySQL Workbench](https://dev.mysql.com/downloads/workbench/) (recommended)

### 1. Clone the Repository

```bash
git clone https://github.com/DevSaboor/CampusLostFoundSystem.git
```

Navigate into the project directory:

```bash
cd CampusLostFoundSystem
```

### 2. Set Up the Database

Start your MySQL server.

Execute the database schema:

```bash
mysql -u root -p < src/main/resources/schema.sql
```

Alternatively, open `schema.sql` in MySQL Workbench and execute it.

This creates the `campus_lost_found` database, its four tables, and the default item categories.

### 3. Configure Database Connectivity

Open:

```text
src/main/resources/config.properties
```

Configure the following properties using your own MySQL credentials:

```properties
db.url=jdbc:mysql://localhost:3306/campus_lost_found?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
db.user=your_mysql_username
db.password=your_mysql_password
```

**Important:** Never commit real database credentials to a public repository.

### 4. Insert Sample Data (Optional)

To populate the database with demonstration records, execute:

```bash
mysql -u root -p < src/main/resources/sample_data.sql
```

The sample dataset includes demonstration users, item reports, and ownership claims.

These records are intended for local testing and demonstration only.

## ▶️ Running the Application

From the project root directory, execute:

```bash
mvn clean javafx:run
```

Maven will resolve the required dependencies and launch the JavaFX desktop application.

**Before launching, ensure:**
- MySQL Server is running.
- The database schema has been initialized.
- `config.properties` contains valid database credentials.

## 👥 User Roles

The application supports two roles:

| Permission | USER | ADMIN |
|---|:---:|:---:|
| Register and log in | ✅ | ✅ |
| Report lost items | ✅ | ✅ |
| Report found items | ✅ | ✅ |
| Search items | ✅ | ✅ |
| Submit ownership claims | ✅ | ✅ |
| View personal reports | ✅ | ✅ |
| Manage pending claims | ❌ | ✅ |
| Approve claims | ❌ | ✅ |
| Reject claims | ❌ | ✅ |

### Creating an Administrator Account

Administrator accounts are assigned manually through MySQL.

1. Register a normal account through the application.
2. Execute the following SQL statement:

```sql
USE campus_lost_found;

UPDATE users
SET role = 'ADMIN'
WHERE email = 'your_admin_email';
```

3. Log in again using the updated account.

The administrator dashboard will display the Manage Claims option.

## 📂 Additional SQL Resources

The project includes additional SQL scripts for database demonstration and testing.

| File | Description |
|---|---|
| `schema.sql` | Database and table creation, including default categories. |
| `sample_data.sql` | Sample users, items, and claims for testing. |
| `queries.sql` | 11 SQL examples covering search, retrieval, administration, reports, and integrity checks. |

These scripts can be executed through MySQL Workbench or the MySQL command-line client.

## ⚠️ Known Limitations

This project is an academic implementation and is not intended for production deployment in its current state.

Some existing limitations include:

- Passwords are currently stored in plain text rather than securely hashed.
- Administrator authorization is primarily enforced through UI visibility rather than comprehensive backend access checks.
- Users cannot edit or delete their existing reports through the application.
- There is no notification system for claim decisions.
- Item photographs and image uploads are not supported.
- Registration does not include comprehensive email or password-strength validation.
- `RETURNED` and `CLOSED` statuses are not currently assigned by the application.
- Automated tests have not yet been implemented.

These limitations represent potential areas for future development.

## 🚀 Future Improvements

Potential enhancements include:

- Implement secure password hashing.
- Introduce stronger role-based authorization.
- Add item image uploads and previews.
- Implement notifications for claim approval and rejection.
- Allow users to edit or withdraw their reports.
- Improve form validation and error handling.
- Add automated unit and integration tests.
- Improve concurrent claim handling and database integrity constraints.

## 🎓 Academic Context

**Project:** Campus Lost & Found System  
**Course:** Database Management Systems (DBMS)  
**Institution:** Mehran University of Engineering & Technology (MUET)  
**Department:** Software Engineering  

Developed as an academic database application project to demonstrate practical implementation of relational database concepts, JDBC connectivity, SQL operations, transaction management, and desktop application development.

---

<p align="center">
  <b>Built with Java ☕, JavaFX, and MySQL.</b>
  <br>
  <i>Making lost things easier to find.</i>
</p>
