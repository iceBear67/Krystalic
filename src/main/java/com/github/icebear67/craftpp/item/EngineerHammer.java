package com.github.icebear67.craftpp.item;

import com.cryptomorin.xseries.XMaterial;
import com.github.icebear67.craftpp.CraftPP;
import com.github.icebear67.craftpp.InteractType;
import com.github.icebear67.craftpp.api.machine.AbstractMachine;
import com.github.icebear67.craftpp.data.CPPItem;
import com.github.icebear67.craftpp.machine.result.Result;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class EngineerHammer extends AbstractMachine {
    private static final String id = CPPItem.ENGINEER_HAMMER.toString();

    public static Recipe getRecipe() {
        ItemStack hammer = new ItemStack(Objects.requireNonNull(XMaterial.IRON_AXE.parseMaterial()));
        ItemMeta im = hammer.getItemMeta();
        im.setLore(CraftPP.getLang().items.get(id).lore);
        hammer.setItemMeta(im);
        ShapedRecipe shapedRecipe = new ShapedRecipe(hammer);
        shapedRecipe.shape("xyx", " z ", " z ")
                .setIngredient('x', XMaterial.IRON_BLOCK.parseMaterial())
                .setIngredient('y', XMaterial.EMERALD.parseMaterial());
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
        this.pullEnergy(1);
        if (this.getEnergy() <= 0) {
            player.sendMessage("We have no power");
        }
        if (player.isSneaking()) {
            player.sendMessage("当前电量：" + this.getEnergy());
        }
        return true;
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
