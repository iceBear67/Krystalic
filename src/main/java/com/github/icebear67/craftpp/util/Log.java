package com.github.icebear67.craftpp.util;

import com.github.icebear67.craftpp.CraftPP;
import org.bukkit.ChatColor;

public class Log {
    private static final String FORMAT = ChatColor.AQUA.toString() + ChatColor.BOLD + "Craft" + ChatColor.GREEN + "++ " + ChatColor.RESET + ChatColor.WHITE + ">> %";

    public static void info(String mesg) {
        CraftPP.getCpp().getLogger().info(FORMAT.replaceAll("%", mesg));
    }

    public static void warn(String mesg) {
        CraftPP.getCpp().getLogger().info(FORMAT.replaceAll("%", ChatColor.RED + mesg));
    }

    public static void debug(String mesg) {
        if (!CraftPP.getConf().debug) return;
        CraftPP.getCpp().getLogger().info(FORMAT.replaceAll("%", "[D] " + ChatColor.UNDERLINE + mesg));
    }
}
