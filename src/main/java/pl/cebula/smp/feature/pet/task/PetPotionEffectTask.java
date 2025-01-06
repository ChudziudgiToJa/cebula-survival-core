package pl.cebula.smp.feature.pet.task;

import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;

public class PetPotionEffectTask extends BukkitRunnable {

    private final UserService userService;
    private final SurvivalPlugin survivalPlugin;

    public PetPotionEffectTask(UserService userService, SurvivalPlugin survivalPlugin) {
        this.userService = userService;
        this.survivalPlugin = survivalPlugin;
        this.runTaskTimerAsynchronously(this.survivalPlugin, 20, 0);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            User user = this.userService.findUserByUUID(player.getUniqueId());
            if (user.getPetDataArrayList().isEmpty()) return;
            user.getPetDataArrayList().forEach(pet -> {
                Bukkit.getScheduler().runTaskLater(this.survivalPlugin, () -> {
                    PotionEffectType potionEffectType = PotionEffectType.getByName(pet.getPetData().getPotionEffect());
                    if (potionEffectType == null) return;
                    player.addPotionEffect(new PotionEffect(potionEffectType, 40, 0, true, false));
                }, 0);
            });
        });
    }
}
