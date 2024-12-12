package pl.cebula.smp.feature.shop.controller;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.cebula.smp.configuration.implementation.NpcShopConfiguration;
import pl.cebula.smp.configuration.implementation.PluginConfiguration;
import pl.cebula.smp.feature.shop.inventory.ShopInventory;
import pl.cebula.smp.feature.shop.object.Shop;

public class ShopNpcController implements Listener {

    private final NpcShopConfiguration npcShopConfiguration;
    private final ShopInventory shopInventory;

    public ShopNpcController(NpcShopConfiguration npcShopConfiguration, ShopInventory shopInventory) {
        this.npcShopConfiguration = npcShopConfiguration;
        this.shopInventory = shopInventory;
    }

    @EventHandler
    public void click(NPCRightClickEvent event) {
        Player player = event.getClicker();

        for (Shop shop : this.npcShopConfiguration.shops) {
            if (event.getNPC().getId() == shop.getNpcId()) {
                shopInventory.show(player, shop);
                player.playSound(player, Sound.BLOCK_BARREL_OPEN, 5 ,5);
            }
        }
    }
}
