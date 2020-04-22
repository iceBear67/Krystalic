package com.github.icebear67.craftpp.config;

import com.github.icebear67.craftpp.CraftPP;
import com.github.icebear67.craftpp.PluginConfig;

import java.util.HashMap;

public class Lang extends PluginConfig {
    public String localeName = "en_US";
    public internal internal = new internal();
    public HashMap<String, Item> items = new HashMap<>();

    public Lang(String localeName) {
        super(CraftPP.getCpp().getDataFolder().getAbsolutePath().concat("/lang"));
        this.localeName = localeName;
    }

    @Override
    public String getConfigName() {
        return localeName + ".json";
    }

    public class internal {
        public String already_loaded = "CraftPP Already loaded!";
    }
}
