package pl.cebula.smp.feature.shop.npcshop.object;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class Shop implements Serializable {
    private final String name;
    private final int npcId;
    private final List<ItemToInteract> itemToInteracts;

    public Shop(String name, Integer npcId, List<ItemToInteract> itemToInteracts) {
        this.name = name;
        this.npcId = npcId;
        this.itemToInteracts = itemToInteracts;
    }
}
