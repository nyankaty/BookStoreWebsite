package com.bookstore.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.bookstore.entity.Users;

public class UserDAO extends JpaDAO<Users> implements GenericDAO<Users> {

	public UserDAO(EntityManager entityManager) {
		super(entityManager);
	}
	
	@Override
	public Users create(Users user) {
		String encryptedPassword = HashGenerator.generateMD5(user.getPassword());
		user.setPassword(encryptedPassword);
		return super.create(user);
	}

	@Override
	public Users update(Users user) {
		return super.update(user);
	}

	@Override
	public Users get(Object userID) {
		return super.find(Users.class, userID);
	}

	@Override
	public void delete(Object userID) {
		super.delete(Users.class, userID);
	}
	

	@Override
	public List<Users> listAll() {
		return super.findWithNameQuery("Users.findAll");
	}

	@Override
	public long count() {
		return super.countWithNameQuery("Users.countAll");		
	}
	
	public Users findByEmail(String email) {
		List<Users> listUsers = super.findWithNameQuery("Users.findByEmail","email", email);
		
		if(listUsers != null && listUsers.size() > 0) {
			return listUsers.get(0);
		}
		return null;
	}
	
	public Boolean checkLogin(String email, String password) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("email", email);
		String encryptedPassword = HashGenerator.generateMD5(password);
		parameters.put("password", encryptedPassword);
		
		List<Users> listUsers = super.findWithNameQuery("Users.checkLogin", parameters);
		
		if(listUsers.size() == 1) {
			return true;
		}
		
		return false;
	}
	
}
