package pl.cebula.smp.feature.clan.feature.cuboid.bossbar;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.service.ClanService;
import pl.cebula.smp.util.MessageUtil;

import java.util.ArrayList;
import java.util.List;

public class ClanCuboidBossBarTak extends BukkitRunnable {

    private final ClanService clanService;
    private final SurvivalPlugin survivalPlugin;

    public ClanCuboidBossBarTak(ClanService clanService, SurvivalPlugin survivalPlugin) {
        this.clanService = clanService;
        this.survivalPlugin = survivalPlugin;
        this.runTaskTimerAsynchronously(survivalPlugin, 20, 0);
    }


    @Override
    public void run() {
        this.clanService.getAllClans().forEach(clan -> {
            Location location = new Location(Bukkit.getWorlds().getFirst(), clan.getLocation().getX(), clan.getLocation().getY(), clan.getLocation().getZ());
            List<Player> players = new ArrayList<>(location.getWorld().getPlayers());

            players.stream()
                    .filter(nearbyPlayer -> {
                        double dx = nearbyPlayer.getLocation().getX() - location.getX();
                        double dz = nearbyPlayer.getLocation().getZ() - location.getZ();
                        return Math.sqrt(dx * dx + dz * dz) < 80;
                    })
                    .forEach(player -> {
                        if (clanService.isLocationOnClanCuboid(player.getLocation())) {
                            Bukkit.getScheduler().runTask(this.survivalPlugin, () -> {
                                if (clan.getOwnerName().equals(player.getName()) || clan.getMemberArrayList().contains(player.getName())) {
                                    Location locationToBossBar = new Location(Bukkit.getWorlds().getFirst(), clan.getLocation().getX(), player.getLocation().getY(), clan.getLocation().getZ());
                                    double dx = player.getLocation().getX() - locationToBossBar.getX();
                                    double dz = player.getLocation().getZ() - locationToBossBar.getZ();
                                    double distance = Math.sqrt(dx * dx + dz * dz);
                                    double progress = Math.max(0, 1 - (distance / 20.0));
                                    String bossBarMessage = String.format(
                                            "§aᴊᴇsᴛᴇś ɴᴀ ᴛᴇʀᴇɴɪᴇ sᴡᴏᴊᴇɢᴏ ᴋʟᴀɴᴜ §8| §a§l%s §7(%.1f ᴍ ᴏᴅ sᴇʀᴄᴀ ᴋʟᴀɴᴜ)",
                                            clan.getTag(), distance
                                    );
                                    BossBar bossBar = ClanCuboidBossBarManager.getBossBar(player.getUniqueId());
                                    if (bossBar == null) {
                                        bossBar = Bukkit.createBossBar(
                                                MessageUtil.smallTextToColor(bossBarMessage),
                                                BarColor.GREEN,
                                                BarStyle.SEGMENTED_10
                                        );
                                        bossBar.addPlayer(player);
                                        ClanCuboidBossBarManager.addBossBar(player.getUniqueId(), bossBar);
                                    }
                                    bossBar.setTitle(bossBarMessage);
                                    bossBar.setProgress(progress);
                                } else {
                                    String bossBarMessage = String.format(
                                            "§cᴊᴇsᴛᴇś ɴᴀ ᴛᴇʀᴇɴɪᴇ ᴡʀᴏɢɪᴇɢᴏ ᴋʟᴀɴᴜ §8| §4§l%s",
                                            clan.getTag()
                                    );
                                    BossBar bossBar = ClanCuboidBossBarManager.getBossBar(player.getUniqueId());
                                    if (bossBar == null) {
                                        bossBar = Bukkit.createBossBar(
                                                bossBarMessage,
                                                BarColor.RED,
                                                BarStyle.SEGMENTED_10
                                        );
                                        bossBar.addPlayer(player);
                                        ClanCuboidBossBarManager.addBossBar(player.getUniqueId(), bossBar);
                                    }
                                    bossBar.setTitle(bossBarMessage);
                                    bossBar.setProgress(1);
                                }
                            });
                        } else {
                            BossBar bossBar = ClanCuboidBossBarManager.getBossBar(player.getUniqueId());
                            Bukkit.getScheduler().runTask(this.survivalPlugin, () -> {
                                if (bossBar != null) {
                                    bossBar.removePlayer(player);
                                    ClanCuboidBossBarManager.removeBossBar(player.getUniqueId());
                                }
                            });
                        }
                    });
        });
    }

}
