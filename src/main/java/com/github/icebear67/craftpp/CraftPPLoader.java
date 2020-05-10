package com.github.icebear67.craftpp;

import com.github.icebear67.craftpp.api.machine.AbstractMachine;
import com.github.icebear67.craftpp.config.Config;
import com.github.icebear67.craftpp.config.Item;
import com.github.icebear67.craftpp.config.Lang;
import com.github.icebear67.craftpp.machine.TickListener;
import com.github.icebear67.craftpp.manager.MachineManager;
import com.github.icebear67.craftpp.manager.RecipeManager;
import com.github.icebear67.craftpp.manager.ResidenceSecMgr;
import com.github.icebear67.craftpp.manager.WorldGuardSecMgr;
import com.github.icebear67.craftpp.util.Log;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.http.Http;
import org.nutz.http.Response;

import java.io.File;
import java.util.Objects;

public class CraftPPLoader extends BukkitRunnable {
    @Override
    public void run() {
        if (CraftPP.getInst().loaded) {
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
        Log.info("Loading SecurityManager..");
        if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null) {
            if (!Bukkit.getServer().getBukkitVersion().contains("1.12")) {
                Log.warn("CraftPP NOW ONLY SUPPORT FOR " + ChatColor.UNDERLINE + "WorldGuard 6.2.2");
                Log.warn("If you have any problem,feel free to submit an issue here:");
                Log.warn(ChatColor.UNDERLINE + "https://github.com/iceBear67/CraftPP/issues");
            }
            CraftPP.getInst().addSecurityManager(new WorldGuardSecMgr());
        }
        if (Bukkit.getPluginManager().getPlugin("Residence") != null) {
            CraftPP.getInst().addSecurityManager(new ResidenceSecMgr());
        }
        Log.info("Loading Database..");
        loadDatabase();
        Log.info("Loading Recipes..");
        Bukkit.getPluginManager().registerEvents(RecipeManager.getInstance(), CraftPP.getInst());
        CraftPP.getInst().loaded = true;
    }

    @SneakyThrows
    private void loadDatabase() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(CraftPP.getConf().jdbcUrl);
        hikariConfig.setUsername(CraftPP.getConf().db_username);
        hikariConfig.setPassword(CraftPP.getConf().db_password);
        hikariConfig.setDriverClassName(CraftPP.getConf().dbDriver);
        HikariDataSource ds = new HikariDataSource(hikariConfig);
        Dao dao = new NutDao(ds);
        if (!dao.exists("machine")) {
            Log.info("Creating Table....machine");
            dao.create(AbstractMachine.class, false);
        }
        CraftPP.getInst().setDao(dao);
        MachineManager.getInstance().loadMachines();
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(CraftPP.getInst(), () -> {
            MachineManager.getInstance().saveMachines();
        }, 0, CraftPP.getConf().saveDelay * 20L);
        Log.info(dao.count("machine") + " machines and " + dao.count("linkedBlocks") + " loaded.");

    }

    private void checkUpdate() {
        Response resp = Http.get("https://api.github.com/repos/iceBear67/CraftPP/releases/latest");
        Release releaseInfo = CraftPP.getGson().fromJson(resp.getContent(), Release.class);
        if (!releaseInfo.tag_commitish.equals("master")) return;
        if (!CraftPP.getVERSION().equalsIgnoreCase(releaseInfo.tag_name)) {
            Log.warn("NEW VERSION RELEASED!");
            Log.warn(releaseInfo.published_at);
            Log.warn(releaseInfo.body);
        }
    }

    private void loadMachines() {
        MachineManager.getInstance().registerMachine(new TickListener());
        //They are not same.
        loadItems();
    }

    @SneakyThrows
    private void loadItems() {
        File dataDir = new File(CraftPP.getInst().getDataFolder().getAbsolutePath() + "/item");
        if (!dataDir.exists()) {
            dataDir.mkdir();
            CraftPP.getInst().getItemMap().forEach((k, v) -> {
                v.saveConfig();
            });
            return;
        }


        //Must be last
        for (String s : Objects.requireNonNull(dataDir.list((file, s) -> s.endsWith(".json")))) {
            Item i = new Item(s);
            i = (Item) i.reloadConfig();
            CraftPP.getInst().getItemMap().put(s.replaceAll("\\.json", ""), i);
        }
    }

    private void loadConfig() {
        CraftPP.getInst().getDataFolder().mkdir();
        Config config = new Config(CraftPP.getInst());
        File confFile = new File(CraftPP.getInst().getDataFolder().getAbsolutePath() + "/conf.json");
        if (!confFile.exists()) {
            Log.warn("config.json not exist.");
            Log.info("Saving..");
            config.saveConfig();
        }
        CraftPP.getInst().setConf((Config) config.reloadConfig());
    }

    private void loadLang() {
        File langDir = new File(CraftPP.getInst().getDataFolder().getAbsolutePath().concat("/lang"));
        if (!langDir.exists()) {
            langDir.mkdir();
        }
        Lang lang = new Lang(CraftPP.getConf().locale);
        File langFile = new File(CraftPP.getInst().getDataFolder().getAbsolutePath() + "/lang/" + CraftPP.getConf().locale + ".json");
        if (!langFile.exists()) {
            Log.warn("/lang/" + CraftPP.getConf().locale + ".json not exist.");
            Log.info("Saving Default..");
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
