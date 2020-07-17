package com.foundrysoftware.minecraft.plugins;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.validation.constraints.NotNull;

public class PluginTemplate extends JavaPlugin {

    private final String PLUGIN_NAME = "PluginTemplate";

    public void onEnable(){
        getLogger().info("["+ PLUGIN_NAME +"] Started ");
    }

    public void onReload(){
        getLogger().info("["+ PLUGIN_NAME +"] Server Reload");
    }

    public void onDisable(){
        getLogger().info("["+ PLUGIN_NAME +"] Server Stopping");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (label.equalsIgnoreCase("simple")){
            if (sender instanceof Player){
                Player me = (Player)sender;
                me.sendMessage("Plugin Working");
                return true;
            }
        }
        return false;
    }
    :Override
    public boolean onInteract(PlayerInteractEvent e){
        if(e.getItem()!=null && e.getItem().getItemMeta().getDisplayName().contains("score")) {
            Location loc = e.getClickedBlock().getLocation();
            loc.setY(loc.getY() + 1);
            World world = loc.getWorld();
            ArmorStand a = world.spawn(loc, ArmorStand.class);
            a.setVisible(false);
            a.addScoreboardTag("score");
            return true;
        }
        return false;
    }
}
