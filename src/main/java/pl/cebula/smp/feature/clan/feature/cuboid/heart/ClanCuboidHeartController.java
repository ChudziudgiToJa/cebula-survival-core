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
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.configuration.implementation.ClanConfiguration;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.feature.cuboid.bossbar.ClanCuboidBossBarManager;
import pl.cebula.smp.feature.clan.manager.ClanManager;
import pl.cebula.smp.feature.clan.service.ClanService;
import pl.cebula.smp.util.MessageUtil;

import java.util.Iterator;

public class ClanCuboidHeartController implements Listener {

    private final ClanService clanService;
    private final SurvivalPlugin survivalPlugin;
    private final ClanConfiguration clanConfiguration;

    public ClanCuboidHeartController(ClanService clanService, SurvivalPlugin survivalPlugin, ClanConfiguration clanConfiguration) {
        this.clanService = clanService;
        this.survivalPlugin = survivalPlugin;
        this.clanConfiguration = clanConfiguration;
    }


    @EventHandler
    public void onDamageClan(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Location blockLocation = event.getBlock().getLocation();
        Clan targetClan = this.clanService.findClanByLocation(blockLocation);
        Clan clan = this.clanService.findClanByMember(player.getName());

        if (targetClan == null) {
            return;
        }

        if (event.getBlock().getType().equals(Material.BEE_NEST)) {
            if (clan == null) {
                MessageUtil.sendActionbar(player, "&cNie możesz atakować wrogiego klanu bez własnego klanu.");
                event.setCancelled(true);
                return;
            }

            if (clan.equals(targetClan)) {
                event.setCancelled(true);
                MessageUtil.sendActionbar(player, "&cNie możesz zniszczyć serca własnego klanu.");
                return;
            }

            if (!this.clanConfiguration.isWar()) {
                MessageUtil.sendActionbar(player, "&cNie możesz atakować wrogiego klanu gdy nie ma włączonych wojen!");
                event.setCancelled(true);
                return;
            }

            if (targetClan.getCuboidHearthValue() > 1) {
                targetClan.setCuboidHearthValue(targetClan.getCuboidHearthValue() - 1);
                event.setCancelled(true);

                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (targetClan.getMemberArrayList().contains(online.getName())) {
                        MessageUtil.sendMessage(online, "&c" + player.getName() + " atakuje serce twojego klanu pozostało &4" + clan.getCuboidHearthValue() + " życia.");
                    }
                }

                blockLocation.getBlock().setType(Material.BEDROCK);
                Bukkit.getScheduler().runTaskLater(this.survivalPlugin, () -> {
                    if (blockLocation.getBlock().getType() == Material.BEDROCK) {
                        blockLocation.getBlock().setType(Material.BEE_NEST);
                    }
                }, 10);

                clan.getMemberArrayList().forEach(string -> {
                    Player target = Bukkit.getPlayer(string);
                    if (target != null) {
                        MessageUtil.sendMessage(target, "&4⚠ &4&lUWAGA &cktoś atakuje twoje serce klanu!");
                    }
                });

                MessageUtil.sendTitle(player, "", "&fżycie klanu &c&l" + targetClan.getTag() + " &d" + targetClan.getCuboidHearthValue(), 10, 20, 10);
            } else {
                blockLocation.getWorld().getPlayers().stream()
                        .filter(nearbyPlayer -> {
                            double dx = nearbyPlayer.getLocation().getX() - blockLocation.getX();
                            double dz = nearbyPlayer.getLocation().getZ() - blockLocation.getZ();
                            return Math.sqrt(dx * dx + dz * dz) < 80;
                        })
                        .forEach(nearbyPlayer -> {
                                    BossBar bossbar = ClanCuboidBossBarManager.getBossBar(player.getUniqueId());
                                    bossbar.removePlayer(player);
                                    ClanCuboidBossBarManager.removeBossBar(player.getUniqueId());
                                }
                        );
                DHAPI.removeHologram(targetClan.getTag());
                this.clanService.removeClan(targetClan);
                Location clanHeart = new Location(player.getWorld(), targetClan.getLocation().getX(), targetClan.getLocation().getY(), targetClan.getLocation().getZ());
                player.getWorld().playSound(clanHeart, Sound.ITEM_GOAT_HORN_SOUND_0, 1, 1);

                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (targetClan.getMemberArrayList().contains(online.getName())) {
                        MessageUtil.sendTitle(online, "", "&ctwój klan został podbity", 20, 50, 20);
                    }
                    MessageUtil.sendMessage(online, "&4⚠ &fklan: &a&l" + clan.getTag() + " &fpodbił klan: &c&l" + targetClan.getTag());
                }
            }
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

        if (event.getBlock().getType().equals(Material.BEE_NEST)) return;
        if (clan == null) return;

        Location clanHeart = new Location(player.getWorld(), clan.getLocation().getX(), clan.getLocation().getY(), clan.getLocation().getZ());
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
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Material material = block.getType();
        boolean isRestrictedMaterial = material == Material.PISTON || material == Material.STICKY_PISTON || material == Material.OBSIDIAN || material == Material.CRYING_OBSIDIAN;

        if (!isRestrictedMaterial) {
            return;
        }

        Clan clan = this.clanService.findClanByLocation(block.getLocation());
        if (clan == null) {
            return;
        }
        if (this.clanService.isLocationOnClanCuboid(block.getLocation())) {
            event.setCancelled(true);
            MessageUtil.sendActionbar(event.getPlayer(), "Nie możesz stawiać tego bloku na terenie klanu!");
        }
    }
}

