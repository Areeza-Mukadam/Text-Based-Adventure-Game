package com.scifi.adventure.servlet;

import com.scifi.adventure.db.DatabaseManager;
import com.scifi.adventure.model.LeaderboardEntry;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * LeaderboardServlet retrieves top scores and forwards them to leaderboard.jsp.
 */
@WebServlet("/leaderboard")
public class LeaderboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Fetch top scores from DB
        DatabaseManager db = DatabaseManager.getInstance();
        List<LeaderboardEntry> topScores = db.getTopScores();

        // Attach to request scope
        request.setAttribute("leaderboard", topScores);   // ✅ fixed

        // Forward to JSP
        request.getRequestDispatcher("leaderboard.jsp").forward(request, response);
    }
}
