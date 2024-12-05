package pl.cebula.smp.feature.lootcase;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.configuration.implementation.PluginConfiguration;

public class LootCaseTask extends BukkitRunnable {

    private final SurvivalPlugin survivalPlugin;
    private final PluginConfiguration pluginConfiguration;

    public LootCaseTask(SurvivalPlugin survivalPlugin, PluginConfiguration pluginConfiguration) {
        this.survivalPlugin = survivalPlugin;
        this.pluginConfiguration = pluginConfiguration;
        this.runTaskTimerAsynchronously(survivalPlugin, 5,5);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            this.pluginConfiguration.lootCaseSettings.lootCases.forEach(lootCase -> {
                if (player.getLocation().distance(lootCase.getLocation()) < 2.0) {
                    player.setVelocity(player.getLocation().toVector().subtract(lootCase.getLocation().toVector()).normalize().multiply(1.5));
                }
            });
        });
    }
}
