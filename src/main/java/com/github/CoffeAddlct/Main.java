package com.github.CoffeAddlct;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import com.github.CoffeAddlct.EventActionListener;
import com.github.CoffeAddlct.SpaceShipManager;
import com.github.CoffeAddlct.BaseSpaceShip;

public class Main extends JavaPlugin {
	public static Main instance;
	public static SpaceShipManager manager;
	
	@Override
	public void onEnable() {
		instance=this;
	System.out.print("Enabling SpaceBattlesCore....updated!");
	Bukkit.getServer().broadcastMessage(ChatColor.GREEN +"SpaceBattlesCore enabled!");
	manager = new SpaceShipManager();
	EventActionListener da= new EventActionListener(manager);
	getServer().getPluginManager().registerEvents(da, this);
		
	}
	
	@Override
	public void onDisable() {
		System.out.print("Disabling SpaceBattles core...");
	}
	public boolean onCommand(CommandSender sender,Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p= (Player) sender;
			if(label.equalsIgnoreCase("Pilot")) {
				Player player = (Player) sender;
				//WorldServer worldServer= ((CraftWorld)player.getWorld()).getHandle();
				//BaseSpaceShip c= new BaseSpaceShip(player.getWorld(),player.getLocation().getX(),player.getLocation().getY(),player.getLocation().getZ(),1);
				//worldServer.addEntity(c);
				manager.addPilot(player);
				return true;
			}else{
				if(label.equalsIgnoreCase("Leave") && manager.checkIfPlayerIsPlaying(p)) {
					manager.removePilot(p);
					return true;
				}
				if(label.equalsIgnoreCase("admin"))
						manager.DebugSolution(p);
						return true;
			}		
		}else {
			return false;
		}
	}
	
}
