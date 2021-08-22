package com.github.CoffeAddlct;

import java.util.HashSet;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_15_R1.util.CraftChatMessage;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_15_R1.DamageSource;
import net.minecraft.server.v1_15_R1.EntityArmorStand;
import net.minecraft.server.v1_15_R1.EntitySlime;
import net.minecraft.server.v1_15_R1.EnumItemSlot;
import net.minecraft.server.v1_15_R1.IChatBaseComponent;
import net.minecraft.server.v1_15_R1.Vector3f;

//TODO implement a FlightController for WASD?
//TODO implement fuel?
//TODO implement Damage and 
//TODO make abstract
//TODO add method for detecting a crash and destroying the ship with a custom damage event
public class BaseSpaceShip extends EntityArmorStand {
	int teamid;
	Vector3f yawPitchVector;
	Vector directionVector;
	int variationID;
	double flyingspeed;
	Player Pilot;
	HashSet<Entity> nearbyEntities;
	HashSet<Block> nearbyBlocks;

	// TODO implement a standard speed multiplier
	// TODO implement a boost speed multiplier
	// TODO implement starships variations

	public BaseSpaceShip(World w, double x, double y, double z, int teamid) {
		super(((CraftWorld) w).getHandle(), x, y, z);
		variationID= new Random().nextInt(2);
		this.teamid = teamid;
		this.setBasePlate(false);
		this.setInvisible(true);
		this.setInvulnerable(true);
		ItemStack sw;
		switch (variationID){
			case 0:
				sw= new ItemStack(Material.WOODEN_SWORD, 1);
				this.setSlot(EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(sw));
				break;
			case 1:
				sw=new ItemStack(Material.WOODEN_PICKAXE, 1);
				this.setSlot(EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(sw));
				break;
		}




		nearbyEntities= new HashSet<>();
		nearbyBlocks= new HashSet<>();
						 
	}

	public void setVelocity(Vector v) {
		this.setMot(v.getX(), v.getY(), v.getZ());
		this.velocityChanged = true;
	}
/*
	@Override
	public void tick() {
		//super.tick();
		if (!this.getBukkitEntity().getPassengers().isEmpty()) {
			directionVector= this.Pilot.getLocation().getDirection();
			//directionVector = this.getBukkitEntity().getPassenger().getLocation().getDirection();
			this.setMot(directionVector.getX(), directionVector.getY(), directionVector.getZ());
			this.velocityChanged = true;
			//utils.sendPlayerActionBarInfo(Pilot, ChatColor.RED + "" +getHealth() +"/" + getHealth() +"❤️", ChatColor.GREEN +"" + "null❈ Defense", ChatColor.AQUA +"" +"null/null✎ Mana");
			//changing the yae and the pitch only if not appy with actual results
			//this.lastYaw = this.yaw = pilot.getLocation().getYaw();
			//this.pitch = pilot.getLocation().getPitch() * 0.5F;
			// create only 1 vector and change it's values later on
			this.setHeadPose(new Vector3f(Pilot.getLocation().getPitch(), Pilot.getLocation().getYaw(), 0F));
			// Set the entity's pitch, yaw, head rotation etc.
		}
	}
	
	*/
	@Override
	public boolean damageEntity(DamageSource damageSource, float f) {
		return true;

	}
	




}
