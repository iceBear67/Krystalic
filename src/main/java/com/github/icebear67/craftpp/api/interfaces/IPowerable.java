package com.github.icebear67.craftpp.api.interfaces;

public interface IPowerable {
    int getEnergy();

    /**
     * Push energy to machine
     *
     * @param value energy
     * @return succeed
     */
    int pushEnergy(int value);

    /**
     * pull energy from machine
     *
     * @param value energy
     * @return succeed
     */
    int pullEnergy(int value);

    int getMaxEnergy();
}
