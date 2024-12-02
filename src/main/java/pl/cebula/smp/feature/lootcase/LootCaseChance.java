package pl.cebula.smp.feature.lootcase;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

@Getter
public class LootCaseChance implements Serializable {
    private final ItemStack itemStack;
    private final double chance;

    public LootCaseChance(ItemStack itemStack, double chance) {
        this.itemStack = itemStack;
        this.chance = chance;
    }
}
