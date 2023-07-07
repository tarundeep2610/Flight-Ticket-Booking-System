package com.tarun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tarun.entites.Book;
import com.tarun.repositories.BookRepository;
import com.tarun.service.BookService;

@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	BookRepository bookRepository;

	@Override
	public void save(Book book) {
		bookRepository.save(book);
	}

	@Override
	public void removeBook(long bid) {
		Book book= bookRepository.findById(bid).orElse(null);
		bookRepository.delete(book);
	}
}
