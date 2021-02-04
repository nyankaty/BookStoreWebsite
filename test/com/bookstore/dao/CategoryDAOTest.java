package com.bookstore.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstore.entity.Category;


public class CategoryDAOTest extends BaseDAOTest{

	private static CategoryDAO categoryDAO;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BaseDAOTest.setUpBeforeClass();
		categoryDAO = new CategoryDAO(entityManager);
	}

	@Test
	public void testCreateCategory() {
		Category newCat = new Category("Java Frameworks");
		Category category = categoryDAO.create(newCat);
		
		assertTrue(category != null && category.getCategoryId() > 0);
	}
	
	@Test
	public void testUpdateCategory() {
		Category cat = new Category("Core Javah4");
		cat.setCategoryId(11);
		
		Category category = categoryDAO.update(cat);
		
		assertEquals(cat.getName(), category.getName());
	}
	
	@Test
	public void testGet() {
		Integer catId = 2;
		Category cat = categoryDAO.get(catId);
		
		assertNotNull(cat);
	}
	
	@Test
	public void testDeleteCategory() {
		Integer catId = 14;
		categoryDAO.delete(catId);
		
		Category cat = categoryDAO.get(catId);
		assertNull(cat);
	}
	
	@Test
	public void testListAll() {
		List<Category> listCategory = categoryDAO.listAll();
		assertTrue(listCategory.size() > 0);
	}
	
	@Test
	public void testCount() {
		long totalCategories = categoryDAO.count();
		
		assertEquals(3, totalCategories);
	}
	
	@Test
	public void testFindByName() {
		String name = "Java";
		Category category = categoryDAO.findByName(name);
		
		assertNotNull(category);
	}
	
	@Test
	public void testFindByNameNotFound() {
		String name = "1";
		Category category = categoryDAO.findByName(name);
		
		assertNull(category);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		BaseDAOTest.tearDownAfterClass();
	}

}
