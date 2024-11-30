package pl.cebula.smp.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;

import static pl.cebula.smp.util.MessageUtil.colored;

public final class ItemStackBuilder {

    private final ItemStack item;

    public ItemStackBuilder(Material material) {
        this.item = new ItemStack(material);
    }

    public ItemStackBuilder(Material material, int amount) {
        this.item = new ItemStack(material, amount);
    }

    public ItemStackBuilder(ItemStack itemStack) {
        this.item = itemStack;
    }

    public ItemStackBuilder(ItemStack itemStack, int amount) {
        itemStack.setAmount(amount);
        this.item = itemStack;
    }

    public ItemStackBuilder setName(String name) {
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(MessageUtil.smallTextToColor(name));
        this.item.setItemMeta(meta);
        return this;
    }


    public ItemStackBuilder setAmount(int amount) {
        this.item.setAmount(amount);
        return this;
    }


    public ItemStackBuilder setOwner(String owner) {
        SkullMeta meta = (SkullMeta) this.item.getItemMeta();
        meta.setDisplayName(colored(owner));
        item.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder addLore(String lore) {
        ItemMeta meta = this.item.getItemMeta();
        List<String> list = new ArrayList<>();
        if (meta.hasLore()) {
            list = new ArrayList<>(meta.getLore());
        }
        list.add(lore);
        meta.setLore(MessageUtil.colored(list));
        item.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder addLore(List<String> loreList) {
        ItemMeta meta = this.item.getItemMeta();
        List<String> list = new ArrayList<>();

        if (meta.hasLore()) {
            list = new ArrayList<>(meta.getLore());
        }

        list.addAll(loreList);
        meta.setLore(colored(list));
        item.setItemMeta(meta);

        return this;
    }

    public ItemStackBuilder setLore(List<String> lore) {
        ItemMeta meta = this.item.getItemMeta();
        meta.setLore(colored(lore));
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder setCustomData(int data) {
        ItemMeta meta = this.item.getItemMeta();
        meta.setCustomModelData(data);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder addEnchant(Enchantment enchant, int level) {
        ItemMeta meta = this.item.getItemMeta();
        meta.addEnchant(enchant, level, true);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder setUnbreakabe(boolean status) {
        ItemMeta meta = this.item.getItemMeta();
        meta.setUnbreakable(status);
        this.item.setItemMeta(meta);
        return this;
    }

    public void addEnchantBook(Enchantment enchant, int level) {
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
        if (meta == null) return;
        meta.addStoredEnchant(enchant, level, true);
        item.setItemMeta(meta);
    }

    public ItemStack toItemStack() {
        return this.item;
    }


    public ItemStackBuilder visibleFlag() {
        ItemMeta meta = toItemStack().getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        toItemStack().setItemMeta(meta);
        return this;
    }

    public void setItemMeta(SkullMeta headMeta) {
        ItemStack itemStack = (ItemStack) headMeta;
        itemStack.setItemMeta(headMeta);
    }


    public ItemStackBuilder addEnchant(Map<Enchantment, Integer> enchantments) {
        enchantments.forEach(this::addEnchant);
        return this;
    }

    public ItemStackBuilder addLore(String s, String s1) {
        return null;
    }
}
