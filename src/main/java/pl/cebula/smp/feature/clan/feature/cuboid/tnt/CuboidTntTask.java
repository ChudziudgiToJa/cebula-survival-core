package pl.cebula.smp.feature.clan.feature.cuboid.tnt;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.configuration.implementation.CuboidConfiguration;
import pl.cebula.smp.util.MessageUtil;

import java.time.LocalTime;

public class CuboidTntTask extends BukkitRunnable {

    private final SurvivalPlugin survivalPlugin;
    private final CuboidConfiguration cuboidConfiguration;

    public CuboidTntTask(SurvivalPlugin survivalPlugin, CuboidConfiguration cuboidConfiguration) {
        this.survivalPlugin = survivalPlugin;
        this.cuboidConfiguration = cuboidConfiguration;
    }


    @Override
    public void run() {
        LocalTime now = LocalTime.now();
        LocalTime startTime = LocalTime.parse(this.cuboidConfiguration.warStartTime);
        LocalTime endTime = LocalTime.parse(this.cuboidConfiguration.warEndTime);

        boolean isWarTime = now.isAfter(startTime) && now.isBefore(endTime);
        this.cuboidConfiguration.war = isWarTime;

        Bukkit.getOnlinePlayers().forEach(player -> {
            if (isWarTime) {
                Bukkit.getScheduler().runTask(this.survivalPlugin, () -> {
                    BossBar bossBar = CuboidTntBossBarManager.getBossBar(player.getUniqueId());
                    if (bossBar == null) {
                        bossBar = Bukkit.createBossBar(
                                MessageUtil.smallTextToColor(this.cuboidConfiguration.bossBarMessage),
                                BarColor.RED,
                                BarStyle.SOLID
                        );
                        bossBar.addPlayer(player);
                        CuboidTntBossBarManager.addBossBar(player.getUniqueId(), bossBar);
                    }

                    bossBar.setTitle(this.cuboidConfiguration.bossBarMessage);
                });
            } else {
                CuboidTntBossBarManager.removeBossBar(player.getUniqueId());
            }
        });
    }
}
