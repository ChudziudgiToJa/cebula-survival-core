package pl.cebula.smp.feature.itemshop;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

@Getter
public class ItemShop implements Serializable {

    private final ItemStack itemStack;
    private final double price;
    private final String command;

    public ItemShop(ItemStack itemStack, double price, String command) {
        this.itemStack = itemStack;
        this.price = price;
        this.command = command;
    }
}
