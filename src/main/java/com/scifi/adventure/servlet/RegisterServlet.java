package com.scifi.adventure.servlet;

import com.scifi.adventure.model.Player;

import com.scifi.adventure.db.DBConnection;
import com.scifi.adventure.db.DatabaseManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        DatabaseManager db = DatabaseManager.getInstance();   
        boolean success = db.registerUser(username, password);
        
        if (success) {
            request.setAttribute("success", "Account created successfully! You can now log in.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Username already exists or registration failed.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
