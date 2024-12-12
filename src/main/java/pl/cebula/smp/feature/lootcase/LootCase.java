package pl.cebula.smp.feature.lootcase;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Particle;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class LootCase implements Serializable {
    private final String name;
    private final String string;
    private final Location location;
    private String keyItemStack;
    private final List<LootCaseChance> dropItems;

    public LootCase(String name, String string, Location location, String keyItemStack, List<LootCaseChance> dropItems) {
        this.name = name;
        this.string = string;
        this.location = location;
        this.keyItemStack = keyItemStack;
        this.dropItems = dropItems;
    }
}
