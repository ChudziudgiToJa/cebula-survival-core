package pl.cebula.smp.feature.shop.controller;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.cebula.smp.configuration.implementation.PluginConfiguration;
import pl.cebula.smp.feature.shop.inventory.ShopInventory;
import pl.cebula.smp.feature.shop.object.Shop;

public class ShopNpcController implements Listener {

    private final PluginConfiguration pluginConfiguration;
    private final ShopInventory shopInventory;

    public ShopNpcController(PluginConfiguration pluginConfiguration, ShopInventory shopInventory) {
        this.pluginConfiguration = pluginConfiguration;
        this.shopInventory = shopInventory;
    }


    @EventHandler
    public void click(NPCRightClickEvent event) {
        Player player = event.getClicker();

        for (Shop shop : this.pluginConfiguration.npcShop.shops) {
            if (event.getNPC().getId() == shop.getNpcId()) {
                shopInventory.show(player, shop);
                player.playSound(player, Sound.BLOCK_BARREL_OPEN, 5 ,5);
            }
        }
    }
}
