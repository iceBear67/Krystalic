package com.github.icebear67.craftpp;

import com.github.icebear67.craftpp.api.interfaces.ISecurityManager;
import com.github.icebear67.craftpp.api.machine.AbstractMachine;
import com.github.icebear67.craftpp.config.Config;
import com.github.icebear67.craftpp.config.Item;
import com.github.icebear67.craftpp.config.Lang;
import com.github.icebear67.craftpp.data.Metadata;
import com.github.icebear67.craftpp.manager.MachineManager;
import com.github.icebear67.craftpp.manager.RecipeManager;
import com.github.icebear67.craftpp.util.Log;
import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.nutz.dao.Dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class CraftPP extends JavaPlugin {
    @Getter
    private static final String VERSION = "v0";
    @Getter
    private static CraftPP inst;
    @Getter
    @Setter
    private static Lang lang;
    @Getter
    private static final Gson gson = new Gson();
    @Getter
    private static Config conf;
    @Getter
    private final Metrics metrics = new Metrics(this, 7275);
    protected Boolean loaded = false;
    @Getter
    @Setter(value = AccessLevel.PROTECTED)
    private Dao dao;
    @Getter
    private final HashMap<String, Item> itemMap = new HashMap<>();
    @Getter
    private final List<ISecurityManager> securityManagers = new ArrayList<>();
    @Getter
    private final MachineManager machineManager = MachineManager.getInstance();

    @Override
    public void onEnable() {
        inst = this;
        new CraftPPLoader().runTaskAsynchronously(this);
    }

    @SneakyThrows
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

    public boolean isCPPBlock(Block block) {
        AbstractMachine machine = machineManager.getMachineByLoc(block.getLocation());
        return machine != null && Material.valueOf(machine.getMetadata().get(Metadata.MATERIAL)).equals(block.getType());
    }
}
