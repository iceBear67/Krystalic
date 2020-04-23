package com.github.icebear67.craftpp.connector;

import com.github.icebear67.craftpp.CraftPP;
import com.github.icebear67.craftpp.InteractType;
import com.github.icebear67.craftpp.manager.MachineManager;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class ItemConnector implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!event.getAction().name().contains("AIR")) {
            NBTItem nbtItem = new NBTItem(event.getItem());
            String uuid = nbtItem.getString("cppUUID");
            if (uuid != null) {
                InteractType interactType;
                switch (event.getAction()) {
                    case LEFT_CLICK_AIR:
                    case LEFT_CLICK_BLOCK:
                        interactType = InteractType.LEFT_CLICK;
                    case RIGHT_CLICK_AIR:
                    case RIGHT_CLICK_BLOCK:
                        interactType = InteractType.RIGHT_CLICK;
                    default:
                        interactType = null;
                }
                if (interactType != null) {
                    AtomicBoolean canBuild = new AtomicBoolean(true);
                    InteractType finalInteractType = interactType;
                    CraftPP.getInst().getSecurityManagers().forEach(smgr -> {
                        boolean a = smgr.onInteract(event.getPlayer(), event.getClickedBlock().getLocation(), event.getItem(), finalInteractType);
                        if (canBuild.get() != false) {
                            canBuild.set(a);
                        }
                    });
                    if (!canBuild.get()) {
                        event.setCancelled(true);
                        return;
                    }
                    MachineManager.getInstance().getMachine(UUID.fromString(uuid)).onInteract(interactType, event.getPlayer(), event.getClickedBlock().getLocation());
                }
            }
        }
    }
}
