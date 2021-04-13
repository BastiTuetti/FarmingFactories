package de.riskonia.farmingfactories.utils.sql;

import de.riskonia.farmingfactories.main.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    private Main plugin;

    private String host;
    private String port;
    private String database;
    private String username;
    private String password;
    private boolean useSSL;

    private Connection connection;

    public MySQL(Main plugin) {
        this.plugin = plugin;
        host = plugin.dataManager.getConfig().getString("MySQL.host");
        port = plugin.dataManager.getConfig().getString("MySQL.port");
        database = plugin.dataManager.getConfig().getString("MySQL.database");
        username = plugin.dataManager.getConfig().getString("MySQL.username");
        password = plugin.dataManager.getConfig().getString("MySQL.password");
        useSSL = plugin.dataManager.getConfig().getBoolean("MySQL.useSSL");
    }

    public boolean isConnected(){
        return (connection != null);
    }

    public void connect() throws ClassNotFoundException, SQLException {
        if (!isConnected()) {
            String ssl = "?useSSL=false";
            if (this.useSSL) {
                ssl = "?useSSL=true";
            }
            connection = DriverManager.getConnection("jdbc:mysql://" +
                            host + ":" + port + "/" + database + ssl, username, password);
        }
    }

    public void disconnect(){
        if (isConnected()){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
