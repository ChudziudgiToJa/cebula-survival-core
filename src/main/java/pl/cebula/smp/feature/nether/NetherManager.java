package pl.cebula.smp.feature.nether;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import pl.cebula.smp.configuration.implementation.NetherConfiguration;
import pl.cebula.smp.util.MessageUtil;

public class NetherManager {

    private final NetherConfiguration netherConfiguration;

    private final BossBar bossBar =  Bukkit.createBossBar(
            MessageUtil.smallTextToColor("&cNether jest &awłączony"),
            BarColor.RED,
            BarStyle.SOLID
    );

    public NetherManager(NetherConfiguration netherConfiguration) {
        this.netherConfiguration = netherConfiguration;
    }

    public void toggleNetherBossBar() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (this.netherConfiguration.NetherJoinStatus) {
                NetherBossBarManager.addBossBar(player.getUniqueId(), this.bossBar);
                this.bossBar.addPlayer(player);
            } else {
                NetherBossBarManager.removeBossBar(player.getUniqueId());
                this.bossBar.removePlayer(player);
            }
        });
    }

    public void removeBossBar(Player player) {
        NetherBossBarManager.removeBossBar(player.getUniqueId());
        this.bossBar.removePlayer(player);
    }

    public void addBossBar(Player player) {
        NetherBossBarManager.addBossBar(player.getUniqueId(), this.bossBar);
        this.bossBar.addPlayer(player);
    }
}
