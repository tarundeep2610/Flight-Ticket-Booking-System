package com.tarun.service;

import com.tarun.entites.Book;

public interface BookService {

	void save(Book book);

	void removeBook(long bid);

}
