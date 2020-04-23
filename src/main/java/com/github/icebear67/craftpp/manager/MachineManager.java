package com.github.icebear67.craftpp.manager;

import com.github.icebear67.craftpp.api.machine.AbstractBlockMachine;
import com.github.icebear67.craftpp.api.machine.AbstractMachine;
import com.github.icebear67.craftpp.util.Log;
import lombok.Getter;

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
        if (machine instanceof AbstractBlockMachine) {
            AbstractBlockMachine blockMachine = (AbstractBlockMachine) machine;
            blockMachine.getLocations().forEach(loc -> {
                blockMachines.put(loc.hashCode(), machine.getUUID());
            });
        }
    }

    public void saveMachines() {
    }
}
