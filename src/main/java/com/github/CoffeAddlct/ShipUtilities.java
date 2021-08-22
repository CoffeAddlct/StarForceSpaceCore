package com.github.CoffeAddlct;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class ShipUtilities {
	
	public ShipUtilities() {}
	
	public boolean sendPlayerActionBarInfo(Player target, String first, String second, String third) {
		if(target.isOnline()) {
			target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(first +"    " +second + "    " + third));
			return true;
		}else {
			return false;
		}
		
		
	}

}
