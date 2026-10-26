package com.essdl.smart_parking.service;

import com.essdl.smart_parking.dto.BookingRequestDTO;
import com.essdl.smart_parking.dto.BookingResponseDTO;
import com.essdl.smart_parking.dto.ParkingSpotDTO;
import com.essdl.smart_parking.model.Booking;
import com.essdl.smart_parking.model.ParkingSpot;
import com.essdl.smart_parking.repository.BookingRepository;
import com.essdl.smart_parking.repository.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service layer: business logic. From Milestone 1 it works with DTOs on the
 * outside, so the Controller never sees (or leaks) JPA entities.
 * NOTE: errors are still plain RuntimeExceptions here - Milestone 2 replaces them.
 */
@Service
public class ParkingService {

    @Autowired
    private ParkingSpotRepository spotRepository;

    @Autowired
    private BookingRepository bookingRepository;

    // 1. All spots
    public List<ParkingSpotDTO> getAllSpots() {
        return spotRepository.findAll().stream()
                .map(this::toSpotDTO)
                .toList();
    }

    // 2. Only free spots
    public List<ParkingSpotDTO> getAvailableSpots() {
        return spotRepository.findByIsOccupiedFalse().stream()
                .map(this::toSpotDTO)
                .toList();
    }

    // 3. Add a new spot (a new spot is always free)
    public ParkingSpotDTO addSpot(ParkingSpotDTO request) {
        ParkingSpot spot = new ParkingSpot(
                request.getSpotNumber().trim().toUpperCase(),
                request.getVehicleType().trim().toUpperCase(),
                false);
        return toSpotDTO(spotRepository.save(spot));
    }

    // 4. Book a specific spot by ID
    public BookingResponseDTO reserveSpot(BookingRequestDTO request) {
        ParkingSpot spot = spotRepository.findById(request.getSpotId())
                .orElseThrow(() -> new RuntimeException(
                        "Parking spot with ID " + request.getSpotId() + " not found!"));

        if (spot.isOccupied()) {
            throw new RuntimeException("Spot " + spot.getSpotNumber() + " is already booked!");
        }

        spot.setOccupied(true);
        spotRepository.save(spot);

        Booking booking = new Booking();
        booking.setVehicleNumber(request.getVehicleNumber().toUpperCase());
        booking.setSpot(spot);
        booking.setEntryTime(LocalDateTime.now());
        booking.setTotalFee(0.0);
        booking.setStatus("ACTIVE");

        return toBookingDTO(bookingRepository.save(booking));
    }

    // 5. Checkout: free the spot and calculate the bill
    public BookingResponseDTO releaseSpot(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking ID " + bookingId + " not found!"));

        if ("COMPLETED".equalsIgnoreCase(booking.getStatus())) {
            throw new RuntimeException("This vehicle has already checked out!");
        }

        ParkingSpot spot = booking.getSpot();
        spot.setOccupied(false);
        spotRepository.save(spot);

        booking.setExitTime(LocalDateTime.now());
        booking.setStatus("COMPLETED");

        long minutesParked = Duration.between(booking.getEntryTime(), booking.getExitTime()).toMinutes();
        long hoursCharged = Math.max(1, (minutesParked + 59) / 60); // minimum 1 hour

        double hourlyRate = spot.getVehicleType().equalsIgnoreCase("CAR") ? 50.0 : 20.0;
        booking.setTotalFee(hoursCharged * hourlyRate);

        return toBookingDTO(bookingRepository.save(booking));
    }

    // 6. Booking history
    public List<BookingResponseDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::toBookingDTO)
                .toList();
    }

    // ---------- Entity -> DTO mapping ----------

    private ParkingSpotDTO toSpotDTO(ParkingSpot spot) {
        return new ParkingSpotDTO(
                spot.getId(),
                spot.getSpotNumber(),
                spot.getVehicleType(),
                spot.isOccupied());
    }

    private BookingResponseDTO toBookingDTO(Booking booking) {
        return new BookingResponseDTO(
                booking.getId(),
                booking.getSpot().getId(),
                booking.getSpot().getSpotNumber(),
                booking.getVehicleNumber(),
                booking.getEntryTime(),
                booking.getExitTime(),
                booking.getTotalFee(),
                booking.getStatus());
    }
}
