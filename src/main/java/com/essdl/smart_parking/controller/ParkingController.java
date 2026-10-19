package com.essdl.smart_parking.controller;

import com.essdl.smart_parking.model.Booking;
import com.essdl.smart_parking.model.ParkingSpot;
import com.essdl.smart_parking.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/parking")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    // 1. Saare slots dekhna
    @GetMapping("/spots")
    public List<ParkingSpot> getAllSpots() {
        return parkingService.getAllSpots();
    }

    // 2. Sirf khali slots dekhna
    @GetMapping("/spots/available")
    public List<ParkingSpot> getAvailableSpots() {
        return parkingService.getAvailableSpots();
    }

    // 3. Naya parking slot add karna
    @PostMapping("/spots")
    public ParkingSpot addSpot(@RequestBody ParkingSpot spot) {
        return parkingService.addSpot(spot);
    }

    // 4. Slot Reserve karna
    @PostMapping("/reserve")
    public ResponseEntity<?> reserveSpot(@RequestParam String vehicleNumber, @RequestParam String vehicleType) {
        try {
            Booking booking = parkingService.reserveSpot(vehicleNumber, vehicleType);
            return ResponseEntity.ok(booking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // 5. Vehicle Checkout / Slot Release karna
    @PostMapping("/release/{bookingId}")
    public ResponseEntity<?> releaseSpot(@PathVariable Long bookingId) {
        try {
            Booking booking = parkingService.releaseSpot(bookingId);
            return ResponseEntity.ok(booking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // 6. Booking History dekhna
    @GetMapping("/bookings")
    public List<Booking> getAllBookings() {
        return parkingService.getAllBookings();
    }
}