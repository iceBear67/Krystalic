package com.github.icebear67.craftpp.machine.basic;

import com.cryptomorin.xseries.XMaterial;
import com.github.icebear67.craftpp.api.machine.AbstractBlockMachine;
import com.github.icebear67.craftpp.machine.result.Result;

public class BatTorch extends AbstractBlockMachine {
    @Override
    public XMaterial getBlockType() {
        return XMaterial.TORCH;
    }


    @Override
    public Result onUpdate() {
        return Result.ENERGY_NOT_ENOUGH;
    }

    @Override
    public String getId() {
        return "bat_torch";
    }

    @Override
    public int getMaxEnergy() {
        return 0;
    }
}
