# 🚗 Smart Parking Management System

[![Java](https://img.shields.io/badge/Java-17%2B-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Database](https://img.shields.io/badge/Database-H2%20In--Memory-blue.svg)](https://www.h2database.com/)
[![Build](https://img.shields.io/badge/Build-Maven-red.svg)](https://maven.apache.org/)

A robust, enterprise-grade RESTful backend application engineered with **Java** and **Spring Boot** for real-time vehicular slot allocation, status tracking, automated billing, and reservation workflows[cite: 2, 3, 4].

Developed as part of the **Essential Skills Development Lab (ESSDL)** Mini-Project curriculum[cite: 1, 3].

---

## 📌 Problem Statement & Overview
Modern urban facilities frequently struggle with manual parking assignments, traffic bottlenecks at entry gates, and billing discrepancies. The **Smart Parking Management System** solves this by offering an automated backend engine that:
* Dynamically monitors slot availability in real time across vehicle categories (`CAR` and `BIKE`).
* Allocates the nearest free spot and logs entry timestamps automatically upon vehicle arrival.
* Releases occupied slots instantly upon checkout, accurately computing parking dues based on elapsed duration.
* Maintains an audit-compliant, persistent ledger of all past and active booking transactions.

---

## 🏗️ Architecture & Design
The system strictly enforces standard enterprise **Layered Architecture** principles, maintaining clean separation of concerns:

```text
[ Client / Postman / Frontend ]
               │
               ▼
   [ REST Controller Layer ]     <-- Request routing, parameter parsing & HTTP status codes
               │
               ▼
       [ Service Layer ]         <-- Business rules, slot allocation logic & fee calculations
               │
               ▼
     [ Repository Layer ]        <-- Spring Data JPA abstractions for database interactions
               │
               ▼
     [ H2 In-Memory RDBMS ]      <-- Relational data persistence (PARKING_SPOTS, BOOKINGS)
