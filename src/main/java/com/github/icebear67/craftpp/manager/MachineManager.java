package com.github.icebear67.craftpp.manager;

import com.github.icebear67.craftpp.CraftPP;
import com.github.icebear67.craftpp.Log;
import com.github.icebear67.craftpp.api.BlockMachine;
import com.github.icebear67.craftpp.api.Machine;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.UUID;

public class MachineManager {
    @Getter
    private static MachineManager instance = new MachineManager();
    LinkedHashMap<UUID, Machine> machines = new LinkedHashMap<>();
    LinkedHashMap<Integer, UUID> blockMachines = new LinkedHashMap<>();

    private MachineManager() {
    }

    public void registerMachine(Machine machine) {
        Log.debug("New Machine: " + machine.getUUID().toString() + "," + machine.getName());
        machines.put(machine.getUUID(), machine);
        if (machine instanceof BlockMachine) {
            BlockMachine blockMachine = (BlockMachine) machine;
            blockMachine.getLocations().forEach(loc -> {
                blockMachines.put(loc.hashCode(), machine.getUUID());
            });
        }
    }

    public void saveMachines() {
        CraftPP.getCpp().getDb().save(machines);
        CraftPP.getCpp().getDb().save(blockMachines);
    }
}
