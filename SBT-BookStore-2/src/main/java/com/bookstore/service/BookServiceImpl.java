package com.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.Repository.BookRepository;
import com.bookstore.entity.BookEntity;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepo;

	@Override
	public BookEntity addBook(BookEntity book) {
		// TODO Auto-generated method stub
		return bookRepo.save(book);
	}

	@Override
	public List<BookEntity> getAllBook() 
	
	{
		
		return bookRepo.findAll();
	}

	@Override
	public BookEntity getBookById(int id) {
		
		return bookRepo.findById(id).get();
	}

	@Override
	public boolean deleteBook(int id) {
		BookEntity b = bookRepo.findById(id).get();
		if (b != null) {
			bookRepo.delete(b);
			return true;
		}
		return false;
	}

}
