package pl.cebula.smp.feature.clan.feature.cuboid.bossbar;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.Clan;
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
        this.runTaskTimerAsynchronously(survivalPlugin, 20 * 5, 0);
    }

    @Override
    public void run() {
        this.clanService.getAllClans().forEach(clan -> {
            Location clanCenter = new Location(
                    Bukkit.getWorlds().getFirst(), // Zakładam, że używasz pierwszego świata
                    clan.getLocation().getX(),
                    clan.getLocation().getY(),
                    clan.getLocation().getZ()
            );
            List<Player> players = new ArrayList<>(clanCenter.getWorld().getPlayers());

            players.stream()
                    .filter(player -> player.getLocation().distance(clanCenter) <= 80) // Sprawdza graczy w promieniu 80 bloków
                    .forEach(player -> {
                        if (clanService.isLocationOnClanCuboid(player.getLocation())) {
                            Bukkit.getScheduler().runTask(this.survivalPlugin, () -> {
                                if (clan.getOwnerName().equals(player.getName())
                                        || clan.getMemberArrayList().contains(player.getName())) {
                                    handleBossBarForMember(player, clan);
                                } else {
                                    handleBossBarForEnemy(player, clan);
                                }
                            });
                        } else {
                            removeBossBar(player);
                        }
                    });
        });
    }

    private void handleBossBarForMember(Player player, Clan clan) {
        Location clanCenter = clan.getClanLocation();
        double distance = player.getLocation().distance(clanCenter);
        double progress = Math.max(0, 1 - (distance / 20.0));

        String bossBarMessage = String.format(
                "§aᴊᴇsᴛᴇś ɴᴀ ᴛᴇʀᴇɴɪᴇ sᴡᴏᴊᴇɢᴏ ᴋʟᴀɴᴜ §8| §a§l%s §7(%.1f ᴍ ᴏᴅ sᴇʀᴄᴀ ᴋʟᴀɴᴜ)",
                clan.getTag(), distance
        );

        BossBar bossBar = ClanCuboidBossBarManager.getBossBar(player.getUniqueId());
        if (bossBar == null) {
            bossBar = Bukkit.createBossBar(bossBarMessage, BarColor.GREEN, BarStyle.SEGMENTED_10);
            bossBar.addPlayer(player);
            ClanCuboidBossBarManager.addBossBar(player.getUniqueId(), bossBar);
        }
        bossBar.setTitle(bossBarMessage);
        bossBar.setProgress(progress);
    }

    private void handleBossBarForEnemy(Player player, Clan clan) {
        String bossBarMessage = String.format(
                "§cᴊᴇsᴛᴇś ɴᴀ ᴛᴇʀᴇɴɪᴇ ᴡʀᴏɢɪᴇɢᴏ ᴋʟᴀɴᴜ §8| §4§l%s",
                clan.getTag()
        );

        BossBar bossBar = ClanCuboidBossBarManager.getBossBar(player.getUniqueId());
        if (bossBar == null) {
            bossBar = Bukkit.createBossBar(bossBarMessage, BarColor.RED, BarStyle.SEGMENTED_10);
            bossBar.addPlayer(player);
            ClanCuboidBossBarManager.addBossBar(player.getUniqueId(), bossBar);
        }
        bossBar.setTitle(bossBarMessage);
        bossBar.setProgress(1);
    }

    private void removeBossBar(Player player) {
        BossBar bossBar = ClanCuboidBossBarManager.getBossBar(player.getUniqueId());
        if (bossBar != null) {
            bossBar.removePlayer(player);
            ClanCuboidBossBarManager.removeBossBar(player.getUniqueId());
        }
    }
}