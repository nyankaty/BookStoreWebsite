package com.bookstore.controller.book;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.controller.admin.BaseServlet;
import com.bookstore.service.BookServices;

@WebServlet("/admin/create_book")
@MultipartConfig(
		fileSizeThreshold = 1024 * 10, //10kb
		maxFileSize = 1024 * 10, // 300kb
		maxRequestSize = 1024 * 1024 // 1MB
)
public class CreateBookServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public CreateBookServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BookServices bookServices = new BookServices(entityManager, request, response);
		bookServices.createBook();
	}

}
