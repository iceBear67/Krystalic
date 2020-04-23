package com.github.icebear67.craftpp.api.interfaces;

import com.cryptomorin.xseries.XMaterial;

public interface IPlaceable {
    /**
     * get machine blockType.
     * Using String for support 1.12~1.13 //todo migrate tool
     *
     * @return
     */
    XMaterial getBlockType();

    int getLocation();
}
