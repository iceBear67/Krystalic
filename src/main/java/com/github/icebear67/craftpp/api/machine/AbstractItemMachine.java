package com.github.icebear67.craftpp.api.machine;

import com.cryptomorin.xseries.XMaterial;

public abstract class AbstractItemMachine extends AbstractMachine {
    int energy = getMaxEnergy();

    @Override
    public int getEnergy() {
        return energy;
    }

    @Override
    public int pushEnergy(int value) {
        for (int i = 0; i != value; i++) {
            if (energy + i > getMaxEnergy()) {
                return energy;
            }
            energy = energy + i;
        }
        return energy;
    }

    @Override
    public int pullEnergy(int value) {
        for (int i = 0; i != value; i++) {
            if (energy - i < 0) {
                return energy;
            }
            energy = energy - i;
        }
        return value;
    }

    @Override
    public int getMaxEnergy() {
        return 0;
    }

    public abstract XMaterial getItemType();
}
