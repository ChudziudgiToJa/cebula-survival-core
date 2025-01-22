package pl.cebula.smp.feature.clan.feature.cuboid;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import pl.cebula.smp.configuration.implementation.ClanConfiguration;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.service.ClanService;
import pl.cebula.smp.util.MessageUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ClanCuboidController implements Listener {

    private final ClanService clanService;
    private final ClanConfiguration clanConfiguration;
    private final Random random;

    public ClanCuboidController(ClanService clanService, ClanConfiguration clanConfiguration, Random random) {
        this.clanService = clanService;
        this.clanConfiguration = clanConfiguration;
        this.random = random;
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
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();
        Location blockLocation = event.getBlockClicked().getLocation().add(event.getBlockFace().getDirection());
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

        Location toLocation = toBlock.getLocation();
        Clan fromClan = this.clanService.findClanByLocation(block.getLocation());
        Clan toClan = this.clanService.findClanByLocation(toLocation);

        if (fromClan != null && toClan != null && !fromClan.equals(toClan)) {
            event.setCancelled(true);
            return;
        }

        if (toClan != null && fromClan == null) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void antiHeartBlocker(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        List<Material> bannedBlocks = Arrays.asList(
                Material.OBSIDIAN,
                Material.CRYING_OBSIDIAN,
                Material.ENCHANTING_TABLE
        );
        if (block.getY() < 44) {
            Clan clan = this.clanService.findClanByLocation(block.getLocation());
            if (clan != null && bannedBlocks.contains(block.getType())) {
                event.setCancelled(true);
                MessageUtil.sendActionbar(player, "&cTen blok można stawiac tylko od 45 kratki w górę na terenie klanu");
            }
        }
    }
}

