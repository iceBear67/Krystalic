package com.github.icebear67.craftpp.manager;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.github.icebear67.craftpp.InteractType;
import com.github.icebear67.craftpp.api.interfaces.ISecurityManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ResidenceSecMgr implements ISecurityManager {
    public ResidenceSecMgr() {
        FlagPermissions.addFlag("interactWithMachine");
    }

    @Override
    public boolean onInteract(Player player, Location location, ItemStack itemInHand, InteractType interactType) {
        ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(location);
        if (res == null) return true;
        ResidencePermissions perms = res.getPermissions();
        if (perms.playerHas(player, Flags.build, false) || perms.playerHas(player, Flags.admin, false)) {
            return true;
        }
        switch (interactType) {
            case BREAK:
                return perms.playerHas(player, Flags.destroy, false);
            case PLACE:
                return perms.playerHas(player, Flags.place, false);
            default:
                return perms.playerHas(player, "interactWithMachine", false);
        }
    }
}
