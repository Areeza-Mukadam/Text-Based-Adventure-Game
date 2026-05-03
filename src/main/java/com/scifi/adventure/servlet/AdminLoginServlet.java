package com.scifi.adventure.servlet;

import com.scifi.adventure.db.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        boolean isValid = checkAdminLogin(username, password);

        if (isValid) {
            HttpSession session = req.getSession();
            session.setAttribute("isAdmin", true);
            session.setAttribute("adminUser", username);

            // Redirect to admin dashboard
            resp.sendRedirect(req.getContextPath() + "/AdminDashboardServlet");
        } else {
            req.setAttribute("error", "Invalid admin credentials!");
            req.getRequestDispatcher("adminLogin.jsp").forward(req, resp);
        }
    }

    private boolean checkAdminLogin(String username, String password) {
        String sql = "SELECT * FROM admins WHERE username=? AND password=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true if a matching row exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
