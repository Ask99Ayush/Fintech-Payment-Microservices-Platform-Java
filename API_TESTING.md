# 🧪 Fintech Payment System API Testing Guide

Base URL:

```text
http://localhost:8080
```

All protected APIs require:

```http
Authorization: Bearer <token>
```

---

# 🔐 1. AUTH SERVICE

---

## ✅ Signup

### Request

```http
POST /api/auth/signup
Content-Type: application/json
```

### Body

```json
{
  "email": "user@gmail.com",
  "password": "123456",
  "fullName": "John Doe"
}
```

---

## ✅ Login

### Request

```http
POST /api/auth/login
Content-Type: application/json
```

### Body

```json
{
  "email": "user@gmail.com",
  "password": "123456"
}
```

### Copy JWT Token From Response

```json
{
  "token": "eyJhbGc..."
}
```

Use this token for all protected APIs.

---

# 👤 2. USER SERVICE

---

## ✅ Create / Update Profile

### Request

```http
POST /api/users/profile
Authorization: Bearer <token>
```

### Body

```json
{
  "fullName": "John Doe",
  "phone": "9999999999",
  "address": "New Delhi, India"
}
```

---

## ✅ Get Profile

### Request

```http
GET /api/users/profile
Authorization: Bearer <token>
```

---

# 💰 3. WALLET SERVICE

---

## ✅ Create Wallet

### Request

```http
POST /api/wallet/create
Authorization: Bearer <token>
```

---

## ✅ Get Wallet

### Request

```http
GET /api/wallet
Authorization: Bearer <token>
```

---

## ✅ Top Up Wallet

### Request

```http
POST /api/wallet/topup
Authorization: Bearer <token>
```

### Body

```json
{
  "amount": 1000.00
}
```

---

## ✅ Debit Wallet

### Request

```http
POST /api/wallet/debit
Authorization: Bearer <token>
```

### Body

```json
{
  "amount": 100.00,
  "idempotencyKey": "debit-001"
}
```

---

# 💳 4. PAYMENT SERVICE

---

## ✅ Send Payment

### Request

```http
POST /api/payments/send
Authorization: Bearer <token>
```

### Body

```json
{
  "receiverEmail": "receiver@gmail.com",
  "amount": 100.00,
  "idempotencyKey": "pay-001"
}
```

---

## ✅ Get Payment History

### Request

```http
GET /api/payments/history
Authorization: Bearer <token>
```

---

# 📒 5. LEDGER SERVICE

---

## ✅ Transaction History

### Request

```http
GET /api/ledger/history
Authorization: Bearer <token>
```

---

# 🔍 6. FRAUD SERVICE

> Internal service usually called by payment-service.

---

## ✅ Fraud Check

### Request

```http
POST /api/fraud/check
```

### Body

```json
{
  "email": "user@gmail.com",
  "amount": 100.00,
  "referenceId": "pay-001"
}
```

---

# 🧪 Recommended API Testing Order

## 1️⃣ Signup

```text
POST /api/auth/signup
```

---

## 2️⃣ Login

```text
POST /api/auth/login
```

Copy JWT token.

---

## 3️⃣ Create Profile

```text
POST /api/users/profile
```

---

## 4️⃣ Create Wallet

```text
POST /api/wallet/create
```

---

## 5️⃣ Top Up Wallet

```text
POST /api/wallet/topup
```

Add balance.

---

## 6️⃣ Send Payment

```text
POST /api/payments/send
```

Triggers:

- Fraud Check
- Wallet Debit
- Ledger Entries
- Kafka Event
- Notification Service

---

## 7️⃣ Check Ledger

```text
GET /api/ledger/history
```

---

## 8️⃣ Check Payment History

```text
GET /api/payments/history
```

---

# 🛡️ Fraud Rules

Transactions fail if:

- Amount > $10,000
- Duplicate referenceId
- More than 5 transactions/minute

---

# 🧰 Postman Setup

## Add Environment Variable

| Variable | Value |
|---|---|
| baseUrl | http://localhost:8080 |
| token | JWT token from login |

---

## Authorization Header

```http
Authorization: Bearer {{token}}
```

---

# 🐳 Start Services Before Testing

```bash
docker compose up --build
```

---

# ✅ Verify Services Running

```bash
docker ps
```

---

# 📜 View Logs

```bash
docker compose logs -f
```