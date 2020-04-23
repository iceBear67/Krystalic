package com.github.icebear67.craftpp.manager;

import com.github.icebear67.craftpp.api.machine.AbstractMachine;
import com.github.icebear67.craftpp.util.Log;
import lombok.Getter;
import org.bukkit.Location;

import java.util.LinkedHashMap;
import java.util.UUID;

public class MachineManager {
    @Getter
    private static MachineManager instance = new MachineManager();
    LinkedHashMap<UUID, AbstractMachine> machines;
    LinkedHashMap<Integer, UUID> blockMachines = new LinkedHashMap<>();

    private MachineManager() {
    }

    public void registerMachine(AbstractMachine machine) {
        Log.debug("New AbstractMachine: " + machine.getUUID().toString() + "," + machine.getId());
        machines.put(machine.getUUID(), machine);
    }

    public void bindBlock(AbstractMachine machine, Location location, String id) {
        machine.getMetadata().put("internal.location." + id, String.valueOf(location.hashCode()));
        blockMachines.put(location.hashCode(), machine.getUUID());
    }

    public void bindBlock(AbstractMachine machine, Location location) {
        bindBlock(machine, location, "DEFAULT");
    }

    public AbstractMachine getMachineByLoc(Location location) {
        return machines.get(blockMachines.get(location.hashCode()));
    }

    public AbstractMachine getMachine(UUID uuid) {
        return machines.get(uuid);
    }

    public void unbindBlock(AbstractMachine machine, Location location, String id) {
        if (!String.valueOf(location.hashCode()).equals(machine.getMetadata().get("internal.location." + id))) {
            Log.warn("Machine " + machine.getId() + "(" + machine.getUUID().toString() + ") from" + machine.getClass().getCanonicalName() + "tried to unbind an invaild location");
            return;
        }
        machine.getMetadata().remove("internal.location." + id);
        blockMachines.remove(location.hashCode());
    }

    public void saveMachines() {

    }
}
