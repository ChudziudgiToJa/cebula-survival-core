package pl.cebula.smp.feature.blocker;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;

import java.util.Arrays;

public class MobChunkLimitTask extends BukkitRunnable {

    private final SurvivalPlugin survivalPlugin;

    public MobChunkLimitTask(final SurvivalPlugin survivalPlugin) {
        this.survivalPlugin = survivalPlugin;
        this.runTaskTimerAsynchronously(this.survivalPlugin, 0,20*5);
    }


    @Override
    public void run() {
        for (World world : Bukkit.getWorlds()) {
            for (Chunk chunk : world.getLoadedChunks()) {
                Entity[] entities = chunk.getEntities();

                long mobCount = Arrays.stream(entities)
                        .filter(e -> e instanceof LivingEntity && !(e instanceof Player))
                        .count();

                long armorStandCount = Arrays.stream(entities)
                        .filter(e -> e instanceof ArmorStand)
                        .count();

                long mineCartHopperCount = Arrays.stream(entities)
                        .filter(e -> e instanceof HopperMinecart)
                        .count();

                if (mobCount > 15) {
                    long excess = mobCount - 15;
                    Arrays.stream(entities)
                            .filter(e -> e instanceof LivingEntity && !(e instanceof Player))
                            .filter(e -> e.getCustomName() == null)
                            .limit(excess)
                            .forEach(entity -> Bukkit.getScheduler().runTask(this.survivalPlugin, entity::remove));
                }

                if (armorStandCount > 5) {
                    long excess = armorStandCount - 5;
                    Arrays.stream(entities)
                            .filter(e -> e instanceof ArmorStand)
                            .limit(excess)
                            .forEach(entity -> Bukkit.getScheduler().runTask(this.survivalPlugin, entity::remove));
                }

                if (mineCartHopperCount > 5) {
                    long excess = mineCartHopperCount - 5;
                    Arrays.stream(entities)
                            .filter(e -> e instanceof HopperMinecart)
                            .limit(excess)
                            .forEach(entity -> Bukkit.getScheduler().runTask(this.survivalPlugin, entity::remove));
                }
            }
        }
    }
}
