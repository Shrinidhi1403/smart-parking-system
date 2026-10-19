package com.essdl.smart_parking.service;

import com.essdl.smart_parking.model.Booking;
import com.essdl.smart_parking.model.ParkingSpot;
import com.essdl.smart_parking.repository.BookingRepository;
import com.essdl.smart_parking.repository.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

  /*
        Service Layer!! => (full backend logic ismein)
        Controller sirf request leta hai aur Repository sirf DB mein save karta hai. Par beech ka saara dimaag—jaise:

        "Kya slot khali hai?",
        "Agar khali hai toh usko occupied mark karo",
         "Gadi kitni der ruki aur uska kitna bill bana?"

         ye sab Service Layer mein likha jata hai.
     */

@Service
public class ParkingService {

    @Autowired
    private ParkingSpotRepository spotRepository;

    @Autowired
    private BookingRepository bookingRepository;

    // 1. Saare slots dekhna
    public List<ParkingSpot> getAllSpots() {
        return spotRepository.findAll();
    }

    // 2. Sirf Available (Khali) slots dekhna
    public List<ParkingSpot> getAvailableSpots() {
        return spotRepository.findByIsOccupiedFalse();
    }

    // 3. Naya Parking Spot add karna
    public ParkingSpot addSpot(ParkingSpot spot) {
        spot.setOccupied(false);
        return spotRepository.save(spot);
    }

    // 4. Slot Reserve / Book karna
    public Booking reserveSpot(String vehicleNumber, String vehicleType) {
        // Step A: Check karo us vehicle type (CAR/BIKE) ke liye koi slot khali hai kya
        List<ParkingSpot> availableSpots = spotRepository
                .findByVehicleTypeIgnoreCaseAndIsOccupiedFalse(vehicleType);

        // Exception Handling (Module-I): Agar khali nahi hai toh error feko
        if (availableSpots.isEmpty()) {
            throw new RuntimeException("No free parking spot available for: " + vehicleType);
        }

        // Step B: List mein se pehla khali slot (index 0) uthao aur usko Occupied (true) kar do
        ParkingSpot allocatedSpot = availableSpots.get(0);
        allocatedSpot.setOccupied(true);
        spotRepository.save(allocatedSpot); // DB mein update ho gaya

        // Step C: Ab nayi Booking slip banao aur DB mein save karo
        Booking booking = new Booking();
        booking.setVehicleNumber(vehicleNumber.toUpperCase());
        booking.setSpot(allocatedSpot);
        booking.setEntryTime(LocalDateTime.now()); // Abhi ka exact system time
        booking.setTotalFee(0.0);
        booking.setStatus("ACTIVE");

        return bookingRepository.save(booking);
    }

    // 5. Vehicle Checkout / Slot Release karna aur Bill banana
    public Booking releaseSpot(Long bookingId) {

        // Step A: Booking ID se booking dhoondo, nahi mili toh exception feko
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking ID " + bookingId + " not found!"));

        if ("COMPLETED".equalsIgnoreCase(booking.getStatus())) {
            throw new RuntimeException("This vehicle has already checked out!");
        }

        // Step B: Jis spot par gadi khadi thi, usko wapas Free (false) kar do
        ParkingSpot spot = booking.getSpot();
        spot.setOccupied(false);
        spotRepository.save(spot);

        // Step C: Exit time note karo aur Status COMPLETED karo
        booking.setExitTime(LocalDateTime.now());
        booking.setStatus("COMPLETED");

        // Step D: Fee Calculation Logic
        long minutesParked = Duration.between(booking.getEntryTime(), booking.getExitTime()).toMinutes();
        long hoursCharged = Math.max(1, (minutesParked + 59) / 60); // Minimum 1 ghante ka charge lagega

        double hourlyRate = spot.getVehicleType().equalsIgnoreCase("CAR") ? 50.0 : 20.0;
        booking.setTotalFee(hoursCharged * hourlyRate);

        return bookingRepository.save(booking);
    }

    // 6. Saari Bookings ka record dekhna
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    /*
        @Service: Ye annotation Spring ko batata hai ki is class ka ek Bean (Object) bana kar apne IoC container mein rakh lo, kyunki isme humara business logic hai.

          @Autowired (Dependency Injection): Dekh humne kahin bhi spotRepository = new ParkingSpotRepository() nahi likha! @Autowired likhte hi Spring ne apna banaya hua Repository Bean yahan connect (inject) kar diya.

           Lambda Expression () -> new RuntimeException(...):

           Ye orElseThrow ke andar humne Java 8 ka Lambda Expression use kiya hai (jo tere Module-II syllabus mein hai)!

            Java 8 aur Collections => DONO USE KIYA HAIN!!!!!
     */
}

