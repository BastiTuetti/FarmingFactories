package de.riskonia.farmingfactories.main;

import de.riskonia.farmingfactories.commands.testCmd;
import de.riskonia.farmingfactories.utils.invs.InventoryManager;
import de.riskonia.farmingfactories.utils.misc.DataManager;
import de.riskonia.farmingfactories.utils.sql.MySQL;
import de.riskonia.farmingfactories.utils.sql.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class Main extends JavaPlugin implements Listener {

    public DataManager dataManager;

    public static String PREFIX = "§aFFactories §7| §f§o";
    public MySQL SQL;
    public SQLGetter sqlm;
    public InventoryManager invman;

    @Override
    public void onEnable(){

        this.dataManager = new DataManager(this);
        this.invman = new InventoryManager(this);

        /*-------------SQL-------------*/
        this.SQL = new MySQL(this);
        this.sqlm = new SQLGetter(this);
        try {
            SQL.connect();
        } catch (ClassNotFoundException | SQLException e) {
            //e.printStackTrace();
            log("§cDatabase not connected");
        }
        if (SQL.isConnected()) {
            log("§aDatabase is connected");
            sqlm.createTable();
        }
        /*-------------SQL-------------*/


        this.getCommand("test").setExecutor(new testCmd(this));
        log("Plugin enabled.");
    }

    @Override
    public void onDisable(){

        SQL.disconnect();
        log("Plugin disabled");
    }

    public void log(String text) {
        Bukkit.getConsoleSender().sendMessage(PREFIX + text);
    }

}
