package com.essdl.smart_parking.repository;

import com.essdl.smart_parking.model.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/*
    Creating a Repository Layer!

    (JDBC) mein database se data laane ke liye 20 line ka code (Connection, PreparedStatement, ResultSet, SELECT * FROM...) likhna padta tha.
    Spring Data JPA mein hum sirf ek Interface banate hain aur usko JpaRepository se extends (Inherit) kar lete hain. Bas itna karte hi save(), findAll(), findById(), deleteById()
    ye methods hum directly use kar skte hain!
 */

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

    // Custom Method 1: Saare khali slots laane ke liye
    List<ParkingSpot> findByIsOccupiedFalse();
    /*
        No need to define method {....} => Why?
        Spring Data JPA itna smart hai ki wo method ka naam padh kar khud SQL query bana leta hai!
     */

    // Custom Method 2: Specific vehicle (CAR/BIKE) ke khali slots laane ke liye
    List<ParkingSpot> findByVehicleTypeIgnoreCaseAndIsOccupiedFalse(String vehicleType);
}