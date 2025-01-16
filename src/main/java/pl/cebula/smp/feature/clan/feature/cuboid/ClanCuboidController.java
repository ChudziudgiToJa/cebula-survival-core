package pl.cebula.smp.feature.clan.feature.cuboid;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import pl.cebula.smp.configuration.implementation.ClanConfiguration;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.service.ClanService;
import pl.cebula.smp.util.MessageUtil;

import java.util.Iterator;

public class ClanCuboidController implements Listener {

    private final ClanService clanService;
    private final ClanConfiguration clanConfiguration;

    public ClanCuboidController(ClanService clanService, ClanConfiguration clanConfiguration) {
        this.clanService = clanService;
        this.clanConfiguration = clanConfiguration;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Location blockLocation = event.getBlock().getLocation();
        Clan clan = this.clanService.findClanByLocation(blockLocation);
        if (clan == null) return;
        if (clan.getMemberArrayList().contains(player.getName())) return;
        if (clan.getOwnerName().equals(player.getName())) return;
        if (player.hasPermission("cebulasmp.clan.admin")) return;
        MessageUtil.sendActionbar(player, "&cNie możesz stawiać bloków na terenie obcego klanu!");
        event.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Location blockLocation = event.getBlock().getLocation();
        Clan clan = this.clanService.findClanByLocation(blockLocation);
        if (clan == null) return;
        if (clan.getMemberArrayList().contains(player.getName())) return;
        if (clan.getOwnerName().equals(player.getName())) return;
        if (player.hasPermission("cebulasmp.clan.admin")) return;
        MessageUtil.sendActionbar(player, "&cNie możesz niszczyć bloków na terenie obcego klanu!");
        event.setCancelled(true);
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent event) {
        Clan clan = this.clanService.findClanByLocation(event.getLocation());
        if (clan == null) return;
        if (this.clanConfiguration.isWar()) {
            event.setCancelled(true);
        }
    }
}
