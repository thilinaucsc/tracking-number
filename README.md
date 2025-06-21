# 📦 Tracking Number Generator API

A scalable, stateless Spring Boot 3.x API that generates unique tracking numbers for parcels. 
It is built with Java 17, MongoDB for persistence, and Kafka for event-driven messaging. 
The API is horizontally scalable and compliant with production-grade standards such as observability, structured logging, and containerization.

---

## ✅ Features
- Stateless tracking number generation using SHA-256 hash
- MongoDB uniqueness check with unique index
- Kafka event publishing for generated tracking numbers
- X-Correlation-ID support for distributed tracing
- Prometheus-compatible actuator metrics
- Exception handling with global `@ControllerAdvice`

---

## 🔧 Technologies Used
- Java 17 + Spring Boot 3.x
- MongoDB (Spring Data)
- Apache Kafka
- Prometheus (via Spring Actuator)
- Docker, Kubernetes-ready

---

## 📘 API Endpoint
**POST** `/api/next-tracking-number`

### ✅ Request Parameters
| Parameter                | Type     | Example                | Description                              |
|--------------------------|----------|------------------------|------------------------------------------|
| `origin_country_id`      | String   | `MY`                   | ISO 3166-1 alpha-2 country code          |
| `destination_country_id` | String   | `ID`                   | ISO 3166-1 alpha-2 country code          |
| `weight`                 | Double   | `1.234`                | Parcel weight in KG                      |
| `customer_id`            | UUID     | `de619854-...`         | Customer UUID                            |
| `customer_name`          | String   | `RedBox Logistics`     | Customer name                            |
| `customer_slug`          | String   | `redbox-logistics`     | Slug version of customer name            |

### 🔁 Optional Headers
| Header             | Example UUID                          |
|--------------------|----------------------------------------|
| `X-Correlation-ID` | `8a2e1f50-70d8-4f59-91ad-f67d70c9e4d1` |

### 📥 Example Request
```http
URL - POST /api/next-tracking-number
request - 
{
  "trackingNumber": "ABC123XYZ789LMNO",
  "createdAt": "2025-06-19T10:00:00Z"
}
```

### 📤 Example Response
```json
{
  "trackingNumber": "ABC123XYZ789LMNO",
  "createdAt": "2025-06-19T10:00:00Z"
}
```

---

## 🧪 Run Tests
```bash
./mvnw test
```

---

## 🚀 Run Locally
```bash
./mvnw spring-boot:run
```

---

## 📦 Docker Build
```bash
docker build -t tracking-api .
```

---

## ☸️ Kubernetes Deployment
```bash
kubectl apply -f kubernetes/deployment.yaml
```

---

## 📡 Prometheus Metrics
Actuator endpoints:
```
/actuator/health
/actuator/metrics
```

---

# 📄 License
MIT License