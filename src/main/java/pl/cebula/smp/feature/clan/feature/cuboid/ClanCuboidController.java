package pl.cebula.smp.feature.clan.feature.cuboid;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.configuration.implementation.ClanConfiguration;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.manager.ClanManager;
import pl.cebula.smp.feature.clan.service.ClanService;
import pl.cebula.smp.util.MessageUtil;

import java.util.List;

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
        if (!blockLocation.getWorld().equals(Bukkit.getWorlds().getFirst())) return;
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
        if (!blockLocation.getWorld().equals(Bukkit.getWorlds().getFirst())) return;
        Clan clan = this.clanService.findClanByLocation(blockLocation);
        if (clan == null) return;
        if (clan.getMemberArrayList().contains(player.getName())) return;
        if (clan.getOwnerName().equals(player.getName())) return;
        if (player.hasPermission("cebulasmp.clan.admin")) return;
        MessageUtil.sendActionbar(player, "&cNie możesz niszczyć bloków na terenie obcego klanu!");
        event.setCancelled(true);
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();
        Location blockLocation = event.getBlockClicked().getLocation().add(event.getBlockFace().getDirection());
        if (!blockLocation.getWorld().equals(Bukkit.getWorlds().getFirst())) return;

        Clan clan = this.clanService.findClanByLocation(blockLocation);
        if (clan == null) return;
        if (clan.getMemberArrayList().contains(player.getName())) return;
        if (clan.getOwnerName().equals(player.getName())) return;
        if (player.hasPermission("cebulasmp.clan.admin")) return;
        MessageUtil.sendActionbar(player, "&cNie możesz wylewać wody na terenie obcego klanu!");
        event.setCancelled(true);
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {
        Player player = event.getPlayer();
        Location blockLocation = event.getBlockClicked().getLocation();
        if (!blockLocation.getWorld().equals(Bukkit.getWorlds().getFirst())) return;

        Clan clan = this.clanService.findClanByLocation(blockLocation);
        if (clan == null) return;
        if (clan.getMemberArrayList().contains(player.getName())) return;
        if (clan.getOwnerName().equals(player.getName())) return;
        if (player.hasPermission("cebulasmp.clan.admin")) return;
        MessageUtil.sendActionbar(player, "&cNie możesz czerpać wody na terenie obcego klanu!");
        event.setCancelled(true);
    }

    @EventHandler
    public void onLiquidFlow(BlockFromToEvent event) {
        Block block = event.getBlock();
        Block toBlock = event.getToBlock();
        if (!toBlock.getWorld().equals(Bukkit.getWorlds().getFirst())) return;
        if (!block.getWorld().equals(Bukkit.getWorlds().getFirst())) return;

        Location toLocation = toBlock.getLocation();
        Clan fromClan = this.clanService.findClanByLocation(block.getLocation());
        Clan toClan = this.clanService.findClanByLocation(toLocation);

        if (fromClan != null && toClan != null && !fromClan.equals(toClan)) {
            event.setCancelled(true);
            return;
        }

        if (toClan != null && fromClan == null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        if (!this.clanConfiguration.isWar()) return;
        Location explosionLocation = event.getLocation();
        int radius = 2;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block affectedBlock = explosionLocation.getBlock().getRelative(x, y, z);
                    if (this.clanConfiguration.getBlockBreakList().contains(affectedBlock.getType())) {
                        if (Math.random() < 0.5) {
                            affectedBlock.breakNaturally();
                        }
                    } else {
                        affectedBlock.breakNaturally();
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent event) {
        handlePistonMovement(event.getBlocks(), event.getDirection(), event);
    }

    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent event) {
        handlePistonMovement(event.getBlocks(), event.getDirection(), event);
    }

    private void handlePistonMovement(List<Block> blocks, BlockFace direction, Cancellable event) {
        for (Block block : blocks) {
            Location newLocation = block.getRelative(direction).getLocation();
            Clan toClan = this.clanService.findClanByLocation(newLocation);

            if (toClan != null && ClanManager.isNearClanHeart(newLocation, toClan.getClanLocation())) {
                event.setCancelled(true);
                return;
            }
        }
    }

}
