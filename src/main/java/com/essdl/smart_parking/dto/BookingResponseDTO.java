package com.essdl.smart_parking.dto;

import java.time.LocalDateTime;

/**
 * Clean payload returned to clients. Flattens the Booking -> ParkingSpot
 * relationship so no JPA entity internals are exposed.
 */
public class BookingResponseDTO {

    private final Long id;
    private final Long spotId;
    private final String spotNumber;
    private final String vehicleNumber;
    private final LocalDateTime startTime;   // Booking.entryTime
    private final LocalDateTime endTime;     // Booking.exitTime (null while ACTIVE)
    private final double totalAmount;        // Booking.totalFee
    private final String status;             // ACTIVE / COMPLETED

    public BookingResponseDTO(Long id, Long spotId, String spotNumber, String vehicleNumber,
                              LocalDateTime startTime, LocalDateTime endTime,
                              double totalAmount, String status) {
        this.id = id;
        this.spotId = spotId;
        this.spotNumber = spotNumber;
        this.vehicleNumber = vehicleNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public Long getId() { return id; }
    public Long getSpotId() { return spotId; }
    public String getSpotNumber() { return spotNumber; }
    public String getVehicleNumber() { return vehicleNumber; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
}
