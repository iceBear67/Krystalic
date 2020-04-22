package com.github.icebear67.craftpp;

import com.github.icebear67.craftpp.api.Machine;
import com.github.icebear67.craftpp.api.interfaces.ISecurityManager;
import com.github.icebear67.craftpp.config.Config;
import com.github.icebear67.craftpp.config.Item;
import com.github.icebear67.craftpp.config.Lang;
import com.github.icebear67.craftpp.manager.RecipeManager;
import com.google.gson.Gson;
import io.ebean.Database;
import lombok.Getter;
import lombok.Setter;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class CraftPP extends JavaPlugin {
    @Getter
    private static final String VERSION = "v0";
    @Getter
    private static CraftPP cpp;
    @Getter
    @Setter
    private static Lang lang;
    @Getter
    private static Gson gson = new Gson();
    @Getter
    private static Config conf;
    @Getter
    private final Metrics metrics = new Metrics(this, 7275);
    protected Boolean loaded = false;
    @Getter
    private Database db;
    @Getter
    private HashMap<String, Item> itemMap = new HashMap<>();
    @Getter
    private List<ISecurityManager> securityManagers = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        //todo achievement
        cpp = this;
        new CraftPPLoader().runTaskAsynchronously(this);
    }

    @Override
    public void onDisable() {
        getServer().clearRecipes();
        Log.info("Saving Data..");
        Bukkit.getScheduler().cancelTasks(this);
        // Plugin shutdown logic
    }

    protected void setConf(Config config) {
        Log.info("Config loaded.");
        conf = config;
    }

    protected void setDb(Database db) {
        this.db = db;
    }

    /**
     * register an item without recipe.
     *
     * @param name    Code Name NOT DISPLAY NAME
     * @param lore    Lore.
     * @param machine Your machine class
     */
    public void registerItem(String name, List<String> lore, Class<? extends Machine> machine, String displayName) {
        Item item = new Item();
        item.displayName = displayName;
        item.lore = lore;
        itemMap.put(name, item);
        Log.debug("Registering: " + displayName + "(" + name + " | " + machine.getCanonicalName() + ")");
    }

    /**
     * register an item
     *
     * @param name    Code Name NOT DISPLAY NAME
     * @param lore    Lore.
     * @param machine Your machine class
     * @param recipe  Recipe.
     */
    public void registerItem(String name, List<String> lore, Class<? extends Machine> machine, Recipe recipe) {
        registerItem(name, lore, machine, recipe.getResult().getItemMeta().getDisplayName());
        RecipeManager.getInstance().register(recipe, machine);
    }

    /**
     * add securitymanager
     */
    public void addSecurityManager(ISecurityManager smgr) {
        securityManagers.add(smgr);
    }
}
