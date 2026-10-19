package com.essdl.smart_parking.repository;

import com.essdl.smart_parking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}

/*
    Yahan <ParkingSpot, Long> Generics hai (Module-II syllabus). Pehla parameter batata hai kaunsi Entity table hai (ParkingSpot), aur dusra batata hai uski Primary Key (@Id) ka data type kya hai (Long)
 */