# 💳 Fintech Payment System

A production-grade microservices-based payment platform built using Spring Boot, Kafka, Docker, PostgreSQL, and JWT authentication.

---

# 🏗️ Architecture

```text
Client
   ↓
API Gateway (8080)
   ↓
Microservices
   ├── Auth Service
   ├── User Service
   ├── Wallet Service
   ├── Payment Service
   ├── Ledger Service
   ├── Fraud Service
   └── Notification Service
            ↓
         Kafka
```

---

# 🚀 Services

| Service | Port |
|---|---|
| API Gateway | 8080 |
| Auth Service | 8081 |
| User Service | 8082 |
| Wallet Service | 8083 |
| Payment Service | 8084 |
| Ledger Service | 8085 |
| Fraud Service | 8086 |
| Notification Service | 8087 |

---

# 🛠️ Tech Stack

- Spring Boot 4.0.6
- Spring Cloud Gateway
- Spring Security + JWT
- Spring Kafka
- OpenFeign
- PostgreSQL (Neon Cloud)
- Docker + Docker Compose
- Apache Kafka
- Zookeeper

---

# ⚙️ Features

- JWT Authentication & Authorization
- API Gateway Routing
- Kafka Event-Driven Architecture
- Wallet Management
- Payment Transfers
- Fraud Detection
- Append-Only Ledger
- Idempotent Payments
- Pessimistic Locking
- Compensating Transactions
- Dockerized Infrastructure

---

# 📦 Project Structure

```text
fintech-payment-system/
│
├── api-gateway/
├── auth-service/
├── user-service/
├── wallet-service/
├── payment-service/
├── ledger-service/
├── fraud-service/
├── notification-service/
│
├── docker-compose.yml
└── README.md
```

---

# ▶️ Running the Project

## 1️⃣ Clone Repository

```bash
git clone <repository-url>
cd fintech-payment-system
```

---

## 2️⃣ Build All Services

```bash
mvn clean package -DskipTests
```

---

## 3️⃣ Start Everything

```bash
docker compose up --build
```

---

# 🐳 Docker Commands

## Stop All Containers

```bash
docker compose down
```

## Restart Containers

```bash
docker compose restart
```

## View Running Containers

```bash
docker ps
```

## View Logs

```bash
docker compose logs -f
```

---

# 🔄 Payment Flow

```text
POST /api/payments/send
        ↓
Fraud Check
        ↓
Debit Sender Wallet
        ↓
Record Debit Entry
        ↓
Credit Receiver Wallet
        ↓
Record Credit Entry
        ↓
Publish Kafka Event
        ↓
Notification Service Consumes Event
        ↓
Email Sent
```

---

# 🛡️ Key Design Decisions

## ✅ Idempotency

Duplicate requests return cached responses.

## ✅ Pessimistic Locking

Prevents concurrent wallet update issues.

## ✅ Append-Only Ledger

No UPDATE or DELETE operations.

## ✅ Compensating Transactions

Automatic refund on failure.

## ✅ JWT Security

Authentication enforced at Gateway level.

---

# 📈 Future Improvements

- Kubernetes Deployment
- Redis Caching
- Circuit Breakers
- Distributed Tracing
- Rate Limiting
- CI/CD Pipelines
- Monitoring & Observability
- Multi-Currency Support

---

# 👨‍💻 Author

Built as a scalable fintech microservices backend architecture project using modern distributed system practices.