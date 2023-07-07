package com.tarun.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tarun.entites.Flight;
import com.tarun.service.FlightService;

@Controller
public class FlightController {
	@Autowired
	private FlightService flightService;

	@GetMapping("/add-flight")
	public String addFlightView() {
		return "addflight";
	}

	@PostMapping("/add-flight-db")
	public String saveFlight(Flight flight) {
		String[] s1= flight.getDepartureDate().split("-");
//		String[] s2=	System.out.println(s1[0]+" "+s1[1]+" "+s1[2]);
		flightService.saveFlight(flight);
		return "redirect:/flight-list";
	}

	@RequestMapping("/remove-flight")
	public String removeFlight(@RequestParam long fid) {
		flightService.removeFlight(fid);
		return "redirect:/flight-list";
	}
	
	@GetMapping("/flight-list")
	public String flightList(Model model) {
		List<Flight> flist= flightService.findAllFlights();
		model.addAttribute("flist", flist);
		return "flightlist";
	}

	@RequestMapping("/search-flights")
	public String searchFlightView(@RequestParam(value = "date", required = false) String date,
			@RequestParam(value = "time", required = false) String time, Model model) {
		if (date != null && time != null) {
			List<Flight> flights = flightService.findAllFlightsByDateAndTime(date.toString(), time.toString());
			model.addAttribute("flights", flights);
		}
		return "search";
	}
}
