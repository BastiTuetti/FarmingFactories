package de.riskonia.farmingfactories.utils.misc;

import de.riskonia.farmingfactories.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class DataManager {

    private Main plugin;

    //Files & File Configs Here
    public FileConfiguration generalcfg;
    public File generalfile;
    public FileConfiguration langcfg;
    public File langfile;
    public FileConfiguration datacfg;
    public File datafile;
    //---------------------------------

    public DataManager(Main plugin) {
        this.plugin = plugin;
        //saves/initializes the config
        saveDefaultConfig();
        saveDefaultLang();
        saveDefaultData();
    }

    public void reloadConfig() {
        //ConfigFile
        if (this.generalfile == null)
            this.generalfile = new File(this.plugin.getDataFolder(),"config.yml");

        this.generalcfg = YamlConfiguration.loadConfiguration(this.generalfile);

        InputStream defaultStream = this.plugin.getResource("config.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.generalcfg.setDefaults(defaultConfig);
        }
    }

    public void reloadLang(){
        //LanguageFile
        if (this.langfile == null)
            this.langfile = new File(this.plugin.getDataFolder(),"lang.yml");

        this.langcfg = YamlConfiguration.loadConfiguration(this.langfile);

        InputStream langStream = this.plugin.getResource("lang.yml");
        if (langStream != null) {
            YamlConfiguration langConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(langStream));
            this.langcfg.setDefaults(langConfig);
        }
    }

    public void reloadData(){
        //DataFile
        if (this.datafile == null)
            this.datafile = new File(this.plugin.getDataFolder(),"data.yml");

        this.datacfg = YamlConfiguration.loadConfiguration(this.datafile);

        InputStream dataStream = this.plugin.getResource("data.yml");
        if (dataStream != null) {
            YamlConfiguration dataConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(dataStream));
            this.datacfg.setDefaults(dataConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (this.generalcfg == null)
            reloadConfig();

        return this.generalcfg;
    }

    public FileConfiguration getLang() {
        if (this.langcfg == null)
            reloadLang();

        return this.langcfg;
    }

    public FileConfiguration getData() {
        if (this.datacfg == null)
            reloadData();

        return this.datacfg;
    }

    public void saveConfig() {
        if (this.generalcfg == null || this.generalfile == null)
            return;

        try {
            this.getConfig().save(this.generalfile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to "+ this.generalfile, e);
        }
    }

    public void saveLang() {
        if (this.langcfg == null || this.langfile == null)
            return;

        try {
            this.getLang().save(this.langfile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to "+ this.langfile, e);
        }
    }

    public void saveData() {
        if (this.datacfg == null || this.datafile == null)
            return;

        try {
            this.getData().save(this.datafile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to "+ this.datafile, e);
        }
    }

    public void saveDefaultConfig() {
        if (this.generalfile == null)
            this.generalfile = new File(this.plugin.getDataFolder(),"config.yml");

        if (!this.generalfile.exists()) {
            this.plugin.saveResource("config.yml", false);
        }
    }

    public void saveDefaultLang() {
        if (this.langfile == null)
            this.langfile = new File(this.plugin.getDataFolder(),"lang.yml");

        if (!this.langfile.exists()) {
            this.plugin.saveResource("lang.yml", false);
        }
    }

    public void saveDefaultData() {
        if (this.datafile == null)
            this.datafile = new File(this.plugin.getDataFolder(),"data.yml");

        if (!this.datafile.exists()) {
            this.plugin.saveResource("data.yml", false);
        }
    }
}
