package com.foundrysoftware.minecraft.plugins;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
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
    @Override
    public boolean onShoot(ProjectileHitEvent e){
       if(e.getEntity() instanceof Arrow) {
           Location loc;
           if (e.getHitBlock() != null) {
               loc = e.getHitBlock().getLocation();
           } else {
               loc = e.getHitEntity().getLocation();
           }
           Bukkit.getServer().broadcastMessage(loc.toString());
           return true;
       }
    }
}
