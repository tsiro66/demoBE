# E-Commerce Demo Backend (Spring Boot)

This project is the complete backend API for a single-product e-commerce demo site. It is built with Spring Boot and PostgreSQL, focusing on a clean, scalable, and conversion-focused architecture.

It handles product data retrieval, order processing (guest checkout), and full-stack marketing event tracking.

## Tech Stack

* **Java 25**
* **Spring Boot 3.x**
* **Spring Data JPA (Hibernate)**: For database interaction and automatic table creation.
* **PostgreSQL**: As the relational database.
* **Maven**: For dependency management.
* **Lombok**: To reduce boilerplate code.

---

## 1. Prerequisites

Before you begin, ensure you have the following installed on your machine:

1. **Java JDK 25** (or higher)
2. **PostgreSQL** (e.g., v16, v17, or v18)
3. **An IDE** (like IntelliJ IDEA)
4. **A Database Tool** (like DBeaver or pgAdmin)

---

## 2. Setup & Configuration

### Step 1: Clone the Repository

```bash
git clone https://github.com/tsiro66/demoBE.git
cd demoBE
```

### Step 2: Create the Database

You must manually create the database in PostgreSQL before running the application.

1. Open pgAdmin or your preferred database tool.
2. Connect to your PostgreSQL server.
3. Right-click "Databases" → "Create" → "Database..."
4. Enter the database name: `demo_db`

### Step 3: Configure application.properties

Create a file named `application.properties` in `src/main/resources/`. Spring Boot will fail to start without this file.

Paste the following configuration and update your PostgreSQL port and password.

```properties
# Server configuration
server.port=8080

# PostgreSQL Datasource Configuration
# Make sure your port (e.g., 5433) and password are correct!
spring.datasource.url=jdbc:postgresql://localhost:5433/demo_db
spring.datasource.username=postgres
spring.datasource.password=YOUR_POSTGRES_PASSWORD

# JPA/Hibernate Configuration
# 'update' automatically creates/updates tables based on your @Entity classes
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Step 4: Run the Application

Open the project in your IDE (IntelliJ IDEA) and run the main `DemoBeApplication.java` file.

Hibernate will now connect to `demo_db` and automatically create the following tables:

- `product`
- `customer`
- `app_order` (renamed from `order` to avoid SQL conflicts)
- `order_item`

### Step 5: (CRITICAL) Insert the Product Data

The app needs a product with `id=1` to function.

Open the Query Tool for your `demo_db` in pgAdmin and run the following SQL command:

```sql
INSERT INTO product (id, name, description, price, stock_quantity, image_url)
VALUES (
    1, 
    'SmartWatch', 
    'The ultimate wearable for modern professionals. Features all-day battery life, heart-rate monitoring, and seamless connection to all major devices.', 
    149.99, 
    100, 
    'http://googleusercontent.com/image_collection/image_retrieva/11433173323137310151_0](https://mannaihome.com/cdn/shop/files/AIWROrbit_IDWhite_Background_45_View_black_031c22e1-3107-4870-bf17-6e8a24ac61b9.png?v=1752733415&width=1500'
);
```

The backend is now fully configured and ready to accept requests.

---

## 3. API Endpoints

| Method   | Endpoint                 | Description                                                  |
| -------- | ------------------------ | ------------------------------------------------------------ |
| **GET**  | `/api/v1/product`        | Retrieves the single product (ID=1).                         |
| **POST** | `/api/v1/orders`         | Places a new order (guest checkout). Requires an `OrderRequest` JSON body. |
| **POST** | `/api/v1/tracking/event` | Receives a `TrackingEventDTO` JSON body and logs the event to the console. |
