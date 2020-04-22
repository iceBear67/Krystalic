package com.github.icebear67.craftpp;

public interface IPowerable {
    int getEnergy();

    void pushEnergy(int value);

    void pullEnergy(int value);
}
