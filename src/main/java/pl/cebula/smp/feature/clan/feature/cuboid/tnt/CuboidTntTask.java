package pl.cebula.smp.feature.clan.feature.cuboid.tnt;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.configuration.implementation.ClanConfiguration;
import pl.cebula.smp.util.MessageUtil;

public class CuboidTntTask extends BukkitRunnable {

    private final SurvivalPlugin survivalPlugin;
    private final ClanConfiguration cuboidConfiguration;

    public CuboidTntTask(SurvivalPlugin survivalPlugin, ClanConfiguration cuboidConfiguration) {
        this.survivalPlugin = survivalPlugin;
        this.cuboidConfiguration = cuboidConfiguration;
        this.runTaskTimerAsynchronously(this.survivalPlugin, 20*60, 0);
    }


    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (this.cuboidConfiguration.war) {
                if (CuboidTntBossBarManager.containsKeyBossBar(player.getUniqueId())) {
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
                }
            } else {
                CuboidTntBossBarManager.removeBossBar(player.getUniqueId());
            }
        });
    }
}
