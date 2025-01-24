package pl.cebula.smp.feature.bordercollection;

import net.citizensnpcs.api.event.NPCClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.cebula.smp.configuration.implementation.BorderCollectionConfiguration;

public class BorderCollectionController implements Listener {

    private final BorderCollectionConfiguration borderCollectionConfiguration;
    private final BorderCollectionInventory borderCollectionInventory;

    public BorderCollectionController(BorderCollectionConfiguration borderCollectionConfiguration, BorderCollectionInventory borderCollectionInventory) {
        this.borderCollectionConfiguration = borderCollectionConfiguration;
        this.borderCollectionInventory = borderCollectionInventory;
    }

    @EventHandler
    public void onClickNpc(NPCClickEvent event) {
        if (event.getNPC().getId() == this.borderCollectionConfiguration.npcId) {
            this.borderCollectionInventory.show(event.getClicker());
        }
    }
}
