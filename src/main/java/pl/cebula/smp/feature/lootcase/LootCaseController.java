package pl.cebula.smp.feature.lootcase;

import org.bukkit.Location;
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
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Block block = event.getClickedBlock();
        if (block == null) return;

        Player player = event.getPlayer();
        Location clickedLocation = block.getLocation();

        LootCase matchingLootCase = this.pluginConfiguration.lootCases.stream()
                .filter(lootCase -> lootCase.getLocation().equals(clickedLocation))
                .findFirst()
                .orElse(null);

        if (matchingLootCase != null) {
            this.lootCaseInventory.showPrewiew(player, matchingLootCase);
            event.setCancelled(true);
        }
    }
}
