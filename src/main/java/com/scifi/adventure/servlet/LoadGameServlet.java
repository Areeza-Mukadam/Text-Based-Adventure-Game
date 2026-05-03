package com.scifi.adventure.servlet;

import com.scifi.adventure.db.DatabaseManager;
import com.scifi.adventure.model.Player;
import com.scifi.adventure.model.StoryNode;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Loads saved progress from the database.
 * GET: shows available saves (redirects to index.jsp which lists them)
 * POST: loads the selected save into session.
 */
@WebServlet("/load")
public class LoadGameServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LoadGameServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            DatabaseManager db = DatabaseManager.getInstance();
            List<String> names = db.listSavedPlayerNames();   // ✅ fixed
            req.setAttribute("saves", names);                 // ✅ fixed
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to list saves: " + ex.getMessage(), ex);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load saves");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("loadName");
        if (name == null || name.isBlank()) {
            req.getSession().setAttribute("loadStatus", "Please select a valid save name.");
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        try {
            DatabaseManager db = DatabaseManager.getInstance();
            Optional<Player> playerOpt = db.loadPlayerByName(name);   // ✅ fixed
            if (playerOpt.isEmpty()) {
                req.getSession().setAttribute("loadStatus", "Save not found: " + name);
                resp.sendRedirect(req.getContextPath() + "/index.jsp");
                return;
            }

            Player player = playerOpt.get();
            Optional<StoryNode> nodeOpt = db.getStoryNode(player.getCurrentNodeId());   // ✅ use instance
            if (nodeOpt.isEmpty()) {
                req.getSession().setAttribute("loadStatus", "Saved node not found. Starting at node 1.");
                player.setCurrentNodeId(1);
                nodeOpt = db.getStoryNode(1);
            }

            HttpSession session = req.getSession(true);
            session.setAttribute("player", player);
            session.setAttribute("currentNode", nodeOpt.orElseGet(() -> {
                StoryNode fallback = new StoryNode();
                fallback.setId(1);
                fallback.setDescription("A void. (fallback)");
                return fallback;
            }));
            session.setAttribute("loadStatus", "Loaded save: " + player.getName());
            resp.sendRedirect(req.getContextPath() + "/game.jsp");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to load game: " + ex.getMessage(), ex);
            req.getSession().setAttribute("loadStatus", "Failed to load game: " + ex.getMessage());
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }
}
