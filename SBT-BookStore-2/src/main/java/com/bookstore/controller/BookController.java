package com.bookstore.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.entity.BookEntity;
import com.bookstore.service.BookService;

@Controller
public class BookController {


	@Autowired
	private BookService bookService;

	/* VIEW HOME PAGE IN WEBSITE */
	
	@GetMapping("/")
	public String home()
	{
		return "index";
	}
	
	
	//SHOW ADDBOOK FROM
	@GetMapping("/addbook")
	public String Add()
	{
		return "Addbook";
	}
	
	//PROCESSING ADD BOOK
	
	@PostMapping("/addBooks")
	public String addBooks(@ModelAttribute BookEntity book, @RequestParam("img") MultipartFile file, HttpSession session) {

		book.setImgName(file.getOriginalFilename());

		if (bookService.addBook(book) != null) {

			try {
				File saveFile = new ClassPathResource("static/imgs").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				// System.out.println(path);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			} catch (IOException e) {
				e.printStackTrace();
			}
			session.setAttribute("succMsg", "Book Added Sucessfully");
		}
		return "redirect:/addbook";
	}
	
	
	// VIEW ALL BOOK IN PAGE
	@GetMapping("/viewBook")
	public String viewBook(Model m) {
	
		m.addAttribute("books", bookService.getAllBook());
		m.addAttribute("bookService", bookService);
		return "Viewbook";
	}
	
	

	//DELETE BOOK
	@GetMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable int id, HttpSession session) {

		if (bookService.deleteBook(id)) {
			session.setAttribute("succMsg", "Book Delete Sucessfully");
		} else {
			session.setAttribute("errorMsg", "Something wrong on server");
		}
		return "redirect:/viewBook";
	}
	
	//EDIT BOOK
	@GetMapping("/editBook/{id}")
	public String editBook(Model m, @PathVariable int id) {
		m.addAttribute("book", bookService.getBookById(id));
	
		return "EditBook";
	}
	
	@PostMapping("/updateBook")
	public String updateBook(@ModelAttribute BookEntity book, @RequestParam("img") MultipartFile file, HttpSession session) {
		// System.out.println(file.isEmpty());
		if (!file.isEmpty()) {
			book.setImgName(file.getOriginalFilename());
		} else {
			BookEntity oldBook = bookService.getBookById(book.getId());
			book.setImgName(oldBook.getImgName());
		}

		if (bookService.addBook(book) != null) {

			try {
				if (!file.isEmpty()) {
					File saveFile = new ClassPathResource("static/imgs").getFile();

					Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
					// System.out.println(path);
					Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			session.setAttribute("succMsg", "Book update Sucessfully");
		}
		return "redirect:/viewBook";
	}
	
	//base
	
	@GetMapping("/base")
	public String base()
	{
		return "base";
	}
	

}
