package com.scifi.adventure.servlet;

import com.scifi.adventure.db.DatabaseManager;
import com.scifi.adventure.model.Player;
import com.scifi.adventure.model.StoryNode;
import com.scifi.adventure.db.AchievementDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/game")
public class GameServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(GameServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        Player player = (Player) session.getAttribute("player");
        StoryNode currentNode = (StoryNode) session.getAttribute("currentNode");
        if (player == null || currentNode == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        try {
            // --- Hacking Logic ---
            String hackInput = req.getParameter("hack_input");
            if (currentNode.isHackingNode()) {
                String answer = currentNode.getHackingAnswer();
                if (hackInput != null && answer != null &&
                        answer.equalsIgnoreCase(hackInput.trim())) {
                    req.setAttribute("eventTitle", "SUCCESS");
                    req.setAttribute("eventMessage", "System bypassed. Access granted.");
                    AchievementDAO.grant(player.getId(), 2); // Master Hacker achievement
                } else if (hackInput != null) {
                    player.setHealth(player.getHealth() - 10);
                    req.setAttribute("eventTitle", "FAILURE");
                    req.setAttribute("eventMessage", "Security shock triggered! -10 Health.");
                    session.setAttribute("player", player);
                    session.setAttribute("currentNode", currentNode);
                    DatabaseManager db = DatabaseManager.getInstance();
                    db.saveOrUpdatePlayer(player);
                    req.getRequestDispatcher("game.jsp").forward(req, resp);
                    return;
                }
            }

            // --- Standard Navigation ---
            String choice = req.getParameter("choice");
            int nextNodeId = 0;
            if (choice != null) {
                if ("1".equals(choice) || "option1".equalsIgnoreCase(choice)) {
                    nextNodeId = currentNode.getOption1Next();
                } else if ("2".equals(choice) || "option2".equalsIgnoreCase(choice)) {
                    nextNodeId = currentNode.getOption2Next();
                } else {
                    try {
                        nextNodeId = Integer.parseInt(choice);
                    } catch (NumberFormatException nfe) {
                        nextNodeId = currentNode.getOption1Next();
                    }
                }
            }

            LOGGER.info("Processing choice=" + choice + ", nextNodeId=" + nextNodeId);

            // --- Update Survival Stats ---
            player.setOxygen(player.getOxygen() - 5);
            player.setThreatLevel(player.getThreatLevel() + 7);
            player.setScore(player.getScore() + 20);
            player.setCurrentNodeId(nextNodeId);

            if (player.getThreatLevel() > 70) {
                req.setAttribute("eventTitle", "WARNING");
                req.setAttribute("eventMessage", "Something is crawling in the vents... fast.");
            }

            // --- Save Player ---
            try {
                DatabaseManager db = DatabaseManager.getInstance();
                db.saveOrUpdatePlayer(player);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Failed auto-saving player: " + e.getMessage(), e);
            }

            // --- Load Next Node ---
            if (nextNodeId <= 0) {
                if (player.getScore() <= 0) {
                    player.setScore(50); // fallback score
                }
                try {
                    DatabaseManager db = DatabaseManager.getInstance();
                    db.saveOrUpdatePlayer(player);
                } catch (Exception e) {
                    LOGGER.log(Level.WARNING, "Failed saving final player stats: " + e.getMessage(), e);
                }
                req.setAttribute("endMessage", "Your journey ends here...");
                req.getRequestDispatcher("end.jsp").forward(req, resp);
                return;
            }

            DatabaseManager db = DatabaseManager.getInstance();
            Optional<StoryNode> nodeOpt = db.getStoryNode(nextNodeId);

            if (nodeOpt.isEmpty()) {
                session.setAttribute("endMessage", 
                    "You drift into the silence of space... (missing node " + nextNodeId + ")");
                resp.sendRedirect(req.getContextPath() + "/end.jsp");
                return;
            }

            session.setAttribute("player", player);
            session.setAttribute("currentNode", nodeOpt.get());

            req.getRequestDispatcher("game.jsp").forward(req, resp);

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error processing choice", ex);
            resp.sendError(500, "Failed to process choice: " + ex.getMessage());
        }
    }
}
