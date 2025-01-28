package pl.cebula.smp.feature.nether;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;
import pl.cebula.smp.configuration.implementation.NetherConfiguration;
import pl.cebula.smp.util.MessageUtil;

public class NetherController implements Listener {

    private final NetherConfiguration netherConfiguration;
    private final NetherManager netherManager;


    public NetherController(NetherConfiguration netherConfiguration, NetherManager netherManager) {
        this.netherConfiguration = netherConfiguration;
        this.netherManager = netherManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (this.netherConfiguration.NetherJoinStatus) {
            netherManager.addBossBar(event.getPlayer());
        }
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (this.netherConfiguration.NetherJoinStatus) {
            netherManager.removeBossBar(event.getPlayer());
        }
    }

    @EventHandler
    public void onJoinPortalOnWorld(PlayerPortalEvent event) {
        Player player = event.getPlayer();

        if (!event.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)) return;
        event.setCancelled(true);

        if (!this.netherConfiguration.NetherJoinStatus && !player.hasPermission("cebulasmp.nether.admin")) {
            MessageUtil.sendTitle(player, "", "&cNether aktualnie jest wyłączony", 20, 50, 20);
            return;
        }

        if (player.getWorld().equals(Bukkit.getWorlds().getFirst())) {
            if (this.netherConfiguration.netherSpawnLocation == null) {
                MessageUtil.sendMessage(player, "&cLokalizacja spawnu w nether nie jest ustawiona");
                return;
            }
            player.teleport(this.netherConfiguration.netherSpawnLocation);
        } else {
            player.teleport(Bukkit.getWorlds().getFirst().getSpawnLocation());
        }
    }

    @EventHandler
    public void breakSpawnerBlock(BlockBreakEvent event) {
        if (event.getBlock().getWorld().equals(Bukkit.getWorlds().getFirst())) return;
        if (event.getBlock().getType().equals(Material.SPAWNER)) {
            MessageUtil.sendMessage(event.getPlayer(), "&cNiszczenie spawnerów jest zakazane.");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlaceWitcherHead(BlockPlaceEvent event) {
        if (event.getBlock().getWorld().equals(Bukkit.getWorlds().getFirst())) return;
        if (event.getBlock().getType().equals(Material.WITHER_SKELETON_SKULL)) {
            event.setCancelled(true);
            MessageUtil.sendMessage(event.getPlayer(), "&cStawianie tego przedmiotu jest tylko możliwe w netherze.");
        }
    }

    @EventHandler
    public void onSendCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage().split(" ")[0].substring(1).toLowerCase();
        if (player.hasPermission("cebulasmp.nether.admin")) return;
        if (player.getWorld().equals(Bukkit.getWorlds().get(1))) {
            if (this.netherConfiguration.blockedCommandsOnNether.contains(command)) {
                event.setCancelled(true);
                MessageUtil.sendTitle(player, "", "&ckomenda jest zablokowana w netherze.", 20, 50, 20);
            }
        }
    }
}
