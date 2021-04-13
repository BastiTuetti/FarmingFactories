package de.riskonia.farmingfactories.utils.sql;

import de.riskonia.farmingfactories.main.Main;
import de.riskonia.farmingfactories.utils.misc.Factory;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLGetter {

    private Main plugin;
    public SQLGetter(Main plugin) {
        this.plugin = plugin;
    }

    public void createTable(){
        PreparedStatement ps;
        try {
            ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS ffactoriesFList "
                                                            + "(ID INT NOT NULL AUTO_INCREMENT,TYPE VARCHAR(100),ININV INT(100),OUTINV INT(100),LOCW VARCHAR(100),LOCX DOUBLE(100,2),LOCY DOUBLE(100,2),LOCZ DOUBLE(100,2),"
                                                            + "PRIMARY KEY (ID))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int createFactory(String type,int input, int output, Location loc){
        try{
            String world = loc.getWorld().getName();
            double x = loc.getX();
            double y = loc.getY();
            double z = loc.getZ();
            if (!existsFactory(loc)) {
                PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("INSERT INTO ffactoriesFList"
                        + " (ID,TYPE,ININV,OUTINV,LOCW,LOCX,LOCY,LOCZ) VALUES (NULL,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, type);
                ps.setInt(2, input);
                ps.setInt(3, output);
                ps.setString(4, world);
                ps.setDouble(5, x);
                ps.setDouble(6, y);
                ps.setDouble(7, z);
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()) {
                    int id = rs.getInt(1);
                    return id;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    };

    public boolean existsFactory(int id) {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM ffactoriesFList WHERE ID=?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsFactory(Location loc){
        try {
            String world = loc.getWorld().getName();
            double x = loc.getX();
            double y = loc.getY();
            double z = loc.getZ();

            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM ffactoriesFList WHERE LOCW=? AND LOCX=? AND LOCY=? AND LOCZ=?");
            ps.setString(1,world);
            ps.setDouble(2,x);
            ps.setDouble(3,y);
            ps.setDouble(4,z);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteFactory(int id){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("DELETE FROM ffactoriesFList WHERE ID=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Factory getFactory(int id){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM ffactoriesFList WHERE ID=?");
            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Location loc = new Location(Bukkit.getWorld(rs.getString("LOCW")),rs.getDouble("LOCX"),
                        rs.getDouble("LOCY"),rs.getDouble("LOCZ"));
                return new Factory(loc,rs.getString("TYPE"),id,rs.getInt("ININV"),rs.getInt("OUTINV"));
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Factory getFactory(Location loc){
        try {
            String world = loc.getWorld().getName();
            double x = loc.getX();
            double y = loc.getY();
            double z = loc.getZ();

            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM ffactoriesFList WHERE (LOCW,LOCX,LOCY,LOCZ) VALUES (?,?,?,?)");
            ps.setString(1,world);
            ps.setDouble(2,x);
            ps.setDouble(3,y);
            ps.setDouble(4,z);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Factory(loc,rs.getString("TYPE"),rs.getInt("ID"),rs.getInt("ININV"),rs.getInt("OUTINV"));
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
