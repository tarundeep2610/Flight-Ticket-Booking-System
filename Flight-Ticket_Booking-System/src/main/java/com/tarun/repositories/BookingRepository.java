package com.tarun.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tarun.entites.Booking;
import com.tarun.entites.User;

public interface BookingRepository extends JpaRepository<Booking, Long>{
	
	@Query("SELECT b FROM Booking b WHERE b.flight.flightNumber = :flightNumber AND b.flight.departureDate = :date")
	List<Booking> findAllByFlightNumberAndTime(String flightNumber, String date);

	List<Booking> findAllByUser(User user);

}
