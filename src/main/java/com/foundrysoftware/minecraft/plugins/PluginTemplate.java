package com.foundrysoftware.minecraft.plugins;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.PluginManager;
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

public class PluginTemplate extends JavaPlugin implements Listener {
    private static final String ARROW_TYPE_TNT = "tnt";
    private final String PLUGIN_NAME = "PluginTemplate";
    public static boolean destroysBlocks = false;
    public static boolean setFire = false;
@Override
    public void onEnable() {
        getLogger().info("[" + PLUGIN_NAME + "] Started ");
        PluginManager pluginmanager = Bukkit.getPluginManager();
        pluginmanager.registerEvents(this,this);
        FileConfiguration config = getConfig();
        if(getConfig()==null){

           config.createSection("destroysBlocks");
           config.addDefault("destroysBlocks",false);
           config.createSection("setFire");
           config.addDefault("setFire",false);
           saveDefaultConfig();

        }
        destroysBlocks=config.getBoolean("destroysBlocks");
        setFire = config.getBoolean("setFire");

    }

    public void onReload() {
        getLogger().info("[" + PLUGIN_NAME + "] Server Reload");
    }

    public void onDisable() {
        getLogger().info("[" + PLUGIN_NAME + "] Server Stopping");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        String hawkeye = "hawkeye";
            if (label.equalsIgnoreCase(hawkeye)) {
                if (args[0].equalsIgnoreCase(ARROW_TYPE_TNT)) {
                   arrow(sender,"CONDUIT_POWER");
                   return true;
                } else {
                    arrow(sender,args[0]);
                    return true;
                }
            }

        return false;
    }


    @EventHandler
    public boolean onShoot(ProjectileHitEvent e) {
        if (e.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow) e.getEntity();
            Entity shooter = (Entity) arrow.getShooter();
            Location loc=getLocation(e);
            Bukkit.getServer().broadcastMessage(loc.toString());
            if (arrow.hasCustomEffect(PotionEffectType.CONDUIT_POWER)) {
                int size = 10;


                loc.getWorld().createExplosion(loc, size, setFire, destroysBlocks, shooter);
            }

            return true;
        }
        return false;
    }

    private boolean isArrow(ItemStack item) {
        if (item.getType() == Material.TIPPED_ARROW || item.getType() == Material.ARROW || item.getType() == Material.SPECTRAL_ARROW) {
            return true;
        } else {
            return false;
        }

    }

    private boolean areaDamage(Location loc, int area, int damage, Entity shooter) {
        int Yarea = area;
        int Zarea =area;
        int Xarea= area;
        for (Entity entity : loc.getWorld().getNearbyEntities(loc, Xarea, Yarea, Zarea)) {
            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).damage(damage, shooter);

            }
        }
        return true;
    }

    private Location getLocation(ProjectileHitEvent event) {

        Arrow arrow = (Arrow) event.getEntity();
        Entity shooter = (Entity) arrow.getShooter();
        Location loc;
        if (event.getHitBlock() != null) {
            loc = event.getHitBlock().getLocation();
        } else {
            loc = event.getHitEntity().getLocation();

        }
        return loc;
    }
    private PotionMeta createPotion(ItemStack item, String potion){
        PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
        potionMeta.setBasePotionData(new PotionData(PotionType.getByEffect(PotionEffectType.getByName(potion))));
        int duration = 10;
        int amplifier = 2;
        boolean visible = true;
        potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.getByName(potion), duration, amplifier), visible);
        return(potionMeta);

    }
    private void arrow(CommandSender sender, String potion) {
        Player me = (Player) sender;
        PlayerInventory inventory = me.getInventory();
        ItemStack item = null;
        for (int i = 0; i < inventory.getSize(); i++) {
            item = inventory.getItem(i);
            if (item == null) {
                continue;
            }
            if (isArrow(item) == true) {

                item.setType(Material.TIPPED_ARROW);
                item.setAmount(item.getAmount());

                item.setItemMeta(createPotion(item, potion));
            }

        }

    }
}

