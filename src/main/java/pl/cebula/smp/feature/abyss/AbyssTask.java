package pl.cebula.smp.feature.abyss;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.util.MessageUtil;

public class AbyssTask extends BukkitRunnable {
    private final SurvivalPlugin survivalPlugin;
    private int countdown;

    public AbyssTask(SurvivalPlugin survivalPlugin) {
        this.survivalPlugin = survivalPlugin;
        this.countdown = 60;
        this.runTaskTimerAsynchronously(survivalPlugin, 0, 20); // Runs asynchronously every second
    }

    @Override
    public void run() {
        if (countdown == 60 || countdown == 30 || countdown == 10) {
            notifyPlayers(Sound.ENTITY_BAT_HURT, "&3ⓚⓄⓈⓏ &7Przedmioty z ziemi zostaną usunięte za &3&n" + countdown + "&7 sekund.");
        } else if (countdown == 0) {
            notifyPlayers(Sound.BLOCK_BELL_RESONATE, "&3ⓚⓄⓈⓏ &7Przedmioty z ziemi zostały &3&nusunięte&7.");
            Bukkit.getScheduler().runTask(survivalPlugin, () -> {
                Bukkit.getWorlds().getFirst().getEntities().forEach(entity -> {
                    if (entity instanceof Item) {
                        entity.remove();
                    }
                });
            });

            this.cancel();
        }
        countdown--;
    }

    private void notifyPlayers(Sound sound, String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), sound, 5, 5);
            MessageUtil.sendMessage(player, message);
        }
    }
}
