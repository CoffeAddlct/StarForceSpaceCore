package com.github.CoffeAddlct;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.List;
import com.github.CoffeAddlct.FlightController;
import net.minecraft.server.v1_15_R1.EntityArmorStand;
import net.minecraft.server.v1_15_R1.EnumItemSlot;
import net.minecraft.server.v1_15_R1.WorldServer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


//TODO Maybe upgrade to UUID to speed up HashMap management?
public class SpaceShipManager {
	HashMap<Player, FlightController> activePlayersMap;
	int maxPlayersAllowed = 15;
	WorldServer worldServer;
	List<World> worldList;
	HashMap<Double, UUID> timeplayedHashMap;
	
//Manager class used to handle active maps
	public SpaceShipManager() {
		activePlayersMap = new HashMap<>();
		//Maps
		worldList= Bukkit.getServer().getWorlds();
		this.setActiveWorld(0);
	}

//TODO is deprecated
	public boolean addFlightController(FlightController fg) {
		if (activePlayersMap.size() > maxPlayersAllowed || activePlayersMap.containsValue(fg)) {
			return false;
		} else {
			activePlayersMap.put(fg.pilot, fg);
			return true;
		}
	}
//TODO is deprecated
	public boolean removeFlightController(Player p) {
		if (activePlayersMap.isEmpty() == false && activePlayersMap.containsKey(p)) {
			activePlayersMap.remove(p);
			return true;
		} else {
			return false;
		}

	}

	public FlightController findController(Player p) {
		if (activePlayersMap.containsKey(p)) {
			FlightController fgController = activePlayersMap.get(p);
			return fgController;
		} else {
			return null;
		}

	}

//DEBUG
	public String printList() {
		return activePlayersMap.toString();
	}
//TODO add code for teleporting the player to the new map if it is in a different world/location
	public boolean addPilot(Player p) {
		if (activePlayersMap.size() > maxPlayersAllowed || activePlayersMap.containsKey(p)) {
			return false;
		} else {
			BaseSpaceShip bss = new BaseSpaceShip(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(),
					p.getLocation().getZ(), 1);
			worldServer.addEntity(bss);
			FlightController controller= new FlightController(bss, p);
			controller.setPilot(p);

			this.activePlayersMap.put(p, controller);
			controller.tick(controller);
			
			return true;
		}
	}
//Killing the entity doesn't work, need help
	public boolean removePilot(Player p) {
		if (!activePlayersMap.isEmpty() && activePlayersMap.containsKey(p)) {
			p.sendMessage("Killing the ship...");
			FlightController fgController=findController(p);
			fgController.removePilot();
			activePlayersMap.remove(p);
			try {
				fgController.destroy();
			} catch (InterruptedException c) {
				//if unable delete, teleport the entity to the void
				//Not a fix, doesn't work;
				c.printStackTrace();
				
			}
			
			return true;
		} else {
			return false;
		}

	}

	public boolean checkIfPlayerIsPlaying(Player p) {
		if (activePlayersMap.containsKey(p)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setActiveWorld(int index) {
		if(worldList.isEmpty()==false)
			worldServer= ((CraftWorld)worldList.get(index)).getHandle();
	}
	
	public void DebugSolution(Player p) {
		EntityArmorStand d= new EntityArmorStand(((CraftWorld)p.getWorld()).getHandle(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
		 ((CraftWorld)p.getWorld()).getHandle().addEntity(d, SpawnReason.CUSTOM);
		d.setBasePlate(false);
		d.setInvisible(false);
		ItemStack sw = new ItemStack(Material.WOODEN_SWORD, 1);
		d.setSlot(EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(sw));
		Slime slime= p.getWorld().spawn(p.getLocation(),Slime.class);
		slime.addPassenger(p);
		slime.setAI(false);
		slime.setSize(6);
		slime.setInvulnerable(true);
		slime.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 240000, 1));
		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 240000, 1));
		d.getBukkitEntity().setPassenger(slime);
		
		
		
		
	}

	

}
