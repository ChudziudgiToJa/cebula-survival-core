package pl.cebula.smp.feature.nether;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.configuration.implementation.NetherConfiguration;
import pl.cebula.smp.util.MessageUtil;

public class NetherTask extends BukkitRunnable {

    private final NetherConfiguration netherConfiguration;
    private final SurvivalPlugin survivalPlugin;

    public NetherTask(final SurvivalPlugin survivalPlugin, NetherConfiguration netherConfiguration) {
        this.netherConfiguration = netherConfiguration;
        this.survivalPlugin = survivalPlugin;
        this.runTaskTimerAsynchronously(survivalPlugin, 20 * 10, 0);
    }

    @Override
    public void run() {
        Bukkit.getWorlds().forEach(world -> {
            if (world.getName().equalsIgnoreCase("nether_world")) {
                world.getPlayers().forEach(player -> {
                    if (!this.netherConfiguration.NetherJoinStatus) {
                        MessageUtil.sendTitle(player, "", "&cNether został zamknięty", 20,60,20);
                        Bukkit.getScheduler().runTask(this.survivalPlugin, () -> {
                            player.teleport(Bukkit.getWorlds().getFirst().getSpawnLocation());
                        });
                    }
                });
            }
        });
    }
}
