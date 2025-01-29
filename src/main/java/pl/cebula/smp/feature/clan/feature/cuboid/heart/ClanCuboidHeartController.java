package pl.cebula.smp.feature.clan.feature.cuboid.heart;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.configuration.implementation.ClanConfiguration;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.manager.ClanManager;
import pl.cebula.smp.feature.clan.service.ClanService;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.MessageUtil;

import java.util.Iterator;

public class ClanCuboidHeartController implements Listener {

    private final ClanService clanService;
    private final SurvivalPlugin survivalPlugin;
    private final ClanConfiguration clanConfiguration;
    private final ClanCuboidHeartInventory clanCuboidHeartInventory;
    private final UserService userService;

    public ClanCuboidHeartController(ClanService clanService, SurvivalPlugin survivalPlugin, ClanConfiguration clanConfiguration, ClanCuboidHeartInventory clanCuboidHeartInventory, UserService userService) {
        this.clanService = clanService;
        this.survivalPlugin = survivalPlugin;
        this.clanConfiguration = clanConfiguration;
        this.clanCuboidHeartInventory = clanCuboidHeartInventory;
        this.userService = userService;
    }


    @EventHandler
    public void onClanHeartAttack(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Location blockLocation = event.getBlock().getLocation();

        Clan targetClan = clanService.findClanByLocation(blockLocation);
        Clan playerClan = clanService.findClanByMember(player.getName());

        if (!blockLocation.getWorld().equals(Bukkit.getWorlds().getFirst())) return;

        if (targetClan == null) {
            return;
        }

        if (!targetClan.getClanLocation().equals(blockLocation)) {
            return;
        }

        if (!clanConfiguration.isWar()) {
            MessageUtil.sendActionbar(player, "&cNie możesz atakować wrogiego klanu, gdy nie ma włączonych wojen!");
            event.setCancelled(true);
            return;
        }

        if (playerClan == null) {
            MessageUtil.sendActionbar(player, "&cNie możesz atakować wrogiego klanu bez własnego klanu.");
            event.setCancelled(true);
            return;
        }


        if (targetClan.equals(playerClan)) {
            MessageUtil.sendActionbar(player, "&cNie możesz zniszczyć serca własnego klanu.");
            event.setCancelled(true);
            return;
        }

        if (targetClan.getCuboidHearthValue() > 1) {
            ClanCuboidHeartManager.handleClanHeartDamage(player, targetClan.getClanLocation(), targetClan, this.survivalPlugin);
            event.setCancelled(true);
        } else {
            event.setCancelled(true);
            ClanCuboidHeartManager.handleClanHeartDestruction(player, targetClan.getClanLocation(), targetClan, this.clanService);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Location blockLocation = event.getBlock().getLocation();
        Clan clan = this.clanService.findClanByLocation(blockLocation);

        if (!blockLocation.getWorld().equals(Bukkit.getWorlds().getFirst())) return;
        if (clan == null) return;

        if (ClanManager.isNearClanHeart(blockLocation, clan.getClanLocation())) {
            MessageUtil.sendActionbar(player, "&cNie możesz stawiać bloków w pobliżu serca klanu!");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Location blockLocation = event.getBlock().getLocation();
        Clan clan = this.clanService.findClanByLocation(blockLocation);

        if (!blockLocation.getWorld().equals(Bukkit.getWorlds().getFirst())) return;
        if (clan == null) return;

        Location clanHeart = new Location(player.getWorld(), clan.getLocation().getX(), clan.getLocation().getY(), clan.getLocation().getZ());
        if (blockLocation.equals(clanHeart)) return;

        if (ClanManager.isNearClanHeart(blockLocation, clanHeart)) {
            MessageUtil.sendActionbar(player, "&cNie możesz niszczyć bloków w pobliżu serca klanu!");
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onExplosion(EntityExplodeEvent event) {
        Clan clan = this.clanService.findClanByLocation(event.getLocation());
        if (clan == null) return;

        Iterator<Block> iterator = event.blockList().iterator();
        while (iterator.hasNext()) {
            Location blockLocation = iterator.next().getLocation();
            if (ClanManager.isNearClanHeart(blockLocation, new Location(Bukkit.getWorlds().getFirst(), clan.getLocation().getX(), clan.getLocation().getY(), clan.getLocation().getZ()))) {
                iterator.remove();
            }
        }
    }


    @EventHandler
    public void onClickHeart(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        Block block = event.getClickedBlock();

        if (action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        User user = userService.findUserByUUID(player.getUniqueId());
        if (user == null) {
            return;
        }
        if (block == null) {
            return;
        }

        if (!block.getWorld().equals(Bukkit.getWorlds().getFirst())) return;

        Clan clan = clanService.findClanByLocation(player.getLocation());
        if (clan == null) {
            return;
        }

        Location location = new Location(Bukkit.getWorlds().getFirst(), clan.getLocation().getX(), clan.getLocation().getY(), clan.getLocation().getZ());
        if (!location.equals(block.getLocation())) return;

        if (clan.getCuboidHearthValue() <= 200) {
            MessageUtil.sendActionbar(player, "&cSerce posiada max życia.");
            return;
        }
        if (clanConfiguration.isWar()) {
            MessageUtil.sendActionbar(player, "&cNaprawa serca klanu jest wyłączona podczas wojny.");
            return;
        }
        clanCuboidHeartInventory.showHealClanInventory(player, clan, user);
    }


    @EventHandler
    public void onLiquidFlow(BlockFromToEvent event) {
        Block block = event.getBlock();
        Block toBlock = event.getToBlock();
        if (!toBlock.getWorld().equals(Bukkit.getWorlds().getFirst())) return;
        if (!block.getWorld().equals(Bukkit.getWorlds().getFirst())) return;


        Location toLocation = toBlock.getLocation();
        Clan clan = this.clanService.findClanByLocation(block.getLocation());

        if (clan == null) {
            return;
        }

        if (ClanManager.isNearClanHeart(toLocation, clan.getClanLocation())) {
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();
        if (!location.getWorld().equals(Bukkit.getWorlds().getFirst())) return;


        Clan clan = this.clanService.findClanByLocation(location);
        if (clan == null) return;
        if (ClanManager.isNearClanHeart(location, clan.getClanLocation())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {
        Location location = event.getBlock().getLocation();
        if (!location.getWorld().equals(Bukkit.getWorlds().getFirst())) return;

        Clan clan = this.clanService.findClanByLocation(location);
        if (clan == null) return;
        if (ClanManager.isNearClanHeart(location, clan.getClanLocation())) {
            event.setCancelled(true);
        }
    }
}

