package com.github.icebear67.craftpp.api.machine;

import com.github.icebear67.craftpp.InteractType;
import com.github.icebear67.craftpp.api.interfaces.IMachine;
import com.github.icebear67.craftpp.api.interfaces.IPowerable;
import com.github.icebear67.craftpp.machine.result.Result;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

@DatabaseTable(tableName = "machines")
public abstract class AbstractMachine implements IMachine, IPowerable {
    @Getter
    private boolean enabled = false;
    @DatabaseField(id = true, unique = true)
    private UUID uuid;

    @Setter
    @Getter
    private boolean frozen = false;

    public AbstractMachine() {
        if (this.uuid == null) {
            uuid = UUID.randomUUID();
            //todo save here
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
