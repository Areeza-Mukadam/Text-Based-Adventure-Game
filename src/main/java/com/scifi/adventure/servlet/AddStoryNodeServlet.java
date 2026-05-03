package com.scifi.adventure.servlet;

import com.scifi.adventure.db.DatabaseManager;
import com.scifi.adventure.model.StoryNode;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/AddStoryNodeServlet")
public class AddStoryNodeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Boolean isAdmin = (Boolean) req.getSession().getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            resp.sendRedirect("index.jsp");
            return;
        }

        int id = Integer.parseInt(req.getParameter("id"));
        String description = req.getParameter("description");
        String option1Text = req.getParameter("option1Text");
        int option1Next = Integer.parseInt(req.getParameter("option1Next"));
        String option2Text = req.getParameter("option2Text");
        int option2Next = Integer.parseInt(req.getParameter("option2Next"));
        boolean isHackingNode = req.getParameter("isHackingNode") != null;
        String hackingAnswer = req.getParameter("hackingAnswer");
        String requiredItem = req.getParameter("requiredItem");

        // ✅ Populate StoryNode
        StoryNode node = new StoryNode();
        node.setId(id);
        node.setDescription(description);
        node.setOption1Text(option1Text);
        node.setOption1Next(option1Next);
        node.setOption2Text(option2Text);
        node.setOption2Next(option2Next);
        node.setHackingNode(isHackingNode);
        node.setHackingAnswer(hackingAnswer);
        node.setRequiredItem(requiredItem);

        DatabaseManager db = DatabaseManager.getInstance();
        boolean success = db.addStoryNode(node);

        if (success) {
            req.setAttribute("message", "Story node added successfully!");
        } else {
            req.setAttribute("message", "Failed to add story node.");
        }
        req.getRequestDispatcher("adminDashboard.jsp").forward(req, resp);
    }
}
