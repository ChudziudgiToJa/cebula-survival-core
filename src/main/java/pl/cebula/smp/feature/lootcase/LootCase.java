package pl.cebula.smp.feature.lootcase;

import com.comphenix.protocol.wrappers.EnumWrappers;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.List;

@Getter
public class LootCase implements Serializable {
    private final String name;
    private final String string;
    private final Location location;
    private final ItemStack keyItemStack;
    private final Particle particle;
    private final List<LootCaseChance> dropItems;

    public LootCase(String name, String string, Location location, ItemStack keyItemStack, Particle particle, List<LootCaseChance> dropItems) {
        this.name = name;
        this.string = string;
        this.location = location;
        this.keyItemStack = keyItemStack;
        this.particle = particle;
        this.dropItems = dropItems;
    }
}
