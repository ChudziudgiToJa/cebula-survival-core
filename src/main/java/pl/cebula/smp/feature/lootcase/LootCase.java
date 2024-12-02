package pl.cebula.smp.feature.lootcase;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.List;

@Getter
public class LootCase implements Serializable {
    private final String name;
    private final Location location;
    private final ItemStack keyItemStack;
    private final List<LootCaseChance> dropItems;

    public LootCase(String name, Location location, ItemStack keyItemStack, List<LootCaseChance> dropItems) {
        this.name = name;
        this.location = location;
        this.keyItemStack = keyItemStack;
        this.dropItems = dropItems;
    }
}
