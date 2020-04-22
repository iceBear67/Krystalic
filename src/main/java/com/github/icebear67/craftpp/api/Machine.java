package com.github.icebear67.craftpp.api;

import com.github.icebear67.craftpp.IMachine;
import com.github.icebear67.craftpp.InteractType;
import com.github.icebear67.craftpp.machine.result.Result;
import io.ebean.Model;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public abstract class Machine extends Model implements IMachine {
    @Getter
    private boolean enabled = false;
    private UUID uuid;
    @Setter
    @Getter
    private boolean frozen = false;

    public Machine() {
        if (this.uuid == null) {
            uuid = UUID.randomUUID();
            this.save();
        }
    }

    /**
     * @return uuid id
     */
    public UUID getUUID() {
        return uuid;
    }

    /**
     * dispatched each cppTick.
     *
     * @return
     */
    @Override
    public abstract Result onUpdate();

    /**
     * When a player try to interact with machine.
     *
     * @param interactType
     * @param player
     * @param location
     * @return
     */
    @Override
    public abstract boolean onInteract(InteractType interactType, Player player, Location location);

    /**
     * @return your code name NOT DISPLAY NAME
     */
    @Override
    public abstract String getId();
}
