package com.bookstore.dao;

import static org.junit.Assert.*;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstore.entity.Book;
import com.bookstore.entity.Category;

public class BookDAOTest extends BaseDAOTest{
	private static BookDAO bookDAO;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BaseDAOTest.setUpBeforeClass();
		bookDAO = new BookDAO(entityManager);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		BaseDAOTest.tearDownAfterClass();
	}
	
	@Test
	public void testCreateBook() throws ParseException, IOException {
		Book newBook = new Book();
		Category category = new Category("Advanced");
		category.setCategoryId(29);
		newBook.setCategory(category);
		newBook.setTitle("Effective Java (2nd Edition)");
		newBook.setAuthor("Joshua Bloch");
		newBook.setDescription("Are you looking for a deeper understanding of the Java");
		newBook.setPrice(38.87f);
		newBook.setIsbn("0321356683");
		
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		
		Date publishDate = dateFormat.parse("05/28/2008");
		newBook.setPublishDate(publishDate);
		
		String imagePath = "D:\\Other\\Side Projects\\BookStoreProject.doc\\Resouces\\Admin Home Page\\dummy-data-books\\books\\Effective Java.JPG";
		
		byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
		newBook.setImage(imageBytes);
		
		Book createdBook = bookDAO.create(newBook);
		
		assertTrue(createdBook.getBookId() > 0);
	}

	@Test
	public void testUpdateBook() throws ParseException, IOException {
		Book existBook = new Book();
		existBook.setBookId(35);
		
		Category category = new Category("Advanced");
		category.setCategoryId(12);
		existBook.setCategory(category);
		existBook.setTitle("Effective Java (3rd Edition)");
		existBook.setAuthor("Joshua Bloch");
		existBook.setDescription("Are you looking for a deeper understanding of the Java");
		existBook.setPrice(40f);
		existBook.setIsbn("0321356683");
		
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		
		Date publishDate = dateFormat.parse("05/28/2008");
		existBook.setPublishDate(publishDate);
		
		String imagePath = "D:\\Other\\Side Projects\\BookStoreProject.doc\\Resouces\\Admin Home Page\\dummy-data-books\\books\\Effective Java.JPG";
		
		byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
		existBook.setImage(imageBytes);
		
		Book updatedBook = bookDAO.update(existBook);
			
		assertEquals(updatedBook.getTitle(), "Effective Java (3rd Edition)");
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testDeleteBookFail() {
		int bookId = 0;
		bookDAO.delete(bookId);
		
		assertTrue(true);
	}
	
	@Test
	public void testDeleteBookSuccess() {
		int bookId = 35;
		bookDAO.delete(bookId);
		
		assertTrue(true);
	}
	
	@Test
	public void testGetBookFail() {
		int bookId = 0;
		Book book = bookDAO.get(bookId);
		
		assertNull(book);
	}
	
	@Test
	public void testGetBookSuccess() {
		int bookId = 36;
		Book book = bookDAO.get(bookId);
		
		assertNotNull(book);
	}
	
	@Test
	public void testListAll() {
		List<Book> listBooks = bookDAO.listAll();
		
		assertFalse(listBooks.isEmpty());
	}
	
	@Test
	public void testFindByTitleNotExist() {
		String title = "Thinking in Java";
		Book book = bookDAO.findByTitle(title);
		
		assertNull(book);
	}
	
	@Test
	public void testFindByTitleExist() {
		String title = "Effective Java (2nd Edition)";
		Book book = bookDAO.findByTitle(title);
		
		assertNotNull(book);
	}
	
	@Test
	public void testCount() {
		long totalBook = bookDAO.count();
		
		assertEquals(1, totalBook);
	}
}

