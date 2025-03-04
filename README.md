# **Appointment Booking API**
🚀 **A backend API for managing sales manager appointments. Customers can check available time slots based on specific criteria.**

## 📌 **Overview**
This project is an **appointment booking system** where customers can check available slots for scheduling meetings with **sales managers**.  
The system ensures that:
- ✅ Time slots may overlap (e.g., 10:30 - 11:30, 11:00 - 12:00, 11:30 - 12:30).
- ✅ A sales manager cannot be double-booked for overlapping slots.
- ✅ Customers are matched with sales managers based on:
  - Language (German, English)
  - Product expertise (SolarPanels, Heatpumps)
  - Customer rating (Gold, Silver, Bronze)
- Time slots may overlap, but a sales manager cannot be double-booked.

## 🚀 **Tech Stack**
This project uses **modern backend technologies** to ensure performance and maintainability:
- **Java 21** 🔹 Latest Java features for better performance
- **Spring Boot 3** ⚡ Fast, efficient REST API framework
- **PostgreSQL** 🗄️ Relational database for storing appointments
- **Hibernate & JPA** 📜 ORM for database interactions
- **Docker & Docker Compose** 🐳 Containerized deployment
- **Testcontainers** 🧪 Integration testing with PostgreSQL
- **Lombok** 📝 Less boilerplate code
- **OpenAPI / Swagger** 📖 API documentation & testing
- **FlywayDB** 🏦 Database migrations

---

## 🔧 **Setup & Run**
You can **build and run** this project using **Maven** and **Docker**.

### **1️⃣ Clone the repository**
```
git clone https://github.com/wagner-buettner/booking-api.git
cd booking-api
```

### **2️⃣ Start the local database if necessary**
```
cd database
docker build -t enpal-coding-challenge-db .
docker run --name enpal-coding-challenge-db -p 5432:5432 -d enpal-coding-challenge-db
cd ..
```

### **2️⃣.1️⃣ Build the API project**
```
mvn clean package
```

### **3️⃣ Run FlywayDB (to create indexes)**
```
mvn flyway:baseline
mvn flyway:migrate
```

### **3️⃣ Run the Project inside de IDE or through the terminal**
```
mvn spring-boot:run
```

### 🚀 The API will be available at: http://localhost:3000

## 📖 **API Documentation (Swagger)**
Once the application is running, open Swagger UI to explore and test the API: http://localhost:3000/swagger-ui/index.html

## 🛠 **Testing with cURL**
To check available slots for a customer, use the following cURL request:
```
curl -X 'POST' \
  'http://localhost:3000/calendar/query' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
    "date": "2024-05-03",
    "products": ["SolarPanels", "Heatpumps"],
    "language": "German",
    "rating": "Gold"
}'
```

### ✅ This will return a list of available slots:
```
[
  {
    "availableCount": 2,
    "startDate": "2024-05-03T11:30:00Z"
  },
  {
    "availableCount": 1,
    "startDate": "2024-05-03T11:00:00Z"
  },
  {
    "availableCount": 1,
    "startDate": "2024-05-03T10:30:00Z"
  }
]
```

## 📂 **Project Structure**
```
booking-api
│── src/main/java/com/webuildit/bookingapi
│   ├── config/               # Configuration classes
│   ├── controller/           # REST controllers
│   ├── exception/            # Custom exceptions
│   ├── model/                # JPA entities
│   ├── repository/           # Spring Data JPA repositories
│   ├── service/              # Business logic layer
│   ├── dto/                  # Data Transfer Objects (DTOs)
│── src/test/java/com/webuildit/bookingapi
│   ├── controller/           # Unit tests for controllers
│   ├── service/              # Unit tests for services
│   ├── model/                # Unit tests for models
│── docker-compose.yml        # Containerized setup
│── Dockerfile                # API container build
│── README.md                 # Project documentation
```

## 📌 **Business Rules**
- Each time slot is exactly 1 hour long.
- Slots may overlap, meaning multiple slots can exist at the same time.
- Sales managers cannot be double-booked (if a manager is booked at 10:30 - 11:30, they cannot be booked for 11:00 - 12:00).
- Customers can only book a sales manager if they match all three criteria:
  - ✅ Language (German, English)
  - ✅ Product expertise (SolarPanels, Heatpumps)
  - ✅ Customer rating (Gold, Silver, Bronze)

## 🏗 **Future Improvements**
- ✅ Implement caching (e.g., Redis) to reduce database queries.
- ✅ Add pagination when listing available slots.
- ✅ Implement Rate Limiting to prevent abuse.
- ✅ Add Authentication to secure endpoints.

