package pl.cebula.smp.feature.nether;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.configuration.implementation.WorldsSettings;
import pl.cebula.smp.util.MessageUtil;

public class NetherTask extends BukkitRunnable {

    private final WorldsSettings worldsSettings;
    private final SurvivalPlugin survivalPlugin;

    public NetherTask(final SurvivalPlugin survivalPlugin, WorldsSettings worldsSettings) {
        this.worldsSettings = worldsSettings;
        this.survivalPlugin = survivalPlugin;
        this.runTaskTimerAsynchronously(survivalPlugin, 20, 0);
    }

    @Override
    public void run() {
        Bukkit.getWorlds().get(1).getPlayers().forEach(player -> {
            if (player.hasPermission("cebulasmp.nether.admin")) return;
            if (!this.worldsSettings.netherJoinStatus) {
                MessageUtil.sendTitle(player, "", "&cNether został zamknięty", 20,60,20);
                Bukkit.getScheduler().runTask(this.survivalPlugin, () -> {
                    player.teleport(Bukkit.getWorlds().getFirst().getSpawnLocation());
                });
            }
        });
    }
}
