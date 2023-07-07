package com.tarun.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tarun.entites.Booking;
import com.tarun.entites.Flight;
import com.tarun.entites.User;
import com.tarun.security.UserDetailsImpl;
import com.tarun.service.BookingService;
import com.tarun.service.FlightService;
import com.tarun.service.UserService;

@Controller
public class BookingController {

	@Autowired
	BookingService bookingService;
	@Autowired
	FlightService flightService;
	@Autowired
	UserService userService;

	@GetMapping("view-bookings")
	public String beforeBookingView() {
		return "beforeviewbookings";
	}

	@PostMapping("booking-list")
	public String bookingView(@RequestParam String flightnumber, @RequestParam String departureDate, Model model) {
		List<Booking> blist = bookingService.getBookingByFlightNumAndTime(flightnumber, departureDate);
		model.addAttribute("blist", blist);
		return "viewbookings";
	}
	
	@RequestMapping("book-flight")
	public String bookFlight(@RequestParam String fid,Model model) {
		Flight flight=flightService.findByFlightNum(fid);
		model.addAttribute("flight", flight.getFlightNumber());
		return "booking";
	}
	
	@PostMapping("book")
	public String bookFlight(@RequestParam String fid,int numberOfTickets,Model model) {
		Flight flight= flightService.findByFlightNum(fid);
		if(flight.getSeatCount()>=numberOfTickets) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        UserDetails userDetails = (UserDetailsImpl) authentication.getPrincipal();
	 
	        String loggedInUsername = userDetails.getUsername();
	        User user= userService.findByUsername(loggedInUsername);
			flight.setSeatCount(flight.getSeatCount()-numberOfTickets);
			flightService.saveFlight(flight);
			
			Booking booking= new Booking();
			booking.setNumberOfTickets(numberOfTickets);
			booking.setFlight(flight);
			booking.setUser(user);
			
			bookingService.save(booking);
			return "redirect:/my-bookings";
		}
		
		model.addAttribute("msg", "Only "+flight.getSeatCount()+" seats are remaining.");
		model.addAttribute("flight", flight.getFlightNumber());
		return "redirect:/book-flight";
	}
	
	@RequestMapping("my-bookings")
	public String myBookings(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetailsImpl) authentication.getPrincipal();
 
        String loggedInUsername = userDetails.getUsername();
        User user= userService.findByUsername(loggedInUsername);
        List<Booking> blist= bookingService.findAllByUser(user);
        model.addAttribute("blist", blist);
        return "viewbookings";
	}
}

