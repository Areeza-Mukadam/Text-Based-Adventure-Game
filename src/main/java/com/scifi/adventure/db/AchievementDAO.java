package com.scifi.adventure.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AchievementDAO {
    public static void grant(int playerId, int achievementId) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(
            "INSERT IGNORE INTO player_achievements (player_id, achievement_id) VALUES (?,?)");
        ps.setInt(1, playerId);
        ps.setInt(2, achievementId);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }
}
