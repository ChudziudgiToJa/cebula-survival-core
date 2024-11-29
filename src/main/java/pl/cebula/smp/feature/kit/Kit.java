package pl.cebula.smp.feature.kit;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.List;

@Getter
public class Kit implements Serializable {
    private final String name;
    private final long coolDownTime;
    private final ItemStack icon;
    private final List<ItemStack> itemStackArrayList;

    public Kit(String name, long coolDownTime, ItemStack icon, List<ItemStack> itemStackArrayList) {
        this.name = name;
        this.coolDownTime = coolDownTime * 1000L;
        this.icon = icon;
        this.itemStackArrayList = itemStackArrayList;
    }
}
