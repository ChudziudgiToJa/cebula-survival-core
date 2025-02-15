package pl.cebula.smp.feature.end;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.configuration.implementation.WorldsSettings;
import pl.cebula.smp.util.MessageUtil;

public class EndTask extends BukkitRunnable {

    private final WorldsSettings worldsSettings;
    private final SurvivalPlugin survivalPlugin;

    public EndTask(final SurvivalPlugin survivalPlugin, WorldsSettings worldsSettings) {
        this.worldsSettings = worldsSettings;
        this.survivalPlugin = survivalPlugin;
        this.runTaskTimerAsynchronously(survivalPlugin, 20, 0);
    }

    @Override
    public void run() {
        Bukkit.getWorlds().forEach(world -> {
            if (world.getName().equals("world_the_end")) {
                world.getPlayers().forEach(player -> {
                    if (player.hasPermission("cebulasmp.end.admin")) return;
                    if (!this.worldsSettings.endJoinStatus) {
                        MessageUtil.sendTitle(player, "", "&cEnd został zamknięty", 20, 60, 20);
                        Bukkit.getScheduler().runTask(this.survivalPlugin, () -> {
                            player.teleport(Bukkit.getWorlds().getFirst().getSpawnLocation());
                        });
                    }
                });
            }

        });
    }
}
