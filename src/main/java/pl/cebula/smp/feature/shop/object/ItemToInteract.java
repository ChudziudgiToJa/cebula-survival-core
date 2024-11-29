package pl.cebula.smp.feature.shop.object;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

@Getter
public class ItemToInteract implements Serializable {
    private final ItemStack itemStack;
    private final int itemSlot;
    private final double buyPrice;
    private final double sellPrice;

    public ItemToInteract(ItemStack itemStack, int itemSlot, double buyPrice, double sellPrice) {
        this.itemStack = itemStack;
        this.itemSlot = itemSlot;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }
}
