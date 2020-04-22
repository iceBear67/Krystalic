package com.github.icebear67.craftpp;

import com.github.icebear67.craftpp.machine.result.Result;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface IMachine {
    String getId();

    Result onUpdate();

    boolean onInteract(InteractType interactType, Player player, Location location);

    UUID getUUID();
}
