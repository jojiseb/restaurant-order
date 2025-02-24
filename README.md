# Restaurant Order Management API

## Overview
Spring Boot-based REST API for managing restaurant orders and menu items, using MongoDB as the database.

## Prerequisites
- Java 8
- Maven
- MongoDB
- Git

## Technologies
- Spring Boot 2.7.5
- Spring Data MongoDB
- Java 8
- Lombok
- Spring Validation

## Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/[YOUR_USERNAME]/restaurant-order.git
cd restaurant-order
```

### 2. Configure MongoDB
- Ensure MongoDB is installed and running
- Default connection settings in `application.properties`:
  ```properties
  spring.data.mongodb.uri=mongodb://localhost:27017/restaurantdb
  ```

### 3. Build the Project
```bash
mvn clean install
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

## API Endpoints

### Menu Items
- `POST /api/menu-items`: Create a new menu item
- `GET /api/menu-items`: Retrieve all menu items
  - Optional query parameter: `category`

### Orders
- `POST /api/orders`: Create a new order
- `GET /api/orders/{id}`: Retrieve a specific order

## Testing
Run unit tests:
```bash
mvn test
```

## Contributing
1. Fork the repository
2. Create a feature branch
3. Commit changes
4. Push to the branch
5. Create a Pull Request
```
