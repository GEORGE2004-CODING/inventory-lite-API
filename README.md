# Inventory Lite API

API REST para gestionar productos e inventario con historial de movimientos.

## 🚀 Funcionalidades

- Crear productos
- Aumentar y reducir stock
- Validación de stock negativo
- Historial de movimientos
- Paginación y sorting
- Filtros avanzados por tipo y fecha

## 🛠 Tecnologías

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven

## 📦 Endpoints principales

### Crear producto
POST /products

### Aumentar stock
POST /products/{id}/increase

### Reducir stock
POST /products/{id}/decrease

### Ver movimientos
GET /products/{id}/movements

## 🧠 Conceptos aplicados

- Lógica de negocio en servicios
- Excepciones personalizadas
- DTOs y Mappers
- Specification para filtros dinámicos
- Pageable y sorting

## 📌 Estado del proyecto
Proyecto educativo enfocado en buenas prácticas backend.
