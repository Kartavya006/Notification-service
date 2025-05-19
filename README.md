# 📬 Notification Service

A Spring Boot-based backend service that allows sending notifications to users through **Email**, **SMS**, and **in-app messages**. It uses **RabbitMQ** for asynchronous message processing and **Spring Retry** for handling failures.

---

## 🚀 Features

- ✅ Send notification: `POST /notifications`
- ✅ Retrieve user notifications: `GET /notifications/users/{id}`
- ✅ Supports:
  - 📧 Email (via Gmail SMTP)
  - 📲 SMS (via Twilio)
  - 🗃️ In-app (stored in PostgreSQL)
- ✅ Asynchronous processing with RabbitMQ
- ✅ Retry failed messages up to 3 times using Spring Retry
- ✅ Tracks status (`pending`, `sent`, `failed`) of each notification

---

## 🛠️ Setup Instructions

### ✅ Prerequisites

- Java 17+
- Maven
- PostgreSQL (running locally)
- RabbitMQ (running locally)
- Gmail account with **App Password**
- Twilio account (Trial is fine)
- IntelliJ or any Java IDE

---

### ✅ 1. Clone the Project

```bash
git clone https://github.com/yourusername/notification-service.git
cd notification-service
```

---

### ✅ 2. PostgreSQL Setup

1. Start PostgreSQL
2. Create the database:
```sql
CREATE DATABASE notifications;
```

---

### ✅ 3. RabbitMQ Setup

Either install it locally or run via Docker:
```bash
docker run -d --hostname rabbit --name rabbit \
-p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

Then access the management dashboard:  
[http://localhost:15672](http://localhost:15672)  
Login: `guest / guest`

---

### ✅ 4. Configure Application Properties

Rename:
```bash
src/main/resources/application.properties.example → application.properties
```

Then update it:

```properties
# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/notifications
spring.datasource.username=postgres
spring.datasource.password=yourpassword

# RabbitMQ
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672

# Email via Gmail SMTP
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your.email@gmail.com
spring.mail.password=your_gmail_app_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# Twilio (get from Twilio console)
twilio.account.sid=ACxxxxxxxxxxxxxxxxxxxxxxxxxxxx
twilio.auth.token=xxxxxxxxxxxxxxxxxxxxxxxxxxxx
twilio.phone.number=+1XXXXXXXXXX
```

---

### ✅ 5. Build and Run the App

```bash
./mvnw clean install
./mvnw spring-boot:run
```

---

## 🔍 API Usage

### ➕ Send a Notification
```http
POST http://localhost:8080/notifications
Content-Type: application/json

{
  "userId": 1,
  "type": "sms",      // or "email", "inapp"
  "message": "Hello from Notification Service!"
}
```

### 📥 Get Notifications by User
```http
GET http://localhost:8080/notifications/users/1
```

---

## 📌 Assumptions Made

- The `userId` maps to contact info **manually**, not fetched from a user service.
- Twilio (Free Tier) only allows sending SMS to **verified phone numbers**.
- Email is sent using **Gmail SMTP with App Passwords**, not OAuth.
- All notifications are processed asynchronously using **RabbitMQ**.
- Retry logic is handled using **Spring Retry**, with up to 3 attempts.
- In-app notifications are stored in PostgreSQL and retrieved via API.
- This project is backend-only (no UI/frontend).

---
