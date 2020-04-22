package com.github.icebear67.craftpp.api;

import com.github.icebear67.craftpp.IPlaceable;
import com.github.icebear67.craftpp.InteractType;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class BlockMachine extends Machine implements IPlaceable {
    @Override
    public boolean onInteract(InteractType interactType, Player player, Location location) {
        if (interactType == InteractType.PLACE && this.canPlace(player)) {
            return true;
        } else if (interactType == InteractType.BREAK && this.canBreak(player)) {
            return true;
        } else return interactType != InteractType.BREAK && interactType != InteractType.PLACE;
    }

    public boolean canPlace(Player player) {
        return true;
    }

    public boolean canBreak(Player player) {
        return true;
    }
}
