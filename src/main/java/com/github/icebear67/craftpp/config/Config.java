package com.github.icebear67.craftpp.config;

import com.github.icebear67.craftpp.util.PluginConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class Config extends PluginConfig {

    public String locale = "en_US";
    public String jdbcUrl = "jdbc:sqlite:craftpp.db";
    public String dbDriver = "org.sqlite.JDBC";
    public String db_username = "root";
    public String db_password = "123456lol";
    public Boolean checkUpdate = true;
    public Boolean debug = false;
    public Boolean allowStackCraft = false;
    public int saveDelay = 300;

    public Config(JavaPlugin p) {
        super(p);
    }
}
