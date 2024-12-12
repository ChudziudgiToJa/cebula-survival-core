package pl.cebula.smp.feature.lootcase;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.cebula.smp.configuration.implementation.LootCaseConfiguration;
import pl.cebula.smp.configuration.implementation.PluginConfiguration;

public class LootCaseController implements Listener {

    private final LootCaseConfiguration pluginConfiguration;
    private final LootCaseInventory lootCaseInventory;

    public LootCaseController(LootCaseConfiguration pluginConfiguration, LootCaseInventory lootCaseInventory) {
        this.pluginConfiguration = pluginConfiguration;
        this.lootCaseInventory = lootCaseInventory;
    }


    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (block == null) return;

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            for (LootCase lootCase : this.pluginConfiguration.lootCases) {
                if (lootCase.getLocation().equals(block.getLocation())) {
                    this.lootCaseInventory.showPrewiew(player, lootCase);
                    event.setCancelled(true);
                }
            }
        }
    }
}
