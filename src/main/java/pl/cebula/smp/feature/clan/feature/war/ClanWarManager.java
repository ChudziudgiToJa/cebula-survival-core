package pl.cebula.smp.feature.clan.feature.war;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import pl.cebula.smp.configuration.implementation.ClanConfiguration;
import pl.cebula.smp.util.MessageUtil;

public class ClanWarManager {

    private final ClanConfiguration clanConfiguration;

    public ClanWarManager(ClanConfiguration clanConfiguration) {
        this.clanConfiguration = clanConfiguration;
    }

    private final BossBar bossBar =  Bukkit.createBossBar(
            MessageUtil.smallTextToColor("&cAtakowanie klanów jest włączone"),
            BarColor.RED,
            BarStyle.SEGMENTED_10
    );

    public void toggleWarBossBar() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (this.clanConfiguration.isWar()) {
                ClanWarBossBarManager.addBossBar(player.getUniqueId(), this.bossBar);
                this.bossBar.addPlayer(player);
            } else {
                ClanWarBossBarManager.removeBossBar(player.getUniqueId());
                this.bossBar.removePlayer(player);
            }
        });
    }

    public void removeBossBar(Player player) {
        ClanWarBossBarManager.removeBossBar(player.getUniqueId());
        this.bossBar.removePlayer(player);
    }

    public void addBossBar(Player player) {
        ClanWarBossBarManager.addBossBar(player.getUniqueId(), this.bossBar);
        this.bossBar.addPlayer(player);
    }
}
