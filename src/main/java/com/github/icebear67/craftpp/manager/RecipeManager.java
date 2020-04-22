package com.github.icebear67.craftpp.manager;

import com.github.icebear67.craftpp.CraftPP;
import com.github.icebear67.craftpp.api.Machine;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

public class RecipeManager implements Listener {
    @Getter
    private static RecipeManager instance = new RecipeManager();
    private HashMap<Integer, Class<? extends Machine>> items = new HashMap<>();

    private RecipeManager() {
    }

    public void register(Recipe recipe, Class<? extends Machine> machine) {
        items.put(recipe.getResult().hashCode(), machine);
    }

    @EventHandler
    private void onCraft(CraftItemEvent event) {
        if (items.containsKey(event.getRecipe().getResult().hashCode())) {
            ItemStack target = event.getRecipe().getResult();
            List<String> originalLore = target.getItemMeta().getLore();
            ItemMeta im = target.getItemMeta();
            if (target.getAmount() > 1) {
                if (!CraftPP.getConf().allowStackCraft) {
                    event.setCancelled(true);
                    return;
                }
            }
            try {
                Machine machine = items.get(event.getRecipe().getResult().hashCode()).getDeclaredConstructor().newInstance();
                MachineManager.getInstance().registerMachine(machine);
                originalLore.add("");
                originalLore.add(ChatColor.GRAY + machine.getUUID().toString());
                im.setLore(originalLore);
                target.setItemMeta(im);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}