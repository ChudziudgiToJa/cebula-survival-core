package pl.cebula.smp.feature.end;

import com.sk89q.worldguard.bukkit.event.block.PlaceBlockEvent;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.*;
import pl.cebula.smp.configuration.implementation.WorldsSettings;
import pl.cebula.smp.util.MessageUtil;

public class EndController implements Listener {

    private final WorldsSettings worldsSettings;
    private final EndManager endManager;


    public EndController(WorldsSettings worldsSettings, EndManager endManager) {
        this.worldsSettings = worldsSettings;
        this.endManager = endManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (this.worldsSettings.endJoinStatus) {
            endManager.addBossBar(event.getPlayer());
        }
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (this.worldsSettings.endJoinStatus) {
            endManager.removeBossBar(event.getPlayer());
        }
    }

    @EventHandler
    public void onJoinPortalOnWorld(PlayerPortalEvent event) {
        Player player = event.getPlayer();

        if (!event.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)) return;
        event.setCancelled(true);

        if (!this.worldsSettings.endJoinStatus && !player.hasPermission("cebulasmp.end.admin")) {
            MessageUtil.sendTitle(player, "", "&cEnd aktualnie jest wyłączony", 20, 50, 20);
            return;
        }

        if (player.getWorld().getName().equals("world_the_end")) {
            if (this.worldsSettings.endSpawnLocation == null) {
                MessageUtil.sendMessage(player, "&cLokalizacja spawnu w endu nie jest ustawiona");
                return;
            }
            player.teleport(this.worldsSettings.endSpawnLocation);
        } else {
            player.teleport(Bukkit.getWorlds().getFirst().getSpawnLocation());
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getEntityType() == EntityType.ENDERMAN) {
            World world = event.getEntity().getWorld();
            if (world.getName().equalsIgnoreCase("world_the_end")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (!player.getWorld().getName().equals("world_the_end")) return;

        if (player.hasPermission("cebulasmp.end.break")) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (!player.getWorld().getName().equals("world_the_end")) return;

        if (player.hasPermission("cebulasmp.end.break")) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onBucketFill(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();

        if (!player.getWorld().getName().equals("world_the_end")) return;

        if (player.hasPermission("cebulasmp.end.break")) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onBreakBlock(PlayerBucketFillEvent event) {
        Player player = event.getPlayer();

        if (!player.getWorld().getName().equals("world_the_end")) return;

        if (player.hasPermission("cebulasmp.end.break")) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onSendCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage().split(" ")[0].substring(1).toLowerCase();
        if (player.hasPermission("cebulasmp.end.admin")) return;
        if (player.getWorld().getName().equals("world_the_end")) {
            if (this.worldsSettings.blockedCommandsOnEnd.contains(command)) {
                event.setCancelled(true);
                MessageUtil.sendTitle(player, "", "&ckomenda jest zablokowana w endzie.", 20, 50, 20);
            }
        }
    }
}
