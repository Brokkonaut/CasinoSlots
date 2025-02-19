package test.com.craftyn.casinoslots.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MockPlayerInventory implements PlayerInventory {

    private int armorSize = 4, inventorySize = 36, heldSlot = 0;
    ItemStack[] armorContents = new ItemStack[armorSize];
    ItemStack[] inventoryContents = new ItemStack[inventorySize];

    @Override
    public ItemStack[] getArmorContents() {
        return armorContents;
    }

    @Override
    public ItemStack getHelmet() {
        return armorContents[0];
    }

    @Override
    public ItemStack getChestplate() {
        return armorContents[1];
    }

    @Override
    public ItemStack getLeggings() {
        return armorContents[2];
    }

    @Override
    public ItemStack getBoots() {
        return armorContents[3];
    }

    @Override
    public void setArmorContents(ItemStack[] itemStacks) {
        this.armorContents = itemStacks;
    }

    @Override
    public void setHelmet(ItemStack itemStack) {
        this.armorContents[0] = itemStack;
    }

    @Override
    public void setChestplate(ItemStack itemStack) {
        this.armorContents[1] = itemStack;
    }

    @Override
    public void setLeggings(ItemStack itemStack) {
        this.armorContents[2] = itemStack;
    }

    @Override
    public void setBoots(ItemStack itemStack) {
        this.armorContents[3] = itemStack;
    }

    @Override
    public ItemStack getItemInHand() {
        return this.inventoryContents[this.heldSlot];
    }

    @Override
    public void setItemInHand(ItemStack itemStack) {
        this.inventoryContents[this.heldSlot] = itemStack;
    }

    @Override
    public int getHeldItemSlot() {
        return this.heldSlot;
    }

    @Override
    public HumanEntity getHolder() {
        return null;
    }

    @Override
    public int getSize() {
        return inventoryContents.length + armorContents.length;
    }

    @Override
    public ItemStack getItem(int i) {
        if (i >= 0 && i < inventorySize) {
            return inventoryContents[i];
        } else if (i >= inventorySize && i < inventorySize + armorSize) {
            return armorContents[i - inventorySize];
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {
        if (i >= 0 && i < inventorySize) {
            inventoryContents[i] = itemStack;
        } else if (i >= inventorySize && i < inventorySize + armorSize) {
            armorContents[i - inventorySize] = itemStack;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    @Override
    public HashMap<Integer, ItemStack> addItem(ItemStack... itemStacks) {
        return new HashMap<Integer, ItemStack>();
    }

    @Override
    public HashMap<Integer, ItemStack> removeItem(ItemStack... itemStacks) {
        return new HashMap<Integer, ItemStack>();
    }

    @Override
    public ItemStack[] getContents() {
        return this.inventoryContents;
    }

    @Override
    public void setContents(ItemStack[] itemStacks) {
        this.inventoryContents = itemStacks;
    }

    @Override
    public ItemStack[] getStorageContents() {
        return this.inventoryContents;
    }

    @Override
    public void setStorageContents(ItemStack[] itemStacks) throws IllegalArgumentException {
        this.inventoryContents = itemStacks;
    }

    @Override
    public boolean contains(Material material) {
        boolean contains = false;

        for (ItemStack item : inventoryContents) {
            if (item.getType() == material) {
                return true;
            }
        }

        return contains;
    }

    @Override
    public boolean contains(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean contains(Material material, int i) {
        return false;
    }

    @Override
    public boolean contains(ItemStack itemStack, int i) {
        return false;
    }

    @Override
    public HashMap<Integer, ? extends ItemStack> all(Material material) {
        return null;
    }

    @Override
    public HashMap<Integer, ? extends ItemStack> all(ItemStack itemStack) {
        return null;
    }

    @Override
    public int first(Material material) {
        return 0;
    }

    @Override
    public int first(ItemStack itemStack) {
        return 0;
    }

    @Override
    public int firstEmpty() {
        return 0;
    }

    @Override
    public void remove(Material material) {

    }

    @Override
    public void remove(ItemStack itemStack) {

    }

    @Override
    public void clear(int i) {
        inventoryContents[i] = null;
    }

    @Override
    public void clear() {

    }

    @Override
    public List<HumanEntity> getViewers() {
        return null;
    }

    @Override
    public InventoryType getType() {
        return null;
    }

    @Override
    public ListIterator<ItemStack> iterator() {
        return null;
    }

    @Override
    public int getMaxStackSize() {
        return 0;
    }

    @Override
    public void setMaxStackSize(int i) {

    }

    @Override
    public ListIterator<ItemStack> iterator(int i) {
        return null;
    }

    @Override
    public boolean containsAtLeast(final ItemStack itemStack, final int i) {
        return false;
    }

    private static Map<String, Object> makeMap(ItemStack[] items) {
        Map<String, Object> contents = new LinkedHashMap<String, Object>(items.length);
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].getType() != Material.AIR) {
                contents.put(Integer.valueOf(i).toString(), items[i]);
            }
        }
        return contents;
    }

    public String toString() {
        return "{\"inventoryContents\":" + makeMap(getContents()) + ",\"armorContents\":" + makeMap(getArmorContents()) + "}";
    }

    @Override
    public void setHeldItemSlot(int slot) {
        this.heldSlot = slot;
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public ItemStack[] getExtraContents() {
        return null;
    }

    @Override
    public ItemStack getItemInMainHand() {
        return null;
    }

    @Override
    public ItemStack getItemInOffHand() {
        return null;
    }

    @Override
    public void setExtraContents(ItemStack[] arg0) {
    }

    @Override
    public void setItemInMainHand(ItemStack paramItemStack) {
    }

    @Override
    public void setItemInOffHand(ItemStack paramItemStack) {
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void setItem(EquipmentSlot slot, ItemStack item) {
    }

    @Override
    public ItemStack getItem(EquipmentSlot slot) {
        return null;
    }

    @Override
    public @NotNull HashMap<Integer, ItemStack> removeItemAnySlot(@NotNull ItemStack... items) throws IllegalArgumentException {
        return null;
    }

    @Override
    public int close() {
        return 0;
    }

    @Override
    public @Nullable InventoryHolder getHolder(boolean useSnapshot) {
        return null;
    }
}