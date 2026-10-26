package com.essdl.smart_parking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Used for both: returning spot details (GET) and adding a new spot (POST).
 * On POST the server ignores "id" and "occupied" (a new spot is always free).
 */
public class ParkingSpotDTO {

    private Long id;

    @NotBlank(message = "Spot number is required")
    private String spotNumber;

    @NotBlank(message = "Vehicle type is required")
    @Pattern(regexp = "^(?i)(CAR|BIKE)$", message = "Vehicle type must be CAR or BIKE")
    private String vehicleType;

    private boolean occupied;

    public ParkingSpotDTO() {
    }

    public ParkingSpotDTO(Long id, String spotNumber, String vehicleType, boolean occupied) {
        this.id = id;
        this.spotNumber = spotNumber;
        this.vehicleType = vehicleType;
        this.occupied = occupied;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSpotNumber() { return spotNumber; }
    public void setSpotNumber(String spotNumber) { this.spotNumber = spotNumber; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public boolean isOccupied() { return occupied; }
    public void setOccupied(boolean occupied) { this.occupied = occupied; }
}
