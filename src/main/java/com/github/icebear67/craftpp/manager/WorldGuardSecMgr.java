package com.github.icebear67.craftpp.manager;

import com.github.icebear67.craftpp.InteractType;
import com.github.icebear67.craftpp.Log;
import com.github.icebear67.craftpp.api.interfaces.ISecurityManager;
import com.sk89q.worldguard.bukkit.RegionQuery;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WorldGuardSecMgr implements ISecurityManager {
    public static StateFlag InteractWithMachines;

    public WorldGuardSecMgr() {
        FlagRegistry registry = WorldGuardPlugin.inst().getFlagRegistry();
        try {
            StateFlag flag = new StateFlag("craftPP-interact-with-machines", false);
            registry.register(flag);
            InteractWithMachines = flag;
        } catch (FlagConflictException e) {
            Flag<?> existing = registry.get("craftPP-interact-with-machines");
            if (existing instanceof StateFlag) {
                InteractWithMachines = (StateFlag) existing;
            } else {
                Log.warn("UNKNOWN ERROR:" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onInteract(Player player, Location location, ItemStack itemInHand, InteractType interactType) {
        if (WorldGuardPlugin.inst().canBuild(player, location)) {
            return true;
        }
        RegionQuery regionQuery = WorldGuardPlugin.inst().getRegionContainer().createQuery();

        switch (interactType) {
            case PLACE:
                return regionQuery.testState(location, player, DefaultFlag.BLOCK_PLACE);
            case BREAK:
                return regionQuery.testState(location, player, DefaultFlag.BLOCK_BREAK);
            default:
                return regionQuery.testState(location, player, InteractWithMachines);
        }
    }
}
