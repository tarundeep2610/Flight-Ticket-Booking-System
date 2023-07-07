package com.tarun.service;

import java.util.List;

import com.tarun.entites.Flight;

public interface FlightService {

	void saveFlight(Flight flight);

	void removeFlight(long id);

	List<Flight> findAllFlightsByDateAndTime(String date, String time);

	List<Flight> findAllFlights();

	Flight findById(long fid);

	Flight findByFlightNum(String flightNum);

}
