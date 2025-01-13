package pl.cebula.smp.feature.clan.feature.cuboid.bossbar;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.service.ClanService;
import pl.cebula.smp.util.MessageUtil;

public class CuboidBossBarTak extends BukkitRunnable {

    private final ClanService clanService;
    private final SurvivalPlugin survivalPlugin;

    public CuboidBossBarTak(ClanService clanService, SurvivalPlugin survivalPlugin) {
        this.clanService = clanService;
        this.survivalPlugin = survivalPlugin;
        this.runTaskTimerAsynchronously(survivalPlugin, 20, 0);
    }


    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            Location playerLocation = player.getLocation();
            if (clanService.isBlockOnClanTerritory(playerLocation)) {
                Clan clan = clanService.findClanByLocation(playerLocation);

                if (clan != null) {
                    Bukkit.getScheduler().runTask(this.survivalPlugin, () -> {
                        Location clanHeartLocation = new Location(
                                player.getWorld(),
                                clan.getLocation().getX(),
                                playerLocation.getY(),
                                clan.getLocation().getZ()
                        );
                        if (clan.getOwnerName().equals(player.getName()) || clan.getMemberArrayList().contains(player.getName())) {
                            double distance = playerLocation.distance(clanHeartLocation);
                            double progress = Math.max(0, 1 - (distance / 20.0));
                            String bossBarMessage = String.format(
                                    "§aJesteś na terenie swojego klanu §8| §a§l%s §7(%.1f m od serca klanu)",
                                    clan.getTag(), distance
                            );
                            BossBar bossBar = CuboidBossBarManager.getBossBar(player.getUniqueId());
                            if (bossBar == null) {
                                bossBar = Bukkit.createBossBar(
                                        MessageUtil.smallTextToColor(bossBarMessage),
                                        BarColor.GREEN,
                                        BarStyle.SEGMENTED_10
                                );
                                bossBar.addPlayer(player);
                                CuboidBossBarManager.addBossBar(player.getUniqueId(), bossBar);
                            }

                            bossBar.setTitle(bossBarMessage);
                            bossBar.setProgress(progress);
                        } else {
                            String bossBarMessage = String.format(
                                    "§cJesteś na terenie wrogiego klanu §8| §4§l%s",
                                    clan.getTag()
                            );
                            BossBar bossBar = CuboidBossBarManager.getBossBar(player.getUniqueId());
                            if (bossBar == null) {
                                bossBar = Bukkit.createBossBar(
                                        bossBarMessage,
                                        BarColor.GREEN,
                                        BarStyle.SEGMENTED_10
                                );
                                bossBar.addPlayer(player);
                                CuboidBossBarManager.addBossBar(player.getUniqueId(), bossBar);
                            }
                            bossBar.setTitle(bossBarMessage);
                            bossBar.setProgress(1);
                        }
                    });
                }
            } else {
                BossBar bossBar = CuboidBossBarManager.getBossBar(player.getUniqueId());
                Bukkit.getScheduler().runTask(this.survivalPlugin, () -> {
                    if (bossBar != null) {
                        bossBar.removePlayer(player);
                        CuboidBossBarManager.removeBossBar(player.getUniqueId());
                    }
                });
            }
        });
    }
}
