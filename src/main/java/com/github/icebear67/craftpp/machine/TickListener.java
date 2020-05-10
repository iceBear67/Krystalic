package com.github.icebear67.craftpp.machine;

import com.github.icebear67.craftpp.InteractType;
import com.github.icebear67.craftpp.api.event.TickEvent;
import com.github.icebear67.craftpp.api.machine.AbstractMachine;
import com.github.icebear67.craftpp.machine.result.Result;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * call TickEvent for additional plugins.
 */
public class TickListener extends AbstractMachine {
    @Getter
    private final String id = "additional_plugin";

    @Override
    public Result onUpdate() {
        Bukkit.getServer().getPluginManager().callEvent(new TickEvent());
        return Result.NORMAL;
    }

    @Override
    public boolean onInteract(InteractType interactType, Player player, Location location) {
        return false;
    }

    @Override
    public int getMaxEnergy() {
        return 0;
    }
}
