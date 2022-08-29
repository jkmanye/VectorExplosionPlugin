package com.schoolvote.MCPlugin.VectorExplosionPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.Random;

public class Main extends JavaPlugin implements Listener {

    public boolean isEnabled = false;

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "===========================");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "VectorExplosionPlugin Disabled!");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "===========================");
    }

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "===========================");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "VectorExplosionPlugin Enabled!");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "===========================");
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("vex")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("en")) {
                    isEnabled = true;
                    sender.sendMessage(ChatColor.GREEN + "Enabled!");
                } else if (args[0].equalsIgnoreCase("dis")) {
                    isEnabled = false;
                    sender.sendMessage(ChatColor.RED + "Disabled!");
                } else sender.sendMessage(ChatColor.RED + "Invalid Syntax! Use /vex <en/dis>.");
            } else sender.sendMessage(ChatColor.RED + "Invalid Syntax! Use /vex <en/dis>.");
        }
        return true;
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (isEnabled) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "Explosion!");

            Random random = new Random();

            Vector v = new Vector();
            v.setY(2);
            v.setZ(random.nextDouble());

            for (Block block : event.blockList()) {
                if (block.getType().equals(Material.AIR)) return;

                BlockState saved = block.getState();
                block.setType(Material.AIR);

                FallingBlock fallingBlock = Bukkit.getWorld("world").spawnFallingBlock(saved.getLocation(), saved.getType(), saved.getData().getData());
                fallingBlock.setVelocity(v);
            }
        }
    }
}
