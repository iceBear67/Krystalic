package com.github.icebear67.craftpp;

import com.github.icebear67.craftpp.api.interfaces.ISecurityManager;
import com.github.icebear67.craftpp.api.machine.AbstractMachine;
import com.github.icebear67.craftpp.config.Config;
import com.github.icebear67.craftpp.config.Item;
import com.github.icebear67.craftpp.config.Lang;
import com.github.icebear67.craftpp.manager.MachineManager;
import com.github.icebear67.craftpp.manager.RecipeManager;
import com.github.icebear67.craftpp.util.Log;
import com.google.gson.Gson;
import com.j256.ormlite.support.ConnectionSource;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
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
    private ConnectionSource connSource;
    @Getter
    private HashMap<String, Item> itemMap = new HashMap<>();
    @Getter
    private List<ISecurityManager> securityManagers = new ArrayList<>();
    @Getter
    private MachineManager machineManager = MachineManager.getInstance();

    @Override
    public void onEnable() {
        // Plugin startup logic
        //todo achievement
        cpp = this;
        new CraftPPLoader().runTaskAsynchronously(this);
    }

    @SneakyThrows
    @Override
    public void onDisable() {
        getServer().clearRecipes();
        Log.info("Saving Data..");
        Bukkit.getScheduler().cancelTasks(this);
        connSource.close();

        // Plugin shutdown logic
    }

    protected void setConf(Config config) {
        Log.info("Config loaded.");
        conf = config;
    }

    protected void setCs(ConnectionSource cs) {
        this.connSource = cs;
    }

    /**
     * register an item without recipe.
     *
     * @param name        Code Name NOT DISPLAY NAME
     * @param lore        Lore.
     * @param machine     Your machine class
     * @param displayName item displayname
     * @param permission  Permission for using this item
     */
    public void registerItem(String name, List<String> lore, Class<? extends AbstractMachine> machine, String displayName, String permission) {
        Item item = new Item(name);
        item.displayName = displayName;
        item.lore = lore;
        item.permission = permission;
        itemMap.put(name, item);
        Log.debug("Registering: " + displayName + "(" + name + " | " + machine.getCanonicalName() + ")");
    }

    public void registerItem(String name, List<String> lore, Class<? extends AbstractMachine> machine, String displayName) {
        registerItem(name, lore, machine, displayName, "CraftPP.item." + name);
    }

    /**
     * register an item
     *
     * @param name    Code Name NOT DISPLAY NAME
     * @param lore    Lore.
     * @param machine Your machine class
     * @param recipe  Recipe.
     */
    public void registerItem(String name, List<String> lore, Class<? extends AbstractMachine> machine, Recipe recipe) {
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
