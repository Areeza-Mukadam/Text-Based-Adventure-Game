package com.scifi.adventure.servlet;

import com.scifi.adventure.db.DatabaseManager;

import com.scifi.adventure.model.Player;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Saves player progress to the database.
 */
@WebServlet("/save")
public class SaveGameServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(SaveGameServlet.class.getName());

    @Override
    protected void doPost(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        Player player = (Player) session.getAttribute("player");
        if (player == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        String saveName = req.getParameter("saveName");
        if (saveName != null && !saveName.isBlank()) {
            player.setName(saveName.trim());
        }

        try {
            DatabaseManager db = DatabaseManager.getInstance();
            db.saveOrUpdatePlayer(player);
            session.setAttribute("saveStatus", "Game saved as '" + player.getName() + "'");
            resp.sendRedirect(req.getContextPath() + "/game.jsp");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to save game: " + e.getMessage(), e);
            session.setAttribute("saveStatus", "Failed to save game: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/game.jsp");
        }
    }

    @Override
    protected void doGet(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/game.jsp");
    }
}
