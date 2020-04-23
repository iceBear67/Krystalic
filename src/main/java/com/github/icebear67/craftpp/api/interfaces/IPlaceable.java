package com.github.icebear67.craftpp.api.interfaces;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Location;

import java.util.List;

public interface IPlaceable {
    /**
     * get machine blockType.
     * Using String for support 1.12~1.13 //todo migrate tool
     *
     * @return
     */
    XMaterial getBlockType();

    List<Location> getLocations();
}
