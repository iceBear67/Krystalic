package com.github.icebear67.craftpp.connector;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockConnector implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        //event.getClickedBlock().getLocation().hashCode()

    }
}
