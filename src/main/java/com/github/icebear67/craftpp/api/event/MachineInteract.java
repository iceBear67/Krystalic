package com.github.icebear67.craftpp.api.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MachineInteract extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    @Getter
    @Setter
    private boolean cancelled = false;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
