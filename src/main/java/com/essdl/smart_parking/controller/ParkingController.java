package com.essdl.smart_parking.controller;

import com.essdl.smart_parking.dto.BookingRequestDTO;
import com.essdl.smart_parking.dto.BookingResponseDTO;
import com.essdl.smart_parking.dto.ParkingSpotDTO;
import com.essdl.smart_parking.service.ParkingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller layer: only HTTP concerns (paths, status codes, validation trigger).
 * @Valid makes Spring run the Bean Validation annotations on the DTO BEFORE
 * this method body executes; invalid input never reaches the service.
 */
@RestController
@RequestMapping("/api/parking")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    // GET /api/parking/spots  -> 200
    @GetMapping("/spots")
    public ResponseEntity<List<ParkingSpotDTO>> getAllSpots() {
        return ResponseEntity.ok(parkingService.getAllSpots());
    }

    // GET /api/parking/spots/available  -> 200
    @GetMapping("/spots/available")
    public ResponseEntity<List<ParkingSpotDTO>> getAvailableSpots() {
        return ResponseEntity.ok(parkingService.getAvailableSpots());
    }

    // POST /api/parking/spots  -> 201
    @PostMapping("/spots")
    public ResponseEntity<ParkingSpotDTO> addSpot(@Valid @RequestBody ParkingSpotDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingService.addSpot(request));
    }

    // POST /api/parking/bookings  -> 201 (replaces the old /reserve?vehicleNumber=...)
    @PostMapping("/bookings")
    public ResponseEntity<BookingResponseDTO> createBooking(@Valid @RequestBody BookingRequestDTO request) {
        BookingResponseDTO response = parkingService.reserveSpot(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // POST /api/parking/release/{bookingId}  -> 200
    @PostMapping("/release/{bookingId}")
    public ResponseEntity<BookingResponseDTO> releaseSpot(@PathVariable Long bookingId) {
        return ResponseEntity.ok(parkingService.releaseSpot(bookingId));
    }

    // GET /api/parking/bookings  -> 200
    @GetMapping("/bookings")
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings() {
        return ResponseEntity.ok(parkingService.getAllBookings());
    }
}
