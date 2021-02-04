package com.bookstore.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UsersTest {
	public static void main(String args[]) {
		Users user1 = new Users();
		user1.setEmail("tokudajp@gmail.com");
		user1.setFullName("Tô Ku Đa");
		user1.setPassword("tokuda");
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookStoreWebsite");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		entityManager.getTransaction().begin();
		entityManager.persist(user1);

		entityManager.getTransaction().commit();
		entityManager.close();

		System.out.println("A Users object was persistence");
	}

}
