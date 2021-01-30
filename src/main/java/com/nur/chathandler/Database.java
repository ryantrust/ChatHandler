package com.nur.chathandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;


public abstract class Database {
    final Main plugin;
    Connection connection;
    public final String nameColorTable = "namecolors";
    public final String chatColorTable = "chatcolors";
    public Database(Main instance){
        plugin = instance;
    }

    public abstract Connection getSQLConnection();

    public abstract void load();

    public void initialize(){
        connection = getSQLConnection();
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + nameColorTable + " WHERE uuid = ?");
            ResultSet rs = ps.executeQuery();
            close(ps,rs);

        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Unable to retrieve connection", ex);
        }
    }

    public String getNameColor(String string) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM " + nameColorTable + " WHERE uuid = '"+string+"';");

            rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("uuid").equalsIgnoreCase(string.toLowerCase())){
                    return rs.getString("namecolor");
                }
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Error while executing MySQL statement: ", ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, "Error while closing MySQL connection: ", ex);
            }
        }
        return "";
    }

    public void setNameColor(String uuid, String nameColor) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("REPLACE INTO " + nameColorTable + " (uuid,namecolor) VALUES(?,?)");
            ps.setString(1, uuid);
            ps.setString(2, nameColor);
            ps.executeUpdate();
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Error while executing MySQL statement: ", ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, "Error while closing MySQL connection: ", ex);
            }
        }
    }

    public String getChatColor(String string) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM " + chatColorTable + " WHERE uuid = '"+string+"';");

            rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("uuid").equalsIgnoreCase(string.toLowerCase())){
                    return rs.getString("chatcolor");
                }
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Error while executing MySQL statement: ", ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, "Error while closing MySQL connection: ", ex);
            }
        }
        return "";
    }

    public void setChatColor(String uuid, String chatColor) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("REPLACE INTO " + chatColorTable + " (uuid,chatcolor) VALUES(?,?)");
            ps.setString(1, uuid);
            ps.setString(2, chatColor);
            ps.executeUpdate();
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Error while executing MySQL statement: ", ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, "Error while closing MySQL connection: ", ex);
            }
        }
    }

    public void close(PreparedStatement ps,ResultSet rs){
        try {
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Failed to close MySQL connection: ", ex);
        }
    }
}
