package com.github.icebear67.craftpp.item;

import com.cryptomorin.xseries.XMaterial;
import com.github.icebear67.craftpp.CraftPP;
import com.github.icebear67.craftpp.InteractType;
import com.github.icebear67.craftpp.api.machine.AbstractMachine;
import com.github.icebear67.craftpp.machine.result.Result;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Objects;

public class EngineerHammer extends AbstractMachine {
    private static final String id = "engineer_hammer";

    public static Recipe getRecipe() {
        ItemStack hammer = new ItemStack(Objects.requireNonNull(XMaterial.IRON_AXE.parseMaterial()));
        ItemMeta im = hammer.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Engineer's Hammer.");
        lore.add("Used for launch or modify machines");
        im.setLore(lore);
        hammer.setItemMeta(im);
        ShapedRecipe shapedRecipe = new ShapedRecipe(hammer);
        shapedRecipe.shape("  ");
        return shapedRecipe;
    }

    public static void register() {
        CraftPP.getInst().registerItem(id, getRecipe().getResult().getItemMeta().getLore(), EngineerHammer.class, getRecipe());
    }


    @Override
    public Result onUpdate() {
        return Result.NORMAL;
    }

    @Override
    public boolean onInteract(InteractType interactType, Player player, Location location) {
        return false;
    }

    @Override
    public String getId() {
        return "engineer_hammer";
    }

    @Override
    public int getMaxEnergy() {
        return 1024;
    }
}
