package com.github.CoffeAddlct;
import com.github.CoffeAddlct.Main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowball;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_15_R1.Vector3f;

public class FlightController {
	BaseSpaceShip controlledSpaceShip;
	Player pilot;
	Vector directionVector;
	Boolean isAlive;
	ShipUtilities utilities;
	
	

	public FlightController(BaseSpaceShip bss, Player pi) {
		this.controlledSpaceShip = bss;
		this.controlledSpaceShip.setInvulnerable(true);
		this.pilot = pi;
		isAlive=true;
		this.utilities=new ShipUtilities();
	}

//DEBUG--------------------------------------------------------------------
	public void sendtextmsg(String text) {
		pilot.sendMessage(text);
	}
//END OF DEBUG-------------------------------------------------------------

	public boolean isStill() {
		if (this.controlledSpaceShip.isNoGravity()) {
			return true;
		} else {
			return false;
		}
	}

	public void setStill() {
		this.controlledSpaceShip.setNoGravity(true);
	}

	public void setMoving() {
		this.controlledSpaceShip.setNoGravity(false);
	}
	
	public void setPilot(Player p) {
		if(this.controlledSpaceShip.getBukkitEntity().getPassengers().isEmpty()) {
			//this.controlledSpaceShip.getBukkitEntity().addPassenger(p);
			Slime slime= p.getWorld().spawn(p.getLocation(), Slime.class);
			slime.setAI(false);
			slime.setSilent(true);
			slime.setSize(7);
			slime.addPassenger(p);
			slime.setInvulnerable(true);
			slime.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 240000, 1));
			p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 240000, 1));
		    this.controlledSpaceShip.getBukkitEntity().addPassenger(slime);
		}
	}

	public void removePilot() {
		this.controlledSpaceShip.ejectPassengers();
	}

	public void setHealth(Float f) {
		if (f >= 0)
			this.controlledSpaceShip.setHealth(f);
	}
	
	public void teleportTo(Double x,Double y,Double z) {
		this.controlledSpaceShip.setLocation(x, y, z, 0, 0);
	}
	//TODO not implemented yet
	public void setFlyingSpeed(double speedvaluemultiplier) {
		this.controlledSpaceShip.flyingspeed = this.controlledSpaceShip.flyingspeed * speedvaluemultiplier;
	}
	
	
	//FIXME the destroy method can't be called while entity is ticking
	public void destroy() throws InterruptedException{
		//Cannot remove entity while ticking fix?
		 new BukkitRunnable() {
		        
	            @Override
	            public void run() {
	                controlledSpaceShip.killEntity();
	                
	            }
	            
	        }.runTaskLater(Main.instance, 1L);

		
	}
	
	public void tick(final FlightController fg) {
		 new BukkitRunnable() {
				@Override
				public void run() {
					if(fg.controlledSpaceShip.isAlive()){
					fg.printdebug();
					}else {
						this.cancel();
						fg.controlledSpaceShip.ejectPassengers();
						Main.manager.removePilot(fg.pilot);
						
					}
					}

			}.runTaskTimer(Main.instance, 0, 0L);
	}
	
	
	public boolean isAbletoFirePrimary() {
		return true;
	}

	public boolean isAbletoFireSecondary() {
		return true;
	}

	@SuppressWarnings("deprecation")
	public void shootPrimaryWeapons() {
	  //this.controlledSpaceShip.getBukkitEntity().getPassenger().getPassenger().sendMessage(ChatColor.GREEN +"FIRING PRIMARY WEAPONS...");

		Snowball primary= this.pilot.getWorld().spawn(pilot.getEyeLocation().add(2,-1,4),Snowball.class);
		Snowball secondary=  this.pilot.getWorld().spawn(pilot.getEyeLocation().add(2,-1,-4),Snowball.class);;
		primary.setBounce(false);
		secondary.setBounce(false);
		primary.setVelocity(pilot.getEyeLocation().getDirection().multiply(3.5));
		secondary.setVelocity(pilot.getEyeLocation().getDirection().multiply(3.5));
	};

	public void shootSecondaryWeapons() {
		 this.controlledSpaceShip.getBukkitEntity().getPassenger().getPassenger().sendMessage(ChatColor.RED+"FIRING SECONDARY WEAPONS...");
			};
	
	
	
	public void printdebug() {
		if (!this.controlledSpaceShip.getBukkitEntity().getPassengers().isEmpty()) {
			directionVector= this.pilot.getLocation().getDirection();
			//directionVector = this.getBukkitEntity().getPassenger().getLocation().getDirection();
			this.controlledSpaceShip.setMot(directionVector.getX(), directionVector.getY(), directionVector.getZ());
			this.controlledSpaceShip.velocityChanged = true;
			utilities.sendPlayerActionBarInfo(pilot, ChatColor.RED + "" +controlledSpaceShip.getHealth() +"/" + controlledSpaceShip.getHealth() +"❤HP", ChatColor.GREEN +"" + "null ❈Ammo", ChatColor.AQUA +"" +"null/null ✎Shields");
			//changing the yae and the pitch only if not appy with actual results
			//this.lastYaw = this.yaw = pilot.getLocation().getYaw();
			//this.pitch = pilot.getLocation().getPitch() * 0.5F;
			// create only 1 vector and change it's values later on
			this.controlledSpaceShip.setHeadPose(new Vector3f(pilot.getLocation().getPitch(), pilot.getLocation().getYaw(), 0F));
			// Set the entity's pitch, yaw, head rotation etc.
		}
	}
	
}
	
