package com.tarun.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tarun.entites.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

}
