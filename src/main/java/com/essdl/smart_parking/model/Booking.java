package com.essdl.smart_parking.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vehicleNumber; // Example: "MH12AB1234"

    @ManyToOne
    @JoinColumn(name = "spot_id")
    private ParkingSpot spot;

    /*
    ek Parking Spot (C1) par aaj subah ek gadi park hui, wo chali gayi; shaam ko dusri gadi park hui. Matlab Ek hi Parking Spot (One) ki alag-alag time par multiple Bookings (Many) ho sakti hain!
    Isliye Booking se ParkingSpot ka rishta @ManyToOne hai. Database mein ye spot_id naam ki Foreign Key bana dega.
     */

    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double totalFee;
    private String status; // "Occupied" OR "Empty"

    public Booking() {
    }

    // Getters and Setters-methods
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public ParkingSpot getSpot() { return spot; }
    public void setSpot(ParkingSpot spot) { this.spot = spot; }

    public LocalDateTime getEntryTime() { return entryTime; }
    public void setEntryTime(LocalDateTime entryTime) { this.entryTime = entryTime; }

    public LocalDateTime getExitTime() { return exitTime; }
    public void setExitTime(LocalDateTime exitTime) { this.exitTime = exitTime; }

    public double getTotalFee() { return totalFee; }
    public void setTotalFee(double totalFee) { this.totalFee = totalFee; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
