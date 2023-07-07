package com.tarun.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tarun.entites.Flight;
import com.tarun.repositories.FlightRepository;
import com.tarun.service.FlightService;

@Service
public class FlightServiceImpl implements FlightService {

	@Autowired
	private FlightRepository flightRepository;

	public void saveFlight(Flight flight) {
		flightRepository.save(flight);
	}

	@Override
	public void removeFlight(long id) {
		Flight f = flightRepository.findById(id).orElse(null);
		if (f != null) {
			flightRepository.delete(f);
		}
	}

	@Override
	public List<Flight> findAllFlightsByDateAndTime(String date, String time) {
		List<Flight> flights = flightRepository.findAllByDepartureDateAndDepartureTimeAfter(date, time);
		return flights;
	}

	@Override
	public List<Flight> findAllFlights() {
		return flightRepository.findAll();
	}

	@Override
	public Flight findById(long fid) {
		return flightRepository.findById(fid).orElse(null);
	}

	@Override
	public Flight findByFlightNum(String flightNum) {
		return flightRepository.findByFlightNumber(flightNum);
	}

}
