# Job Application Tracking System

A robust **Spring Boot** backend application designed to streamline the career hunt process. This system provides a centralized platform to manage job applications, track interview progress, and maintain a persistent history of professional opportunities.

---

## 🚀 Features

* **Application Management**: Create, view, update, and delete job application records.
* **Interview Lifecycle Tracking**: Log and monitor various interview stages (Technical, HR, Managerial).
* **Status Updates**: Real-time tracking of application statuses (e.g., Applied, Interviewing, Offered, Rejected).
* **Persistent Storage**: Full integration with **MySQL** via **JPA/Hibernate** for reliable data management.
* **RESTful API**: Clean, structured endpoints ready for frontend integration.

---

## 🛠 Tech Stack

| Layer | Technology |
| :--- | :--- |
| **Language** | Java |
| **Framework** | Spring Boot |
| **ORM** | Spring Data JPA / Hibernate |
| **Database** | MySQL |
| **Architecture** | REST API |

---

## 🏗 System Architecture

The application follows a standard **N-tier architecture** to ensure a clean separation of concerns:

1.  **Controller Layer**: Handles incoming REST requests and maps them to business logic.
2.  **Service Layer**: Contains the core business logic and validation rules for applications.
3.  **Repository Layer**: Manages data persistence and communication with the MySQL database.
4.  **Model Layer**: Defines the data entities and relationships.

---


## 📋 Prerequisites

Ensure you have the following installed before running the application:
* **Java Development Kit (JDK)** 17 or higher
* **Maven** 3.6+
* **MySQL Server** (Running on default port 3306)
