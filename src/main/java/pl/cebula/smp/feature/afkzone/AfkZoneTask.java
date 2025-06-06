package pl.cebula.smp.feature.afkzone;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.configuration.implementation.LootCaseConfiguration;
import pl.cebula.smp.configuration.implementation.PluginConfiguration;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.DurationUtil;
import pl.cebula.smp.util.ItemStackSerializable;
import pl.cebula.smp.util.MessageUtil;

import java.util.UUID;

public class AfkZoneTask extends BukkitRunnable {

    private final SurvivalPlugin survivalPlugin;
    private final AfkZoneManager afkZoneManager;
    private final LootCaseConfiguration pluginConfiguration;
    private final UserService userService;

    public AfkZoneTask(SurvivalPlugin survivalPlugin, AfkZoneManager afkZoneManager, LootCaseConfiguration pluginConfiguration, UserService userService) {
        this.survivalPlugin = survivalPlugin;
        this.afkZoneManager = afkZoneManager;
        this.pluginConfiguration = pluginConfiguration;
        this.userService = userService;
        this.runTaskTimerAsynchronously(survivalPlugin, 20, 20);
    }


    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            UUID playerId = player.getUniqueId();

            if (AfkZoneUtil.isPlayerInRegion(player, "afk")) {
                long timeInAfkZone = afkZoneManager.playerInAfkZone.getOrDefault(playerId, 0L);

                timeInAfkZone += 1L;
                afkZoneManager.playerInAfkZone.put(playerId, timeInAfkZone);

                long remainingTime = 600 - timeInAfkZone;
                    MessageUtil.sendTitle(
                            player,
                            "",
                            "&aDrop klucza za: &f" + DurationUtil.convertLong(Math.max(remainingTime, 0)),
                            1, 20, 1
                    );
                if (timeInAfkZone >= 600) {
                    afkZoneManager.playerInAfkZone.remove(playerId);

                    User user = userService.findUserByNickName(player.getName());
                    if (user != null) {
                        pluginConfiguration.lootCases.stream()
                                .filter(lootCase -> lootCase.getName().equalsIgnoreCase("afk"))
                                .findFirst()
                                .ifPresent(lootCase -> {
                                    AfkZoneUtil.giveItemWithChance(
                                            player,
                                            ItemStackSerializable.readItemStack(lootCase.getKeyItemStack())
                                    );
                                    user.setMoney(user.getMoney() + 10);
                                });
                    }
                }
            } else {
                afkZoneManager.playerInAfkZone.remove(playerId);
            }
        });
    }

}
