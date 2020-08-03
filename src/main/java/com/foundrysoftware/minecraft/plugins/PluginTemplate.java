package com.foundrysoftware.minecraft.plugins;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.validation.constraints.NotNull;

import java.util.*;
import java.util.List;
import java.util.logging.Logger;
import com.sun.org.apache.bcel.internal.generic.InstructionConstants;
import org.bukkit.potion.PotionType;

import java.util.HashMap;
import java.util.*;
import java.util.logging.Logger;

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
            if (sender instanceof Player) {
                Player me = (Player) sender;
                me.sendMessage("Plugin Working");
                return true;
            }
                if (label.equalsIgnoreCase("hawkeye")){//check command label
                    if(args[0].equalsIgnoreCase("tnt")){//check if the second word is tnt
                        if(sender instanceof Player){
                            Player me =(Player)sender;
                            PlayerInventory inventory = me.getInventory();
                            for(int i = 0; i<inventory.getSize(); i++){
                                ItemStack item = inventory.getItem(i);
                                        if(item == null){
                                            continue;
                                        }
                            if(item.getType().toString().contains("ARROW")){
                                item.setType(Material.TIPPED_ARROW);
                                item.setAmount(item.getAmount());
                                PotionMeta potionmeta = (PotionMeta)item.getItemMeta();
                                potionmeta.setBasePotionData(new PotionData(PotionType.JUMP));
                                potionmeta.addCustomEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER,10,10),true);
                                item.setItemMeta(potionmeta);

                            }
                            }

                        }
                    }else{
                        if(sender instanceof Player){
                            Player me =(Player)sender;
                            PlayerInventory inventory = me.getInventory();
                            for(int i = 0; i<inventory.getSize(); i++){
                                ItemStack item = inventory.getItem(i);
                                if(item == null){
                                    continue;
                                }
                                if(item.getType().toString().contains("ARROW")){
                                    item.setType(Material.TIPPED_ARROW);
                                    item.setAmount(item.getAmount());
                                    PotionMeta potionmeta = (PotionMeta)item.getItemMeta();
                                    potionmeta.setBasePotionData(new PotionData(PotionType.getByEffect(PotionEffectType.getByName(args[0]))));
                                    potionmeta.addCustomEffect(new PotionEffect(PotionEffectType.getByName(args[0]),10,10),true);
                                    item.setItemMeta(potionmeta);
                    }

                }
            }
        }}}
        return false;
    }
    //@Override
    public boolean onShoot(ProjectileHitEvent e){
       if(e.getEntity() instanceof Arrow) {
           Arrow arrow = (Arrow) e.getEntity();
           Entity shooter = (Entity) arrow.getShooter();
           Location loc;
           if (e.getHitBlock() != null) {
               loc = e.getHitBlock().getLocation();
           } else {
               loc = e.getHitEntity().getLocation();
           }
           Bukkit.getServer().broadcastMessage(loc.toString());
           if (arrow.hasCustomEffect(PotionEffectType.CONDUIT_POWER)) {
               loc.getWorld().createExplosion(loc,10,false,false,shooter);//location of explosion,size,setFire,DoBlockDamage,ExplosionOwner
           }
           //if(arrow.hasCustomEffect(PotionEffectType.DOLPHINS_GRACE)){
              // for(Entity ent: loc.getWorld().getNearbyEntities(loc,5,5,5)){
                   //if(ent instanceof LivingEntity){
                      // (Damageable)((LivingEntity) ent).damage(50, shooter);
                   //}
              // }
           return true;
           }
           return false;
       }
    }

