package com.scifi.adventure.db;

import com.scifi.adventure.model.LeaderboardEntry;
import com.scifi.adventure.model.Player;
import com.scifi.adventure.model.StoryNode;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {
    private static final Logger LOGGER = Logger.getLogger(DatabaseManager.class.getName());

    // --- Singleton instance ---
    private static DatabaseManager instance;

    private DatabaseManager() {
        // private constructor prevents direct instantiation
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    // --- Register a new user ---
    public boolean registerUser(String username, String password) {
        String sql = "INSERT INTO users (username, password_hash, role) VALUES (?, ?, 'player')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password); // plain text for now
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error registering user: " + username, e);
            return false;
        }
    }

    // --- Check login credentials ---
    public int checkUserLogin(String username, String password) {
        String sql = "SELECT password_hash FROM users WHERE username=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return 0; // user not found
            }
            String storedHash = rs.getString("password_hash");
            if (storedHash.equals(password)) {
                return 2; // success
            } else {
                return 1; // wrong password
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Login failed for " + username, e);
            return 0;
        }
    }

    // --- StoryNode retrieval ---
    public Optional<StoryNode> getStoryNode(int id) {
        String sql = "SELECT * FROM story_nodes WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                StoryNode node = new StoryNode();
                node.setId(rs.getInt("id"));
                node.setDescription(rs.getString("description"));
                node.setOption1Text(rs.getString("option1_text"));
                node.setOption1Next(rs.getInt("option1_next"));
                node.setOption2Text(rs.getString("option2_text"));
                node.setOption2Next(rs.getInt("option2_next"));
                node.setHackingNode(rs.getBoolean("is_hacking_node"));
                node.setHackingAnswer(rs.getString("hacking_answer"));
                node.setRequiredItem(rs.getString("required_item"));
                return Optional.of(node);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to load node " + id, e);
        }
        return Optional.empty();
    }

    public boolean addStoryNode(StoryNode node) {
        String sql = "INSERT INTO story_nodes " +
                     "(id, description, option1_text, option1_next, option2_text, option2_next, is_hacking_node, hacking_answer, required_item) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, node.getId());
            ps.setString(2, node.getDescription());
            ps.setString(3, node.getOption1Text());
            ps.setInt(4, node.getOption1Next());
            ps.setString(5, node.getOption2Text());
            ps.setInt(6, node.getOption2Next());
            ps.setBoolean(7, node.isHackingNode());
            ps.setString(8, node.getHackingAnswer());
            ps.setString(9, node.getRequiredItem());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to add story node", e);
            return false;
        }
    }

    // --- Save or update player state ---
    public void saveOrUpdatePlayer(Player player) {
        String sql = "INSERT INTO players (username, health, oxygen, threat, score, currentNodeId) " +
                     "VALUES (?, ?, ?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE health=?, oxygen=?, threat=?, score=?, currentNodeId=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, player.getName());
            ps.setInt(2, player.getHealth());
            ps.setInt(3, player.getOxygen());
            ps.setInt(4, player.getThreatLevel());
            ps.setInt(5, player.getScore());
            ps.setInt(6, player.getCurrentNodeId());
            ps.setInt(7, player.getHealth());
            ps.setInt(8, player.getOxygen());
            ps.setInt(9, player.getThreatLevel());
            ps.setInt(10, player.getScore());
            ps.setInt(11, player.getCurrentNodeId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to save player: " + player.getName(), e);
        }
    }

    // --- List saved player names ---
    public List<String> listSavedPlayerNames() {
        List<String> names = new ArrayList<>();
        String sql = "SELECT username FROM players";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                names.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to list saved players", e);
        }
        return names;
    }

    // --- Leaderboard ---
    public List<LeaderboardEntry> getTopScores() {
        List<LeaderboardEntry> leaderboard = new ArrayList<>();
        String sql = "SELECT username, score FROM players ORDER BY score DESC LIMIT 3";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                LeaderboardEntry entry = new LeaderboardEntry(
                    rs.getString("username"),
                    rs.getInt("score")
                );
                leaderboard.add(entry);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to fetch leaderboard", e);
        }
        return leaderboard;
    }

    // --- Load player by name ---
    public Optional<Player> loadPlayerByName(String username) {
        String sql = "SELECT * FROM players WHERE username=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Player player = new Player();
                player.setName(rs.getString("username"));
                player.setHealth(rs.getInt("health"));
                player.setOxygen(rs.getInt("oxygen"));
                player.setThreatLevel(rs.getInt("threat"));
                player.setScore(rs.getInt("score"));
                player.setCurrentNodeId(rs.getInt("currentNodeId"));
                return Optional.of(player);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to load player: " + username, e);
        }
        return Optional.empty();
    }
}
