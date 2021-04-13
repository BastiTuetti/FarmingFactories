package de.riskonia.farmingfactories.utils.misc;

import org.bukkit.Location;

public class Factory {

    private Location location;
    private String type;
    private int id;
    private int inputid;
    private int outputid;

    public Factory(Location location,String type,int id,int inputid,int outputid){
        this.location = location;
        this.type = type;
        this.id = id;
        this.inputid = inputid;
        this.outputid = outputid;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInputid() {
        return inputid;
    }

    public void setInputid(int inputid) {
        this.inputid = inputid;
    }

    public int getOutputid() {
        return outputid;
    }

    public void setOutputid(int outputid) {
        this.outputid = outputid;
    }
}
