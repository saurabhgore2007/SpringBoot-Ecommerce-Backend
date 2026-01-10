# 🌟 Spring Boot E‑Commerce Backend Project

Welcome to the **Spring Boot E‑Commerce Backend** project!  
This repository is designed to showcase a **production‑grade, secure, and scalable backend** built with Java 17, Spring Boot, JPA/Hibernate, and JWT authentication.  

✨ Highlights:
- Modular architecture with clear separation of concerns  
- Profile‑based configuration (dev, test, prod) for predictable environments  
- Secure JWT authentication and role‑based access control  
- RESTful APIs for products, cart, orders, and users  
- Ready for frontend integration (React, Android, or any client)  
- Professional documentation for easy onboarding and collaboration  

Whether you’re a developer exploring backend design, a recruiter reviewing portfolio projects, or a contributor looking to collaborate — this project is built to be **clear, professional, and developer‑friendly**. 🚀

# 🛒 Spring Boot E-Commerce Backend

This Spring Boot E‑commerce Backend is an intermediate‑level project designed to provide a **robust, scalable foundation** for online retail applications. It features a **comprehensive API suite** and a **secure architecture** optimized for multi‑environment deployment.

## ✨ Key Features:
- JWT authentication & role‑based access
- Multi‑profile configuration (dev, test, prod)
- 25 REST APIs for managing users, products, carts, orders, payments, and reviews
- Entities and relations with JPA/Hibernate
- Controllers, services, repositories (JPQL and method naming)
- Custom exceptions & global error handling
- CORS configuration for frontend integration
- Multi‑environment setup with properties and YAML
- Multiple databases across profiles
 Multiple databases across profiles

## 📂 Project Structure

- **src/main/java/com/ecommerce**
  - config → App configuration (CORS, JWT filters, beans)
  - controller → REST endpoints (Auth, Products, Cart, Orders, Users)
  - dto → Request/response payloads
  - entity → JPA entities (User, Product, Cart, Order, etc.)
  - exception → Custom exceptions and global handler
  - repository → JPA repositories (method naming, JPQL)
  - security → JWT provider, filters, and config
  - service → Business logic layer
- **src/main/resources**
  - application.yml → Multi-profile config (dev/test/prod)
- **src/test/java/com/ecommerce** → Unit and integration tests
- **pom.xml** → Maven build and dependencies
- **README.md** → Documentation

## 🗂️ Domain Model

The backend models key components of an e-commerce system with well-defined relationships:

- **User**
  - Fields: `id`, `email`, `password`, `role`, `createdAt`
  - Relations: One-to-One with `Cart`, One-to-Many with `Order`, One-to-Many with `ProductReview`

- **Product**
  - Fields: `id`, `name`, `sku`, `description`, `price`, `stock`, `isActive`, `createdAt`, `updatedAt`
  - Relations: Many-to-One with `Category`, One-to-Many with `ProductReview`, Many-to-Many via `OrderItem`

- **Category**
  - Fields: `id`, `name`, `slug`, `parentId`
  - Relations: One-to-Many with `Product`

- **Cart**
  - Fields: `id`, `userId`, `totalAmount`, `updatedAt`
  - Relations: One-to-Many with `CartItem`

- **CartItem**
  - Fields: `id`, `cartId`, `productId`, `quantity`, `priceAtAdd`
  - Relations: Many-to-One with `Cart`, Many-to-One with `Product`

- **Order**
  - Fields: `id`, `userId`, `status`, `totalAmount`, `createdAt`, `paymentId`
  - Relations: One-to-Many with `OrderItem`, Many-to-One with `User`

- **OrderItem**
  - Fields: `id`, `orderId`, `productId`, `quantity`, `unitPrice`
  - Relations: Many-to-One with `Order`, Many-to-One with `Product`

- **Payment**
  - Fields: `id`, `orderId`, `provider`, `amount`, `status`, `transactionRef`, `createdAt`
  - Relations: One-to-One with `Order`, Many-to-One with `User` (optional)

- **ProductReview**
  - Fields: `id`, `productId`, `userId`, `rating`, `comment`, `createdAt`
  - Relations: Many-to-One with `Product`, Many-to-One with `User`

## ⚙️ Tech Stack

- **Language:** Java 17+
- **Framework:** Spring Boot
- **Security:** Spring Security, JWT
- **Database:**
  - PostgreSQL / MySQL (development & production)
  - H2 (testing)
- **Build Tool:** Maven
- **Libraries & Tools:**
  - Lombok (boilerplate reduction)
  - Validation (input checks)
  - Spring Data JPA (repositories, JPQL)
  - Actuator (optional monitoring)
 
## 🚀 Setup & Installation

### 1. Prerequisites
- Java 17+
- Maven 3+
- MySQL (for dev/prod)
- H2 (for test, auto-configured)


### 2. Clone the repository
```bash
git clone https://github.com/saurabhgore2007/SpringBoot-Ecommerce-Backend.git
cd ecommerce-backend
```

### 3. Configure environment
Update your profile-specific configuration files with correct database credentials and JWT secrets:

- **Dev (`application-dev.yml`)**
  - MySQL database on port 3306  
  - CORS allowed for `http://localhost:3000` (React frontend)  
  - JWT secret and issuer set for development

- **Test (`application-test.yml`)**
  - Uses H2 in-memory DB  
  - Auto schema creation/drop for isolated testing  
  - Short JWT expiry for test runs

- **Prod (`application-prod.yml`)**
  - MySQL with production credentials  
  - JWT secret externalized via environment variable  
  - Restrict CORS to your deployed frontend domain

 ### 4. Build the project
  ```bash
  mvn clean install
  ```

 ### 5. Run with profiles
  By default, the app runs on http://localhost:8080.
  You can activate specific profiles:
   - Development
  mvn spring-boot : run -Dspring-boot.run.profiles=dev
   - Base URL → http://localhost:8081/api
   - Testing
  mvn spring-boot : run -Dspring-boot.run.profiles=test
   - Base URL → http://localhost:8082/api
   - Production
  mvn spring-boot : run -Dspring-boot.run.profiles=prod
   - Base URL → http://localhost:8080/api (or http://yourdomain.com/api)

  
 ### 6. Access APIs
   Use Postman or any REST client to call endpoints.
   Example:
   POST http://localhost:8081/api/auth/register

 ### 7. Frontend Integration
   - Dev profile allows requests from http://localhost:3000 (React frontend).
   - In production, update allowed origins to your deployed frontend domain.

 ## 🛒 Cart & Inventory Management

 Our e‑commerce backend ensures **accurate inventory tracking** by synchronizing cart operations with product stock:

 - **Add to Cart** → Product stock decreases by the quantity added.  
 - **Update Cart Item Quantity** →  
   - If quantity increases, stock decreases accordingly.  
   - If quantity decreases, stock is restored.  
 - **Remove Item from Cart** → Product stock is restored by the removed quantity.  
 - **Clear Cart** → All items are removed and their stock is fully restored.  

 ### 🔧 Why this matters
 - Prevents **overselling** by reserving stock when items are added.  
 - Releases stock back to inventory when items are removed or carts are abandoned.  
 - Ensures **transactional safety** with `@Transactional` — if any operation fails, both cart and stock changes roll back.  
 - Demonstrates **real-world business logic** beyond simple CRUD.

 ### 📌 Example Flow
 1. User adds 2 units of Product A → stock decreases from 10 → 8  
 2. User updates quantity to 3 → stock decreases from 8 → 7  
 3. User removes Product A → stock restored → 10  
 4. User clears cart → all reserved stock is restored

## 🔑 API Endpoints (Highlights)

| Area       | Method | Endpoint                        | Purpose                        |
|------------|--------|---------------------------------|--------------------------------|
| Auth       | POST   | `/api/auth/register`            | Register new user              |
| Auth       | POST   | `/api/auth/login`               | Login and issue JWT            |
| Users      | GET    | `/api/users/me`                 | Get current user profile       |
| Categories | POST   | `/api/categories`               | Create category (ADMIN)        |
| Categories | GET    | `/api/categories`               | List categories                |
| Categories | GET    | `/api/categories/{id}`          | Get category by id             |
| Products   | POST   | `/api/products`                 | Create new product  (ADMIN)    |
| Products   | GET    | `/api/products`                 | List products with filters     |
| Products   | GET    | `/api/products/{id}`            | Get product details            |
| Products   | PUT    | `/api/products/{id}`            | Update product (ADMIN)         |
| Products   | DELETE | `/api/products/{id}`            | Soft delete (ADMIN)            |
| Reviews    | POST   | `/api/products/{id}/reviews`    | Add review (CUSTOMER)          |
| Reviews    | GET    | `/api/products/{id}/reviews`    | List reviews                   |
| Cart       | GET    | `/api/cart`                     | Get current cart               | 
| Cart       |POST    | `/api/cart/items`               | Add item to cart               | 
| Cart       |PUT     | `/api/cart/items/{itemId}`      | Update item quantity           |
| Cart       |DELETE  | `/api/cart/items/{itemId}`      | Remove item                    |
| Cart       |DELETE  | `/api/cart/items/clear`               | Clear cart                     |
| Orders     |POST    | `/api/orders`                   | Create order from cart         |
| Orders     |GET     | `/api/orders`                   | List my orders                 | 
| Orders     |GET     | `/api/orders/{id}`              | Get order details              |
| Orders     |PATCH   | `/api/orders/{id}/status`       | Update status (ADMIN)          |
| Payments   |POST    | `/api/orders/{id}/payments`     | Initiate payment               |
| Payments   |GET     | `/api/orders/{id}/payments`     | Get payment status             |
| Admin      |GET     | `/api/admin/metrics`            | Aggregated metrics (ADMIN)     |

## 🧪 Postman Setup

To test APIs quickly, you can use Postman with the following setup:

### Environment Variables
- `baseUrl` → `http://localhost:8081/api` (dev), `http://localhost:8082/api` (test), `http://localhost:8080/api` (prod)
- `token` → automatically set after login

### Example Test Script
For the `/login` endpoint, add this script under **Tests** in Postman:

```javascript
const json = pm.response.json();
if (json.token && json.token.includes(".")) {
    pm.environment.set("token", json.token);
    console.log("Token saved:", json.token);
} else {
    console.warn("Login response did not contain a valid JWT.");
}
```

## 📬 Postman API Collection

You can test all backend endpoints using our Postman collection:

- [📦 Download Collection JSON](SpringBoot-Ecommerce-Backend/tree/main/ecommerce/postman/Ecommerce_Application.postman_collection.json)
- [🔗 View on Postman](https://saurabhgore44-134748.postman.co/workspace/Saurabh-Gore's-Workspace~15a1118a-a753-4318-8a61-4c8955de6b2f/collection/49247456-f033d9b7-9428-4546-b49a-915a8d077799?action=share&creator=49247456&active-environment=49247456-d3e13478-fc7e-4d46-96fb-484b4217bce2)

> Includes endpoints for users, products, cart, orders, payments, and admin metrics.

### ▶️ How to Import into Postman
1. Download the collection JSON from the link above.
2. Open Postman → click **Import**.
3. Select the downloaded file.
4. Start testing the APIs with the included requests.

## 📡 Sample APIs

Here are some example requests you can try with Postman or curl.  
All routes are prefixed with `/api`.

### 🔑 Authentication

- **POST** `/auth/register` → Register a new user  
- **POST** `/auth/login` → Authenticate and receive JWT token  
- **GET** `/auth/me` → Get current user details (requires JWT)

**Register a new user**  
Content-Type: application/json
```http
POST http://localhost:8081/api/auth/register
```
```http
{
  "email": "ecommerce@example.com",
  "password": "securePass123",
  "role": "ADMIN"
}
```
or
```http
{
  "email": "ecommerce@example.com",
  "password": "securePass123",
  "role": "CUSTOMER"
}
```
**Login a user if registered**  
Content-Type: application/json
```http
POST http://localhost:8081/auth/login
```
```http
{
   "email": "ecommerce@example.com",
   "password": "securePass123"
}
```
**Cart Add Request**  
Content-Type: application/json
```http
POST http://localhost:8081/api/cart/items?productId=1&qty=10
```
**Cart Items Added Respomse**  
content Type: application/json
```http
{
    "id": 1,
    "items": [
        {
            "id": 1,
            "productId": 1,
            "productName": "",
            "quantity": 0,
            "priceAtAdd":0.00
        }
        ]
    "totalAmount": 0.00,
    "updatedAt": "2026-01-10T10:04:16.262142+05:30"
}
```

**Admin Dashboard Aggregations**   
Content-Type: response/json
```http
GET http://localhost:8081/api/admin/metrics
```
```http
{
    "totalRevenue": 0.00,
    "topProducts": [
        {
            "name": "",
            "soldQuantity": 0,
            "revenue": 0.00
        }
    ],
    "ordersByStatus": {
        "SHIPPED": 0,
        "CREATED": 0,
        "PAID": 0
    }
}
```

### ✅ This is the **complete Setup & Installation section** — no shortcuts, all steps included.  

## 🎉 Happy Ending

Thanks for checking out this project!  
Building this e‑commerce backend has been a journey of learning, sharing, and creating something useful for developers and learners alike.  

If you’ve made it this far:
- 🚀 You can run the app locally
- 🛒 You can test APIs with Postman
- 🔑 You can explore secured endpoints with JWT
- 🤝 You can even contribute and make it better

Every contribution, feedback, or even a ⭐ on GitHub helps keep the project alive and growing.  
Let’s keep coding, keep learning, and keep building amazing things together!

---




