package com.github.icebear67.craftpp.machine;

import com.github.icebear67.craftpp.InteractType;
import com.github.icebear67.craftpp.api.Machine;
import com.github.icebear67.craftpp.api.event.TickEvent;
import com.github.icebear67.craftpp.machine.result.Result;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * call TickEvent for additional plugins.
 */
public class TickListener extends Machine {
    @Getter
    private String id = "additional_plugin";

    @Override
    public Result onUpdate() {
        Bukkit.getServer().getPluginManager().callEvent(new TickEvent());
        return Result.NORMAL;
    }

    @Override
    public boolean onInteract(InteractType interactType, Player player, Location location) {
        return false;
    }
}
