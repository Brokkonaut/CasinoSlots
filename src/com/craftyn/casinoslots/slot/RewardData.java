package com.craftyn.casinoslots.slot;

import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.craftyn.casinoslots.CasinoSlots;

public class RewardData {
	
	private CasinoSlots plugin;
	private static final Random random = new Random();
	
	public RewardData(CasinoSlots plugin) {
		this.plugin = plugin;
	}
	
	// Sends reward to player
	public void send(Player player, Reward reward, Type type) {
		
		if(reward.message != null) {
			plugin.sendMessage(player, reward.message);
		}
		
		if(reward.money != null) {
			if(reward.money < 0)
				plugin.economy.withdrawPlayer(player.getName(), Math.abs(reward.money));
			else
				plugin.economy.depositPlayer(player.getName(), reward.money);
		}
		
		if(reward.action != null && !reward.action.isEmpty()) {
			executeAction(reward.action, player, type, reward);
		}
	}
		
	// Parses reward actions
	private void executeAction(List<String> actionList, Player p, Type type, Reward reward) {
		if(plugin.configData.inDebug()) plugin.debug("The size of the actionList is: " + actionList.size());		
		for(String action : actionList) {
			String[] a = action.split(" ");
			
			// Give action
			if(a[0].equalsIgnoreCase("give")) {				
				String[] itemData = a[1].split("\\,");
				int amount = Integer.parseInt(a[2]);
				
				int item = Integer.parseInt(itemData[0]);
				short damage = 0;
				ItemStack is = null;
				
				if(itemData.length == 1 || itemData.length == 2) {
					is = new ItemStack(item, amount, damage);
				}else if (itemData.length == 3) {
					is = new ItemStack(item, amount, damage);
					
					int enID = Integer.parseInt(itemData[1]);
					Enchantment enchantment = Enchantment.getById(enID);
					
					//check if the enchantment is valid
					if (enchantment == null) {
						plugin.severe("There is an invalid enchantment ID for the type " + type.getName());
						continue;
					}
					
					int enLevel = Integer.parseInt(itemData[2]);
					if (enLevel > 127) enLevel = 127;
					if (enLevel < 1) enLevel = enchantment.getMaxLevel();
					
					try {
						is.addUnsafeEnchantment(enchantment, enLevel);
					} catch (Exception e) {
						plugin.severe("Enchanting one of your rewards for " + type.getName() + " wasn't successful.");
					}
				}
				
				p.getInventory().addItem(is);
			}
			
			// Kill action
			else if(a[0].equalsIgnoreCase("kill")) {
				p.setHealth(0);
			}
			
			// Kick action
			else if(a[0].equalsIgnoreCase("kick")) {
				p.kickPlayer("You cheated the Casino!");
			}
			
			// Addxp action
			else if(a[0].equalsIgnoreCase("addxp")) {
				
				int exp = Integer.parseInt(a[1]);
				p.giveExp(exp);
			}
			
			// AddXPlvl action
			else if(a[0].equalsIgnoreCase("addxplvl")) {
				
				int exp = Integer.parseInt(a[1]);
				int oldLvl = p.getLevel();
				int newLvl = oldLvl+exp;
				p.setLevel(newLvl);
			}
			
			// Tpto action
			else if(a[0].equalsIgnoreCase("tpto")) {
				
				String[] xyz = a[1].split("\\,");
				World world = p.getWorld();
				Location loc = new Location(world, Integer.parseInt(xyz[0]), Integer.parseInt(xyz[1]), Integer.parseInt(xyz[2]));
				p.teleport(loc);
			}
			
			// Smite action
			else if(a[0].equalsIgnoreCase("smite")) {
				if(a.length == 2) {
					int times = Integer.parseInt(a[1]);
					for(int i = 1; i < times; i++) {
						p.getWorld().strikeLightning(p.getLocation());
					}
				}else
					p.getWorld().strikeLightning(p.getLocation());
			}
			
			// Fire action
			else if(a[0].equalsIgnoreCase("fire")) {
				if(a.length == 2) {
					int ticks = Integer.parseInt(a[1]);
					p.setFireTicks(ticks);
				}else {
					p.setFireTicks(120);
				}
			}
			
			// goBlind action
			else if(a[0].equalsIgnoreCase("goblind")) {
				if(a.length == 2) {
					int ticks = Integer.parseInt(a[1]);
					p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, ticks, 90));
				}else {
					p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 90));
				}
			}
			
			// gocrazy action
			else if (a[0].equalsIgnoreCase("gocrazy")) {
				if(a.length == 2) {
					int ticks = Integer.parseInt(a[1]);
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, ticks, 1000));
				}else {
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 160, 1000));
				}
			}
			
			// highjump action
			else if (a[0].equalsIgnoreCase("highjump")) {
				if(a.length == 2) {
					int ticks = Integer.parseInt(a[1]);
					p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, ticks, 2));
				}else {
					p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 120, 2));
				}
			}
			
			// digfast action
			else if (a[0].equalsIgnoreCase("digfast")) {
				if(a.length == 2) {
					int ticks = Integer.parseInt(a[1]);
					p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, ticks, 2));
				}else {
					p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 400, 2));
				}
			}
			
			// hulkup action
			else if (a[0].equalsIgnoreCase("hulkup")) {
				if(a.length == 2) {
					int ticks = Integer.parseInt(a[1]);
					p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, ticks, 15));
				}else {
					p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 400, 15));
				}
			}
			
			// DrugUp action
			else if (a[0].equalsIgnoreCase("drugup")) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 900, 200));
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 400, 10));
			}
			
			else if (a[0].equalsIgnoreCase("slap")) {
				// special thanks to CommandBook for this code, loved it enough to add it. Source:
				// https://github.com/sk89q/commandbook/blob/master/src/main/java/com/sk89q/commandbook/FunComponent.java#L204
				p.setVelocity(new Vector(random.nextDouble() * 2.0 - 1, random.nextDouble() * 1, random.nextDouble() * 2.0 - 1));
			}
			
			else if (a[0].equalsIgnoreCase("rocket")) {
				// Special thanks to CommandBook for this code, loved it enough to add it. Source:
				// https://github.com/sk89q/commandbook/blob/master/src/main/java/com/sk89q/commandbook/FunComponent.java#L282
				p.setVelocity(new Vector(0, 30, 0));
			}
			
			//command
			else if (a[0].equalsIgnoreCase("command")) {
				//Check to make sure that the action "command" is greater than 1
				if (a.length < 2) {
					plugin.error("The command action for " + type.getName() + " needs something other than 'command' for it to run.");
					continue;
				}
				
				//Generate the command
				String command = action.substring(8);
				command = command.replaceAll("[cost]", type.getCost().toString());
				command = command.replaceAll("[moneywon]", reward.money.toString());
				command = command.replaceAll("[player]", p.getDisplayName());
				command = command.replaceAll("[type]", type.getName());
				
				//Check to make sure the command isn't actually null
				if (!command.isEmpty()) {
					//Set the sender of the command as the console
					plugin.server.dispatchCommand(plugin.server.getConsoleSender(), command);
					continue;
				}else {
					// if it is, then return an error in the console and don't do anything.
					plugin.error("Couldn't find a command to do for " + type.getName() + ", please check your config.yml file.");
					continue;
				}
			}
			
			// Broadcast action
			else if(a[0].equalsIgnoreCase("broadcast")) {
				//Check to make sure that there is actually something to broadcast
				if (a.length < 2) {
					plugin.error("The broadcast action needs something other than 'broadcast' for it to run.");
					continue;
				}
				
				//Set the message to broadcast to everything after "broadcast ", which is 10.
				String msg = action.substring(10);
				msg = msg.replaceAll("[cost]", type.getCost().toString());
				msg = msg.replaceAll("[moneywon]", reward.money.toString());
				msg = msg.replaceAll("[player]", p.getDisplayName());
				msg = msg.replaceAll("[type]", type.getName());
				msg = ChatColor.translateAlternateColorCodes('&', msg);
				
				//Broadcast the message
				plugin.server.broadcastMessage(msg);
			}
		}
	}
}