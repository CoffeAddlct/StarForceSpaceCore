package com.github.CoffeAddlct;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

public class EventActionListener implements Listener {
	SpaceShipManager manager;

	public EventActionListener(SpaceShipManager sp) {
		this.manager = sp;
	}

	@EventHandler
	void onActionFired(PlayerInteractEvent e) {
		if(e.getPlayer().isInsideVehicle() && manager.checkIfPlayerIsPlaying(e.getPlayer())) {
			FlightController flightController = manager.findController(e.getPlayer());
			if (e.getAction().equals(Action.LEFT_CLICK_AIR)&& flightController.isAbletoFirePrimary()){
				flightController.shootPrimaryWeapons();
			}else {
				if(e.getAction().equals(Action.RIGHT_CLICK_AIR) && flightController.isAbletoFireSecondary() ){
					flightController.shootSecondaryWeapons();
				}
			
		}
	  }
	}


//Code for handling if the player tries to leave the spaceship, check if player is alive and the game is running.
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onVehicleExit(EntityDismountEvent event) {
		if (event.getEntity() instanceof Player && event.getDismounted().getType()==EntityType.ARMOR_STAND && manager.checkIfPlayerIsPlaying((Player)event.getEntity())){
			Player player = (Player) event.getEntity();
			player.sendTitle(ChatColor.RED+"Exiting the craft is disabled while playing", "use /leave");
			event.setCancelled(true);
			
		}
	}
//TODO add funny death messages
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent ed) {
		if (ed.getEntity().isInsideVehicle() && ed.getEntity().getVehicle().getType()==EntityType.ARMOR_STAND) {
			this.manager.removePilot(ed.getEntity());
		}
			
	}
	//FIXEM when player quits, the checkifplayerIsplaying returns always false since the player object is now null?
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent pqe) {
		if(manager.checkIfPlayerIsPlaying(pqe.getPlayer()))
			manager.removePilot(pqe.getPlayer());
		
	}
	
	public void onBlockPlaceEvent(BlockPlaceEvent bp) {
		if(manager.checkIfPlayerIsPlaying(bp.getPlayer())) {
			bp.getPlayer().sendMessage(ChatColor.RED +"You cannot place blocks while playing!");
			bp.setCancelled(true);
		}
			
	}
	
	public void onEntityClicked(PlayerInteractEntityEvent pqt) {
		if(pqt.getRightClicked().getType()==EntityType.ARMOR_STAND && manager.checkIfPlayerIsPlaying(pqt.getPlayer())) {
			FlightController flightController = manager.findController(pqt.getPlayer());
			flightController.shootSecondaryWeapons();
		}
	}
	public void onLaserHitEvent(ProjectileHitEvent projectileHitEvent){
		if(!(projectileHitEvent.getEntity() instanceof Snowball))
			return;
		if(!(projectileHitEvent.getHitEntity() instanceof Slime))
			return;
		manager.printList();
	}

}
