package pl.cebula.smp.feature.randomteleport;

import com.eternalcode.core.EternalCoreApi;
import com.sk89q.worldguard.bukkit.event.block.BreakBlockEvent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.cebula.smp.configuration.implementation.PluginConfiguration;
import pl.cebula.smp.util.MessageUtil;

import java.util.concurrent.CompletableFuture;

public class RandomTeleportController implements Listener {

    private final PluginConfiguration pluginConfiguration;
    private final EternalCoreApi eternalCoreApi;

    public RandomTeleportController(PluginConfiguration pluginConfiguration, EternalCoreApi eternalCoreApi) {
        this.pluginConfiguration = pluginConfiguration;
        this.eternalCoreApi = eternalCoreApi;
    }


    @EventHandler
    public void onClickButton(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if (block == null || event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (this.pluginConfiguration.randomTeleportSettings.buttonsLocations.contains(block.getLocation())) {
            CompletableFuture<Location> randomLocationFuture = this.eternalCoreApi.getRandomTeleportService().getSafeRandomLocationInWorldBorder(player.getWorld(), 5);

            randomLocationFuture.thenAccept(randomLocation -> {
                if (randomLocation != null) {
                    player.teleport(randomLocation);
                    MessageUtil.sendTitle(player, "", "&aZostałeś przeteleportowany w losowe miejsce!", 20,50,20);
                } else {
                    MessageUtil.sendTitle(player, "", "&cNie udało się znaleźć bezpiecznej lokalizacji do teleportacji.", 20,50,20);
                }
            });
        }
    }

    @EventHandler
    public void onBreakButton(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (event.isCancelled()) {
            return;
        }

        if (this.pluginConfiguration.randomTeleportSettings.buttonsLocations.contains(block.getLocation())) {
            this.pluginConfiguration.randomTeleportSettings.buttonsLocations.remove(block.getLocation());
            this.pluginConfiguration.save();
            MessageUtil.sendMessage(player, "&aUsunięto guzik rtp");
        }
    }
}
