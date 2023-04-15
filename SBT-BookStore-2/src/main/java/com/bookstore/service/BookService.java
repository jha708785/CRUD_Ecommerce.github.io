package com.bookstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookstore.entity.BookEntity;


public interface BookService {

	//add book
   BookEntity addBook(BookEntity book);
	
   //list all book
	List<BookEntity> getAllBook();
	
	//book getbyid
	BookEntity getBookById(int id);
	
	//delete book
	boolean deleteBook(int id);

	
}
