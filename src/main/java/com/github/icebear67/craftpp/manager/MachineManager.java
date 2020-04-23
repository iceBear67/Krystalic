package com.github.icebear67.craftpp.manager;

import com.github.icebear67.craftpp.CraftPP;
import com.github.icebear67.craftpp.api.machine.AbstractMachine;
import com.github.icebear67.craftpp.data.Metadata;
import com.github.icebear67.craftpp.util.Log;
import lombok.Getter;
import org.bukkit.Location;

import java.util.LinkedHashMap;
import java.util.UUID;

public class MachineManager {
    @Getter
    private static MachineManager instance = new MachineManager();
    private LinkedHashMap<UUID, AbstractMachine> machines = new LinkedHashMap<>();
    private LinkedHashMap<Integer, UUID> blockMachines = new LinkedHashMap<>();

    private MachineManager() {
    }

    public void registerMachine(AbstractMachine machine) {
        Log.debug("New AbstractMachine: " + machine.getUUID().toString() + "," + machine.getId());
        machines.put(machine.getUUID(), machine);
    }

    public void bindBlock(AbstractMachine machine, Location location, String id) {
        machine.getMetadata().put(Metadata.LOCATION + id, String.valueOf(location.hashCode()));
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
        machine.getMetadata().remove(Metadata.LOCATION + id);
        blockMachines.remove(location.hashCode());
    }

    public void loadMachines() {
        CraftPP.getInst().getDao().query(AbstractMachine.class, null).forEach(rec -> {
            machines.put(rec.getUUID(), rec);
            Log.debug("Loading.. " + rec.getId() + " from " + rec.getClass().getCanonicalName() + " UUID:" + rec.getUUID());
            if (rec.getMetadata().containsKey(Metadata.LOCATION.toString())) {
                blockMachines.put(Integer.parseInt(rec.getMetadata().get(Metadata.LOCATION.toString())), rec.getUUID());
            }
        });
    }

    public void saveMachines() {
        machines.forEach((k, v) -> {
            CraftPP.getInst().getDao().update(v);
        });
    }
}
