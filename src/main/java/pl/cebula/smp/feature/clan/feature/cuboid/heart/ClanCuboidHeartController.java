package pl.cebula.smp.feature.clan.feature.cuboid.heart;

import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.configuration.implementation.ClanConfiguration;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.feature.cuboid.bossbar.ClanCuboidBossBarManager;
import pl.cebula.smp.feature.clan.manager.ClanManager;
import pl.cebula.smp.feature.clan.service.ClanService;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.MessageUtil;

import java.util.Iterator;
import java.util.Objects;

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

        if (targetClan == null) {
            return;
        }

        if (targetClan.equals(playerClan)) {
            return;
        }

        if (playerClan == null) {
            MessageUtil.sendActionbar(player, "&cNie możesz atakować wrogiego klanu bez własnego klanu.");
            event.setCancelled(true);
            return;
        }

        final Location location = new Location(Bukkit.getWorlds().getFirst(), targetClan.getLocation().getX(), targetClan.getLocation().getY(), targetClan.getLocation().getZ());

        if (blockLocation.equals(location)) {
            MessageUtil.sendActionbar(player, "&cNie możesz zniszczyć serca własnego klanu.");
            event.setCancelled(true);
            return;
        }

        if (!clanConfiguration.isWar()) {
            MessageUtil.sendActionbar(player, "&cNie możesz atakować wrogiego klanu gdy nie ma włączonych wojen!");
            event.setCancelled(true);
            return;
        }

        if (targetClan.getCuboidHearthValue() > 1) {
            ClanCuboidHeartManager.handleClanHeartDamage(player, blockLocation, targetClan, this.survivalPlugin);
        } else {
            ClanCuboidHeartManager.handleClanHeartDestruction(player, blockLocation, targetClan, this.clanService);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Location blockLocation = event.getBlock().getLocation();
        Clan clan = this.clanService.findClanByLocation(blockLocation);

        if (clan == null) return;

        Location clanHeart = new Location(player.getWorld(), clan.getLocation().getX(), clan.getLocation().getY(), clan.getLocation().getZ());
        if (ClanManager.isNearClanHeart(blockLocation, clanHeart)) {
            MessageUtil.sendActionbar(player, "&cNie możesz stawiać bloków w pobliżu serca klanu!");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Location blockLocation = event.getBlock().getLocation();
        Clan clan = this.clanService.findClanByLocation(blockLocation);


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

}

