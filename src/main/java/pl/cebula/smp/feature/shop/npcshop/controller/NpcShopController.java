package pl.cebula.smp.feature.shop.npcshop.controller;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.cebula.smp.configuration.implementation.NpcShopConfiguration;
import pl.cebula.smp.feature.shop.npcshop.inventory.NpcShopInventory;
import pl.cebula.smp.feature.shop.npcshop.object.Shop;

public class NpcShopController implements Listener {

    private final NpcShopConfiguration npcShopConfiguration;
    private final NpcShopInventory npcShopInventory;

    public NpcShopController(NpcShopConfiguration npcShopConfiguration, NpcShopInventory npcShopInventory) {
        this.npcShopConfiguration = npcShopConfiguration;
        this.npcShopInventory = npcShopInventory;
    }

    @EventHandler
    public void click(NPCRightClickEvent event) {
        Player player = event.getClicker();

        for (Shop shop : this.npcShopConfiguration.shops) {
            if (event.getNPC().getId() == shop.getNpcId()) {
                npcShopInventory.show(player, shop);
                player.playSound(player, Sound.BLOCK_BARREL_OPEN, 5 ,5);
                event.setCancelled(true);
            }
        }
    }
}
