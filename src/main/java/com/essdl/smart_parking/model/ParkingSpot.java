package com.essdl.smart_parking.model;

import jakarta.persistence.*;

@Entity //annotaiton,this tells SpringBoot,this is not a normal class,but a class whose Table needs to be crreated,in DB!

@Table(name = "parking_spots")

    /*
    What happens in Model?
    -> Normally database mein tables banane ke liye hum CREATE TABLE parking_spots (...) SQL query likhte hain. Par Spring Boot mein Spring Data JPA (Hibernate) use hota hai. Iska matlab hum sirf ek normal Java class likhte hain, aur Spring Boot us class ko dekh kar peeche database mein khud-ba-khud table bana deta hai! Is process ko ORM (Object-Relational Mapping) ya Entity Mapping kehte hain.
    */



public class ParkingSpot {
    @Id //Primary Key,in every table! (unique)
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Jaise jaise nya vehicle park hoga,atomatic db automaticall update hota jayega!

    private Long id;

    private String spotNumber;  // Example: "A1", "B1"
    private String vehicleType; // "CAR" or "BIKE"
    private boolean isOccupied; // false = khali hai, true = book ho gaya

    // Default Constructor(Imp for JPA) // hidernate will installize this with the persistance data 
    public ParkingSpot() {
    }

    public ParkingSpot(String spotNumber, String vehicleType, boolean isOccupied) {
        this.spotNumber = spotNumber;
        this.vehicleType = vehicleType;
        this.isOccupied = isOccupied;
    }

    // Getters and Setters (Encapsulation)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSpotNumber() { return spotNumber; }
    public void setSpotNumber(String spotNumber) { this.spotNumber = spotNumber; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public boolean isOccupied() { return isOccupied; }
    public void setOccupied(boolean occupied) { isOccupied = occupied; }

}
