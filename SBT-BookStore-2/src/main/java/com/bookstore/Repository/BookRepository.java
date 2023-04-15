package com.bookstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.entity.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity,Integer>
{
   
}
