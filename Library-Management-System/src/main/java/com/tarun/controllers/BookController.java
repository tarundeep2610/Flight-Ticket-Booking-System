package com.tarun.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tarun.service.BookService;

@Controller
public class BookController {

	@Autowired
	BookService bookService;
	
	
}
