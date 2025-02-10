package pl.cebula.smp.feature.job;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

@Getter
@Setter
public class JobDropChance implements Serializable {
    private final ItemStack itemStack;
    private final double cahnce;

    public JobDropChance(ItemStack itemStack, double cahnce) {
        this.itemStack = itemStack;
        this.cahnce = cahnce;
    }
}
