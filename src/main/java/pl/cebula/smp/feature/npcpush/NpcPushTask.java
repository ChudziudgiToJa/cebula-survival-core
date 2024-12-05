package pl.cebula.smp.feature.npcpush;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.configuration.implementation.PluginConfiguration;

public class NpcPushTask extends BukkitRunnable {

    private final SurvivalPlugin survivalPlugin;
    private final PluginConfiguration pluginConfiguration;

    public NpcPushTask(SurvivalPlugin survivalPlugin, PluginConfiguration pluginConfiguration) {
        this.survivalPlugin = survivalPlugin;
        this.pluginConfiguration = pluginConfiguration;
        this.runTaskTimerAsynchronously(survivalPlugin, 5,5);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            for (NPC npc : CitizensAPI.getNPCRegistry()) {
                if (player.getLocation().distance(npc.getEntity().getLocation()) < 1.30) {
                    player.setVelocity(player.getLocation().toVector().subtract(npc.getEntity().getLocation().toVector()).normalize().multiply(1.5));
                }
            }
        });
    }
}
