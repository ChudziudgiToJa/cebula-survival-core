package pl.cebula.smp.feature.pet.task;

import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.user.UserService;

public class PetRemoveBuggyPetsTask extends BukkitRunnable {

    private final UserService userService;

    public PetRemoveBuggyPetsTask(final SurvivalPlugin survivalPlugin, UserService userService) {
        this.userService = userService;
        this.runTaskTimerAsynchronously(survivalPlugin, 600, 0);
    }

    @Override
    public void run() {
        this.userService.userConcurrentHashMap.forEach((uuid, user) -> {
            Player player = Bukkit.getPlayer(user.getNickName());
            if (player == null) {
                user.getPetDataArrayList().forEach(pet ->
                {
                    DHAPI.removeHologram(pet.getUuid().toString());
                });
            }
        });
    }
}
