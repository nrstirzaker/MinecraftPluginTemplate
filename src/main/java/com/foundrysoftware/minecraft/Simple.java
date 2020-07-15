package com.foundrysoftware.minecraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Simple extends JavaPlugin {

    public void onEnable(){
        getLogger().info("[Simple] Start up 1");
    }

    public void onReload(){
        getLogger().info("[Simple] Server Reload");
    }

    public void onDisable(){
        getLogger().info("[Simple] Server Stopping");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("simple")){
            if (sender instanceof Player){
                Player me = (Player)sender;
                return true;
            }
        }
        return false;
    }
}
