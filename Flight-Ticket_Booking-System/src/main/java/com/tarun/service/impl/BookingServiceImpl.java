package com.tarun.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tarun.entites.Booking;
import com.tarun.entites.User;
import com.tarun.repositories.BookingRepository;
import com.tarun.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService{

	@Autowired
	BookingRepository bookingRepository;
	
	@Override
	public List<Booking> getBookingByFlightNumAndTime(String flightNumber, String date) {
		return bookingRepository.findAllByFlightNumberAndTime(flightNumber,date);
	}

	@Override
	public void save(Booking booking) {
		bookingRepository.save(booking);
	}

	@Override
	public List<Booking> findAllByUser(User user) {
		return bookingRepository.findAllByUser(user);
	}

}
