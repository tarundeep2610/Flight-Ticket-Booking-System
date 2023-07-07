package com.tarun.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tarun.entites.Book;
import com.tarun.entites.Cart;
import com.tarun.entites.User;
import com.tarun.security.UserDetailsImpl;
import com.tarun.service.BookService;
import com.tarun.service.CartService;
import com.tarun.service.UserService;

@Controller
public class CartController {

	@Autowired
	CartService cartService;
	@Autowired
	BookService bookService;
	@Autowired
	UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@RequestMapping("remove-book")
	public String removeBook(@RequestParam long bid){
		bookService.removeBook(bid);
		return "redirect:/add-cart";
	}
	
	@GetMapping("add-cart")
	public String addToCart(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetailsImpl) authentication.getPrincipal();
 
        String username= userDetails.getUsername();
		User user= userService.findByUsername(username);
		Cart cart= user.getCart();
		if(cart==null) {
			cart= new Cart();
			user.setCart(cart);
			cartService.save(cart);
			userService.registerUser(user);
		}
		
		model.addAttribute("blist",cart.getBooks());
		return "cart";
	}
	@PostMapping("add-cart")
	public String addToCart(Book book, Model model) {
		bookService.save(book);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetailsImpl) authentication.getPrincipal();
 
        String username= userDetails.getUsername();
		User user= userService.findByUsername(username);
		Cart cart= user.getCart();
		if(cart==null) {
			System.out.println(username);
			cart= new Cart();
			cart.setBooks(new ArrayList<>());
			cart.getBooks().add(book);
			cartService.save(cart);
			user.setCart(cart);
			userService.registerUser(user);
		}
		else {
			cart.getBooks().add(book);
			cartService.save(cart);
		}
		
		model.addAttribute("blist",cart.getBooks());
		return "cart";
	}
}
