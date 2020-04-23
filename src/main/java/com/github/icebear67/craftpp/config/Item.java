package com.github.icebear67.craftpp.config;

import com.github.icebear67.craftpp.CraftPP;
import com.github.icebear67.craftpp.util.PluginConfig;

import java.util.ArrayList;
import java.util.List;

public class Item extends PluginConfig {
    public String permission;
    public String codeName;
    public String displayName;
    public List<String> lore = new ArrayList<>();

    public Item(String name) {
        super(CraftPP.getInst().getDataFolder().getAbsolutePath() + "/item");
        this.codeName = name;
    }

    @Override
    public String getConfigName() {
        return codeName + ".json";
    }
}
