package com.tarun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tarun.entites.Cart;
import com.tarun.repositories.CartRepository;
import com.tarun.service.CartService;

@Service
public class CartServiceImpl implements CartService{
	@Autowired
	CartRepository cartRepository;

	@Override
	public void save(Cart cart) {
		cartRepository.save(cart);
	}
}
