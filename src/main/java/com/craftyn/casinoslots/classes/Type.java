package com.craftyn.casinoslots.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import com.craftyn.casinoslots.CasinoSlots;
import com.craftyn.casinoslots.actions.Action;
import com.craftyn.casinoslots.event.CasinoWinEvent;
import com.craftyn.casinoslots.slot.game.Game;

public class Type {
    private CasinoSlots plugin;
    private String name, itemCost = "0";
    private double cost = 0, createCost = 0;
    private ArrayList<ReelBlock> reel;
    private Map<String, String> messages;
    private List<String> helpMessages;
    private Map<BlockData, Reward> rewards;
    private BlockData controllerData;
    
    public Type(CasinoSlots plugin, String name) {
        this.plugin = plugin;
        this.name = name;
    }

    // Initialize new type
    public Type(CasinoSlots plugin, String name, double cost, String itemCost, double createCost, ArrayList<ReelBlock> reel, Map<String, String> messages, List<String> helpMessages, Map<BlockData, Reward> rewards, BlockData controllerData) {
        this.plugin = plugin;
        this.name = name;
        this.cost = cost;
        this.itemCost = itemCost;
        this.createCost = createCost;
        this.reel = reel;
        this.messages = messages;
        this.helpMessages = helpMessages;
        this.rewards = rewards;
        this.controllerData = controllerData;
    }

    /**
     * Gets the name of this type.
     * 
     * @return name of the Type
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the cost to play.
     * 
     * @return cost to play
     */
    public double getCost() {
        return this.cost;
    }
    
    /**
     * Sets the cost to play.
     * 
     * @param cost amount of money needed to play
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Gets the item that this type costs to use.
     * 
     * @return the item cost
     */
    public String getItemCost() {
        return this.itemCost;
    }
    
    /**
     * Sets the item that this slot costs to use.
     * 
     * @param itemCost the item to set as the cost
     */
    public void setItemCost(String itemCost) {
        this.itemCost = itemCost;
    }

    /**
     * Gets the amount it takes to create this type.
     * 
     * @return cost to create
     */
    public double getCreateCost() {
        return this.createCost;
    }
    
    /**
     * Sets the amount it takes to create this type.
     * 
     * @param createCost amount of money needed to create
     */
    public void setCreateCost(double createCost) {
        this.createCost = createCost;
    }
    
    /**
     * Gets the controller's block data
     * 
     * @return the controller's data
     */
    public BlockData getControllerData() {
        return this.controllerData;
    }
    
    /**
     * Sets the controller's block data.
     * 
     * @param data the data for the controller.
     */
    public void setControllerData(BlockData data) {
        this.controllerData = data;
    }

    /**
     * Gets the reel.
     * 
     * @return the list of {@link ReelBlock}'s which make up the reel.
     */
    public ArrayList<ReelBlock> getReel() {
        return this.reel;
    }
    
    /**
     * Sets the reel.
     * 
     * @param reel the {@link ReelBlock} we will be using
     */
    public void setReel(ArrayList<ReelBlock> reel) {
        this.reel = reel;
    }

    // Returns map of type messages
    public Map<String, String> getMessages() {
        return this.messages;
    }
    
    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
    }

    // Returns help messages
    public List<String> getHelpMessages() {
        return this.helpMessages;
    }
    
    public void setHelpMessages(List<String> messages) {
        this.helpMessages = messages;
    }
    
    public Map<BlockData, Reward> getRewards() {
        return this.rewards;
    }
    
    public void setRewards(Map<BlockData, Reward> rewards) {
        this.rewards = rewards;
    }

    // Returns type reward of id
    public Reward getReward(BlockData id) {
        return this.rewards.get(id);
    }
    
    public double getMaxPrize() {
        double max = 0.0;

        for(Reward r : rewards.values())
            if(r.getMoney() > max)
                max = r.getMoney();
                
        return max;
    }

    public double sendRewards(List<BlockData> results, Game game, Player player) {
        double won = 0;
        List<String> messagesSent = new ArrayList<String>();
        
        for(BlockData r : results) {
            if(this.rewards.containsKey(r)) {
                Reward re = this.rewards.get(r);
                
                //Make sure we don't send the same message more than once anymore!
                if(!re.getMessage().isEmpty() && !messagesSent.contains(re.getMessage())) {
                    plugin.sendMessage(player, re.getMessage());
                    messagesSent.add(re.getMessage());
                }
                
                //Add the amount won to the total amount, so we give it all at once
                won += re.getMoney();
                
                for(Action a : re.getActions()) {
                    a.execute(this, re, player);
                }
            }
        }
        
        new CasinoWinEvent(player, game.getSlot(), Collections.unmodifiableList(results), won).call();
        
        if (won < 0) {
            plugin.getEconomy().withdrawPlayer(player, Math.abs(won));
        } else {
            plugin.getEconomy().depositPlayer(player, won);
        }
        
        return won;
    }
}