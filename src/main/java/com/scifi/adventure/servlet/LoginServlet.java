package com.scifi.adventure.servlet;

import com.scifi.adventure.model.Player;
import com.scifi.adventure.db.DatabaseManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        DatabaseManager db = DatabaseManager.getInstance();
        int result = db.checkUserLogin(username, password);

        if (result == 2) {
            // ✅ Successful login
            Player player = new Player();
            player.setName(username);

            HttpSession session = request.getSession();
            session.setAttribute("player", player);
            session.setAttribute("username", username);

            // ✅ Redirect to StartGameServlet (not directly to game.jsp)
            response.sendRedirect(request.getContextPath() + "/start");

        } else {
            // ❌ Failed login
            if (result == 0) {
                request.setAttribute("error", "No such user found. Please sign up first.");
            } else if (result == 1) {
                request.setAttribute("error", "Incorrect password. Please try again.");
            }
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
