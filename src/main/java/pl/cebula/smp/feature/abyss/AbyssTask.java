package pl.cebula.smp.feature.abyss;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.util.MessageUtil;

public class AbyssTask extends BukkitRunnable {
    private static final int START_TIME = 600;
    private final SurvivalPlugin survivalPlugin;

    public AbyssTask(SurvivalPlugin survivalPlugin) {
        this.survivalPlugin = survivalPlugin;
        this.runTaskTimerAsynchronously(survivalPlugin, 0, 20);
    }

    @Override
    public void run() {
        if (AbyssManager.time <= 0) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.playSound(player, Sound.BLOCK_BELL_RESONATE, 5, 5);
                MessageUtil.sendMessage(player, " ");
                MessageUtil.sendMessage(player, "&a♻ &fPorządki zakończone!");
                MessageUtil.sendMessage(player, "            &7Wszystkie byty zostały skutecznie usunięte. &2\uD83C\uDF0D");
                MessageUtil.sendMessage(player, " ");
            });
            Bukkit.getScheduler().runTask(survivalPlugin, () -> {
                Bukkit.getWorlds().forEach(world ->
                        world.getEntities().stream()
                                .filter(entity ->
                                        entity instanceof Item ||
                                                (entity instanceof Monster) ||
                                                (entity instanceof ArmorStand))
                                .forEach(Entity::remove)
                );
            });

            AbyssManager.time = START_TIME;
        } else {
            AbyssManager.time--;
        }
    }

}
