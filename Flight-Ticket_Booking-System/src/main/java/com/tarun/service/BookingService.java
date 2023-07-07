package com.tarun.service;

import java.util.List;

import com.tarun.entites.Booking;
import com.tarun.entites.User;

public interface BookingService {

	List<Booking> getBookingByFlightNumAndTime(String flightNumber, String time);

	void save(Booking booking);

	List<Booking> findAllByUser(User user);

}
