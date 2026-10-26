package com.essdl.smart_parking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * Request body for POST /api/parking/bookings.
 * Only carries what the client is allowed to send. Entry time, status and fee
 * are decided by the server, so they are NOT part of this DTO.
 */
public class BookingRequestDTO {

    @NotNull(message = "Spot ID is required")
    private Long spotId;

    @NotBlank(message = "Vehicle number is required")
    @Pattern(
            regexp = "^[A-Z]{2}[0-9]{1,2}[A-Z]{1,3}[0-9]{4}$",
            message = "Vehicle number must be uppercase plate format, e.g. MH12AB1234"
    )
    private String vehicleNumber;

    // Default constructor + setters are needed so Jackson can build the object from JSON
    public BookingRequestDTO() {
    }

    public Long getSpotId() { return spotId; }
    public void setSpotId(Long spotId) { this.spotId = spotId; }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
}
