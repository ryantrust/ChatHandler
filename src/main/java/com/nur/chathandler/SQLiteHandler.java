package com.nur.chathandler;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class SQLiteHandler extends Database {
    final String dbname;
    public SQLiteHandler(Main instance){
        super(instance);
        dbname = plugin.getConfig().getString("SQLite.Filename", "colors");
    }

    public final String SQLiteCreateNameColorsTable = "CREATE TABLE IF NOT EXISTS namecolors (" +
            "`uuid` varchar(32) NOT NULL," +
            "`namecolor` varchar(32) NOT NULL," +
            "PRIMARY KEY (`uuid`)" +
            ");";

    public final String SQLiteCreateChatColorsTable = "CREATE TABLE IF NOT EXISTS chatcolors (" +
            "`uuid` varchar(32) NOT NULL," +
            "`chatcolor` varchar(32) NOT NULL," +
            "PRIMARY KEY (`uuid`)" +
            ");";


    public Connection getSQLConnection() {
        File dataFolder = new File(plugin.getDataFolder(), dbname+".db");
        if (!dataFolder.exists()){
            try {
                File directory = new File(dataFolder.getParent());
                if(!directory.exists())directory.mkdir();
                dataFolder.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "File write error: "+dbname+".db");
            }
        }
        try {
            if(connection!=null&&!connection.isClosed()){
                return connection;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            return connection;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE,"SQLite exception on initialization", ex);
        } catch (ClassNotFoundException ex) {
            plugin.getLogger().log(Level.SEVERE, "SQLite JBDC library not found.");
        }
        return null;
    }

    public void load() {
        connection = getSQLConnection();
        try {
            Statement s = connection.createStatement();
            s.executeUpdate(SQLiteCreateNameColorsTable);
            s.executeUpdate(SQLiteCreateChatColorsTable);
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialize();
    }
}