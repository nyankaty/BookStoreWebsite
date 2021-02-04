package com.bookstore.service;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.dao.HashGenerationException;
import com.bookstore.dao.HashGenerator;
import com.bookstore.dao.UserDAO;
import com.bookstore.entity.Users;


public class UserServices {
	private EntityManager entityManager;
	private UserDAO userDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public UserServices(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.entityManager = entityManager;
		userDAO = new UserDAO(entityManager);
	}

	public void listUser() throws ServletException, IOException {
		listUser(null);
	}
	public void listUser(String message) throws ServletException, IOException {

		List<Users> listUsers = userDAO.listAll();
		
		request.setAttribute("listUsers", listUsers);
		
		if(message != null) {
			request.setAttribute("message", message);
		}
		
		String listPage = "user_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
		requestDispatcher.forward(request, response);
	}
	
	public void createUser() throws ServletException, IOException {
		String email = request.getParameter("email");
		String fullName = request.getParameter("fullname");
		String password = request.getParameter("password");
		
		Users existUser = userDAO.findByEmail(email);
		
		if(existUser != null) {
			String message = "Couldn't create user. A user with email " + email +
					" already exist";
			
			request.setAttribute("message", message);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);
		}else {
			Users newUser = new Users(email, fullName, password);
			userDAO.create(newUser);
			listUser("New user created successfully");
		}
	}

	public void editUser() throws ServletException, IOException {	
		int userId = Integer.parseInt(request.getParameter("id"));	
		
		Users user = userDAO.get(userId);
		
		String destPage = "user_form.jsp";
		
		if(user == null) {
			destPage = "message.jsp";
			String errorMessage = "Couldn't find user with " + userId;
			
			request.setAttribute("message", errorMessage);
		}
		else {
			user.setPassword(null);
			request.setAttribute("user", user);
		}
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(destPage);
		requestDispatcher.forward(request, response);
	}
	
	public void updateUser() throws ServletException, IOException { 
		int userID = Integer.parseInt(request.getParameter("userID"));
		String email = request.getParameter("email");
		String fullName = request.getParameter("fullname");
		String password = request.getParameter("password");
		
		Users userByID = userDAO.get(userID);
		
		Users userByEmail = userDAO.findByEmail(email);
		
		if(userByEmail != null && userByEmail.getUserID() != userByID.getUserID()) {
			String message = "Could not update user. The email " + userByEmail.getEmail() + " already exist !";
			request.setAttribute("message", message);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);
		}else {	
			userByID = userDAO.get(userID);
			userByID.setEmail(email);
			userByID.setFullName(fullName);
			
			if(password != null & !password.isEmpty()) {
				String encryptedPassword = HashGenerator.generateMD5(password);
				userByID.setPassword(encryptedPassword);
			}
			
			userDAO.update(userByID);
			
			String message = "User has been updated successfully";
			listUser(message);
		}
	}

	public void deleteUser() throws ServletException, IOException {
		int userID = Integer.parseInt(request.getParameter("id"));
		String message = "User has been deleted successfully";
		
		if(userID == 52) {
			message = "The default admin user account cannot be deleted.";
			request.setAttribute("message", message);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);
			return;
		}
		
		Users user = userDAO.get(userID);
		if(user == null) {
			message = "Could not find user with ID " + userID
					+ ", or it might have been deleted by another admin";
			
			request.setAttribute("message", message);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);
		}else {
			userDAO.delete(userID);
			listUser(message);
		}
	}

	public void login() throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		boolean loginResult = userDAO.checkLogin(email, password);
		
		if(loginResult) {
			request.getSession().setAttribute("useremail", email);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/");
			requestDispatcher.forward(request, response);
		}else {
			String message = "Login failed !";
			
			request.setAttribute("message", message);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
			requestDispatcher.forward(request, response);
		}
	}
}
