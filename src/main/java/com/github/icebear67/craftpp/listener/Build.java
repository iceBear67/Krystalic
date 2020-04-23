package com.github.icebear67.craftpp.listener;

import com.github.icebear67.craftpp.CraftPP;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Build implements Listener {
    @EventHandler
    public void onPlace() {

    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!CraftPP.getCpp().isCPPBlock(e.getBlock())) return;

    }
}
