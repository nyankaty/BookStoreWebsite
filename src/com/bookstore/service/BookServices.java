package com.bookstore.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.bookstore.dao.BookDAO;
import com.bookstore.dao.CategoryDAO;
import com.bookstore.entity.Book;
import com.bookstore.entity.Category;

public class BookServices {
	private EntityManager entityManager;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private BookDAO bookDAO;
	private CategoryDAO categoryDAO;
	
	public BookServices(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.entityManager = entityManager;
		bookDAO = new BookDAO(entityManager);
		categoryDAO = new CategoryDAO(entityManager);
	}
	
	public void listBooks() throws ServletException, IOException {
		listBooks(null);
	}
	
	public void listBooks(String message) throws ServletException, IOException {
		List<Book> listBooks = bookDAO.listAll();
		
		request.setAttribute("listBooks", listBooks);
		
		if(message != null) {
			request.setAttribute("message", message);
		}
		
		String listPage = "book_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
		requestDispatcher.forward(request, response);
	}
	
	public void showBookNewForm() throws ServletException, IOException {
		List<Category> listCategory = categoryDAO.listAll();
		request.setAttribute("listCategory", listCategory);
		
		String newPage = "book_form.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(newPage);
		requestDispatcher.forward(request, response);
	}

	public void createBook() throws ServletException, IOException {
		String title = request.getParameter("title");
		
		Book existBook = bookDAO.findByTitle(title);
		
		if(existBook != null) {
			String message = "Could not create new book because the title " + title + " already exist";
			listBooks(message);
			return;
		}
		
		Book newBook = new Book();
		
		readBookFields(newBook);
		
		Book createdBook = bookDAO.create(newBook);
		
		if(createdBook.getBookId() > 0) {
			String message = "A new book has been created successfully";
			listBooks(message);
		}
		
	}

	public void editBook() throws ServletException, IOException {
		int bookId =  Integer.parseInt(request.getParameter("id"));
		Book book = bookDAO.get(bookId);
		String destPage = "book_form.jsp";
		
		if (book != null) {
			List<Category> listCategory = categoryDAO.listAll();
			
			request.setAttribute("book", book);
			request.setAttribute("listCategory", listCategory);
			
		} else {
			destPage = "message.jsp";
			String message = "Could not find book with ID " + bookId;
			request.setAttribute("message", message);			
		}
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(destPage);
		requestDispatcher.forward(request, response);
	}
	
	public void readBookFields(Book book) throws ServletException, IOException {
		
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String description = request.getParameter("description");
		String isbn = request.getParameter("isbn");
		float price = Float.parseFloat(request.getParameter("price"));
		
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date publishDate = null;
		
		try {
			publishDate = dateFormat.parse(request.getParameter("publishDate"));
		}catch (ParseException ex) {
			throw new ServletException("Error parsing publish date (format is MM/dd/yyyy)");
		}
		
		book.setTitle(title);
		book.setAuthor(author);
		book.setDescription(description);
		book.setIsbn(isbn);
		book.setPublishDate(publishDate);
		
		int categoryId = Integer.parseInt(request.getParameter("category"));
		Category category = categoryDAO.get(categoryId);
		book.setCategory(category);
		
		book.setPrice(price);
		
		Part part = request.getPart("bookImage");
		
		if(part != null && part.getSize() > 0) {
			long size = part.getSize();
			byte[] imageBytes = new byte[(int) size];
			
			InputStream inputStream = part.getInputStream();
			inputStream.read(imageBytes);
			inputStream.close();
			
			book.setImage(imageBytes);	
		}	
		System.out.println("title: " + title);
		System.out.println("author: " + author);
		System.out.println("description: " + description);
		System.out.println("isbn: " + isbn);
		System.out.println("publishDate: " + publishDate);
		System.out.println("price: " + price);
		System.out.println("description: " + description);
	}

	public void updateBook() throws ServletException, IOException {
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		String title = request.getParameter("title");
		System.out.println("bookid" + bookId);
		
		Book existBook = bookDAO.get(bookId);
		Book bookByTitle = bookDAO.findByTitle(title);
		
		if(!existBook.equals(bookByTitle)) {
			String message = "Could not update book because there's the same book title has been already exist";
			listBooks(message);
			return;
		}
		
		readBookFields(existBook);
		
		bookDAO.update(existBook);
		
		String message = "The book has been updated successfully";
		listBooks(message);
	}

	public void deleteBook() throws ServletException, IOException {
		int bookId = Integer.parseInt(request.getParameter("id"));
		Book book = bookDAO.get(bookId);
		
		if(book != null) {
			bookDAO.delete(bookId);
			
			String message = "The book has been deleted successfully";
			listBooks(message);
		}else {
			String message = "Could not find book with ID " + bookId 
					+ ", or it might have been deleted";
			request.setAttribute("message", message);
			request.getRequestDispatcher("message.jsp").forward(request, response);
		}
	}
}
