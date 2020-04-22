package com.github.icebear67.craftpp;

import com.github.icebear67.craftpp.config.Config;
import com.github.icebear67.craftpp.config.Lang;
import com.github.icebear67.craftpp.item.EngineerHammer;
import com.github.icebear67.craftpp.machine.TickListener;
import com.github.icebear67.craftpp.manager.MachineManager;
import com.github.icebear67.craftpp.manager.RecipeManager;
import com.github.icebear67.craftpp.manager.ResidenceSecMgr;
import com.github.icebear67.craftpp.manager.WorldGuardSecMgr;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class CraftPPLoader extends BukkitRunnable {
    @Override
    public void run() {
        if (CraftPP.getCpp().loaded) {
            Log.info(CraftPP.getLang().internal.already_loaded);
            //Hot reload is not supported xD
            //Maybe future?
            return;
        }
        Log.info("Loading Config..");
        loadConfig();
        if (CraftPP.getConf().checkUpdate) {
            Log.info("Checking Update..");
            checkUpdate();
        }
        Log.info("Loading Language..");
        loadLang();
        Log.info("Loading Database..");
        loadDatabase();
        Log.info("Doing something last..");
        Bukkit.getPluginManager().registerEvents(RecipeManager.getInstance(), CraftPP.getCpp());
        if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null) {
            if (!Bukkit.getServer().getBukkitVersion().contains("1.12")) {
                Log.warn("CraftPP NOW ONLY SUPPORT FOR " + ChatColor.UNDERLINE + "WorldGuard 6.2.2");
                Log.warn("If you have any problem,feel free to submit an issue here:");
                Log.warn(ChatColor.UNDERLINE + "https://github.com/iceBear67/CraftPP/issues");
            }
            CraftPP.getCpp().addSecurityManager(new WorldGuardSecMgr());
        }
        if (Bukkit.getPluginManager().getPlugin("Residence") != null) {
            CraftPP.getCpp().addSecurityManager(new ResidenceSecMgr());
        }
        CraftPP.getCpp().loaded = true;
    }

    private void loadDatabase() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(CraftPP.getConf().jdbcUrl);
        dataSourceConfig.setDriver(CraftPP.getConf().dbDriver);
        DatabaseConfig dbConf = new DatabaseConfig();
        dbConf.setDataSourceConfig(dataSourceConfig);
        CraftPP.getCpp().setDb(DatabaseFactory.create(dbConf));
    }

    private void checkUpdate() {
        try {
            URL check = new URL("https://api.github.com/repos/iceBear67/CraftPP/releases/latest");
            BufferedReader in = new BufferedReader(new InputStreamReader(check.openConnection().getInputStream()));
            Release releaseInfo = CraftPP.getGson().fromJson(in, Release.class);
            if (!releaseInfo.tag_commitish.equals("master")) return;
            if (!CraftPP.getVERSION().equalsIgnoreCase(releaseInfo.tag_name)) {
                Log.warn("NEW VERSION RELEASED!");
                Log.warn(releaseInfo.published_at);
                Log.warn(releaseInfo.body);
            }
        } catch (IOException e) {
            Log.warn("Failed to check Update!" + e.getMessage());
        }
    }

    private void loadMachines() {
        MachineManager.getInstance().registerMachine(new TickListener());
        loadItems();
    }

    private void loadItems() {
        EngineerHammer.register();
    }

    private void loadConfig() {
        CraftPP.getCpp().getDataFolder().mkdir();
        Config config = new Config(CraftPP.getCpp());
        File confFile = new File(CraftPP.getCpp().getDataFolder().getAbsolutePath() + "/conf.json");
        if (!confFile.exists()) {
            Log.warn("config.json not exist.");
            Log.info("Saving..");
            config.saveConfig();
        }
        CraftPP.getCpp().setConf((Config) config.reloadConfig());
    }

    private void loadLang() {
        File langDir = new File(CraftPP.getCpp().getDataFolder().getAbsolutePath().concat("/lang"));
        if (!langDir.exists()) {
            langDir.mkdir();
        }
        Lang lang = new Lang(CraftPP.getConf().locale);
        File langFile = new File(CraftPP.getCpp().getDataFolder().getAbsolutePath() + "/lang/" + CraftPP.getConf().locale + ".json");
        if (!langFile.exists()) {
            Log.warn("/lang/" + CraftPP.getConf().locale + ".json not exist.");
            Log.info("Saving..");
            lang.saveConfig();
        }
        lang = (Lang) lang.reloadConfig();
        CraftPP.setLang(lang);
    }

    private class Release {
        public String tag_name;
        public String tag_commitish;
        public String body;
        public String published_at;
    }
}
