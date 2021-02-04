package com.bookstore.dao;

import static org.junit.Assert.*;

import java.util.List;


import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstore.entity.Users;

public class UserDAOTest extends BaseDAOTest {
	
	private static UserDAO userDAO;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BaseDAOTest.setUpBeforeClass();
		userDAO = new UserDAO(entityManager);
	}
	@Test
	public void testCreateUsers() {
		Users user1 = new Users();
		user1.setEmail("tokudajp@gmail.com");
		user1.setFullName("Tô Ku Đa");
		user1.setPassword("tokuda");
		
		userDAO.create(user1);

		assertTrue(user1.getUserID() > 0);
		
	}
	
	@Test(expected = PersistenceException.class)
	public void testCreateUsersFieldsNotSet() {
		Users user1 = new Users();	

		userDAO.create(user1);
	}
	
	@Test
	public void testUpdateUsers() {
		Users user = new Users();
		user.setUserID(21);
		user.setEmail("vinhvove2000@gmail.com");
		user.setPassword("vinhvove");
		user.setFullName("Trần Quang Vinh");
		
		userDAO.update(user);
		String expected = "vinhvove2000@gmail.com";
		String actual = user.getEmail();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetUsersFound() {
		int userId = 21;
		Users user = userDAO.get(userId);
		
		assertNotNull(user);
	}
	
	@Test
	public void testGetUsersNotFound() {
		int userId = 99;
		Users user = userDAO.get(userId);
		
		assertNull(user);
	}
	
	@Test
	public void testDeleteUsers() {
		int userId = 25;
		userDAO.delete(userId);
		
		Users user = userDAO.get(userId);
		
		assertNull(user);
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testNoneExistUsers() {
		int userId = 25;
		userDAO.delete(userId);
	}
	
	@Test
	public void testListAll() {
		List<Users> listUsers = userDAO.listAll();
		
		assertTrue(listUsers.size() > 0);
	}
	
	@Test
	public void testCount() {
		long totalUsers = userDAO.count();
		assertEquals(4, totalUsers);
	}
	
	@Test
	public void testFindByEmail() {
		String email = "tokudajp@gmail.com";
		Users user = userDAO.findByEmail(email);
		
		assertNotNull(user);	
	}
	
	@Test
	public void testcheckLoginSuccess() {
		String email = "vinhtqph09311@gmail.com";
		String password = "vinhvove";
		boolean loginResult = userDAO.checkLogin(email, password);
		
		assertTrue(loginResult);
	}
	
	@Test
	public void testcheckLoginFailed() {
		String email = "lananh";
		String password = "vinhvove";
		boolean loginResult = userDAO.checkLogin(email, password);
		
		assertFalse(loginResult);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		BaseDAOTest.tearDownAfterClass();
	}

}
