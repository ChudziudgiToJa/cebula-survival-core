package pl.cebula.smp.feature.blocker;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;

import java.util.Arrays;

public class MobChunkLimitTask extends BukkitRunnable {

    private final SurvivalPlugin survivalPlugin;

    public MobChunkLimitTask(final SurvivalPlugin survivalPlugin) {
        this.survivalPlugin = survivalPlugin;
        this.runTaskTimerAsynchronously(this.survivalPlugin, 20*5,20);
    }


    @Override
    public void run() {
        for (World world : Bukkit.getWorlds()) {
            for (Chunk chunk : world.getLoadedChunks()) {
                Entity[] entities = chunk.getEntities();
                long mobCount = Arrays.stream(entities)
                        .filter(e -> e instanceof LivingEntity && !(e instanceof Player))
                        .count();

                if (mobCount > 20) {
                    long excess = mobCount - 20;
                    Arrays.stream(entities)
                            .filter(e -> e instanceof LivingEntity && !(e instanceof Player))
                            .limit(excess)
                            .forEach(entity -> {
                                Bukkit.getScheduler().runTask(this.survivalPlugin, entity::remove);
                            });
                }
            }
        }
    }
}
