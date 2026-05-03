package com.scifi.adventure.servlet;

import com.scifi.adventure.db.DatabaseManager;
import com.scifi.adventure.model.Player;
import com.scifi.adventure.model.StoryNode;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Initializes a new player and loads the first story node.
 */
@WebServlet("/start")
public class StartGameServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(StartGameServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        startGame(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        startGame(req, resp);
    }

    private void startGame(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();

        // Get username from session (set by LoginServlet)
        String username = (String) session.getAttribute("username");
        if (username == null || username.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        // Create new player object
        Player player = new Player();
        player.setName(username);
        player.setHealth(100);
        player.setOxygen(100);
        player.setThreatLevel(0);
        player.setScore(0);
        player.setCurrentNodeId(1); // starting node ID

        try {
            DatabaseManager db = DatabaseManager.getInstance();
            db.saveOrUpdatePlayer(player);

            // Load first story node
            Optional<StoryNode> nodeOpt = db.getStoryNode(1);
            if (nodeOpt.isEmpty()) {
                LOGGER.warning("Starting node not found in DB.");
                req.setAttribute("errorMessage", "Starting node not found!");
                req.getRequestDispatcher("error.jsp").forward(req, resp);
                return;
            }

            // Put player and node in session
            session.setAttribute("player", player);
            session.setAttribute("currentNode", nodeOpt.get());

            // Forward to game.jsp
            req.getRequestDispatcher("game.jsp").forward(req, resp);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to start game: " + e.getMessage(), e);
            req.setAttribute("errorMessage", "Unable to start game: " + e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
