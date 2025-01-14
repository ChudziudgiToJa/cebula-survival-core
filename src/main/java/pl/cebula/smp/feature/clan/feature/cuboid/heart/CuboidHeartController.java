package pl.cebula.smp.feature.clan.feature.cuboid.heart;

import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.service.ClanService;
import pl.cebula.smp.util.MessageUtil;

public class CuboidHeartController implements Listener {

    private final ClanService clanService;
    private final SurvivalPlugin survivalPlugin;

    public CuboidHeartController(ClanService clanService, SurvivalPlugin survivalPlugin) {
        this.clanService = clanService;
        this.survivalPlugin = survivalPlugin;
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

        if (event.getBlock().getType().equals(Material.BEE_NEST) && clan.equals(targetClan)) {
            event.setCancelled(true);
            MessageUtil.sendActionbar(player, "&cNie możesz zniszczyć serca własnego klanu.");
            return;
        }

        if (!event.getBlock().getType().equals(Material.BEE_NEST)) return;

        if (clan == null) {
            MessageUtil.sendActionbar(player, "&cNie możesz atakować wrogiego klanu bez własnego klanu.");
            event.setCancelled(true);
            return;
        }

        if (targetClan.getCuboidHearthValue() > 1) {
            targetClan.setCuboidHearthValue(targetClan.getCuboidHearthValue() - 1);
            event.setCancelled(true);

            blockLocation.getBlock().setType(Material.BEDROCK);
            Bukkit.getScheduler().runTaskLater(this.survivalPlugin, () -> {
                if (blockLocation.getBlock().getType() == Material.BEDROCK) {
                    blockLocation.getBlock().setType(Material.BEE_NEST);
                }
            }, 20);

            MessageUtil.sendTitle(player, "", "&fżycie klanu &c&l" + targetClan.getTag() + " &d" + targetClan.getCuboidHearthValue(), 10, 20, 10);
            return;
        }

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


    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Location blockLocation = event.getBlock().getLocation();
        Clan clan = this.clanService.findClanByLocation(blockLocation);

        if (clan == null) return;

        Location clanHeart = new Location(player.getWorld(), clan.getLocation().getX(), clan.getLocation().getY(), clan.getLocation().getZ());
        if (isNearClanHeart(blockLocation, clanHeart)) {
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
        if (isNearClanHeart(blockLocation, clanHeart)) {
            MessageUtil.sendActionbar(player, "&cNie możesz niszczyć bloków w pobliżu serca klanu!");
            event.setCancelled(true);
        }
    }

    private boolean isNearClanHeart(Location blockLocation, Location clanHeart) {
        if (!blockLocation.getWorld().equals(clanHeart.getWorld())) return false;
        int dx = Math.abs(blockLocation.getBlockX() - clanHeart.getBlockX());
        int dy = Math.abs(blockLocation.getBlockY() - clanHeart.getBlockY());
        int dz = Math.abs(blockLocation.getBlockZ() - clanHeart.getBlockZ());
        return dx <= 3 && dy <= 2 && dz <= 3;
    }
}

