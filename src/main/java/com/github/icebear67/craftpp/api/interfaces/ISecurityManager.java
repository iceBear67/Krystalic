package com.github.icebear67.craftpp.api.interfaces;

import com.github.icebear67.craftpp.InteractType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface ISecurityManager {
    /**
     * @param player       origin
     * @param location     block location
     * @param itemInHand   item in players hand now
     * @param interactType interact type
     * @return is it allow to do?
     */
    boolean onInteract(Player player, Location location, ItemStack itemInHand, InteractType interactType);
    //boolean onConnect(Tunnel tunnel)
}
