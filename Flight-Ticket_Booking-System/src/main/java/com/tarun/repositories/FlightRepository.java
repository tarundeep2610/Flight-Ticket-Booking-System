package com.tarun.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tarun.entites.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long>{

	Flight findByFlightNumber(String flightNumber);

	List<Flight> findAllByDepartureDateAndDepartureTimeAfter(String date, String time);

}
