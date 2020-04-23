package com.github.icebear67.craftpp.api.machine;

import com.github.icebear67.craftpp.InteractType;
import com.github.icebear67.craftpp.api.interfaces.IPlaceable;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class AbstractBlockMachine extends AbstractMachine implements IPlaceable {
    /**
     * get location.
     *
     * @return location(hashcode)
     */
    @Getter
    private int location;

    public void updateLocation(Location location) {
        updateLocation(location.hashCode());
    }

    public void updateLocation(int hashcode) {
        this.location = hashcode;
    }

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
