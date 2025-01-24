package pl.cebula.smp.feature.clan.feature.cuboid.visual;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.service.ClanService;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.MessageUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


public class ClanCuboidAlertTask extends BukkitRunnable {
    private final ClanService clanService;
    private final Map<UUID, Boolean> playerClanStatusMap;
    private final UserService userService;

    public ClanCuboidAlertTask(ClanService clanService, SurvivalPlugin survivalPlugin, UserService userService) {
        this.clanService = clanService;
        this.userService = userService;
        this.playerClanStatusMap = new HashMap<>();
        this.runTaskTimerAsynchronously(survivalPlugin, 20, 0);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Location playerLocation = player.getLocation();
            Clan clan = clanService.findClanByLocation(playerLocation);

            boolean isInClanTerritory = clan != null;
            boolean wasInClanTerritory = playerClanStatusMap.getOrDefault(player.getUniqueId(), false);

            if (isInClanTerritory && !wasInClanTerritory) {
                playerClanStatusMap.put(player.getUniqueId(), true);

                User user = userService.findUserByUUID(player.getUniqueId());
                if (user == null || user.isVanish()) {
                    continue;
                }

                String playerName = player.getName();

                clan.getMemberArrayList().stream()
                        .filter(memberName -> !memberName.equals(playerName))
                        .map(Bukkit::getPlayer)
                        .filter(Objects::nonNull)
                        .forEach(clanMember -> {
                            if (clan.getOwnerName().equals(playerName)) return; // Pomijamy właściciela klanu
                            MessageUtil.sendActionbar(clanMember, "&c" + playerName + " wchodzi na teren twojego klanu!");
                        });
            }
            else if (!isInClanTerritory && wasInClanTerritory) {
                playerClanStatusMap.put(player.getUniqueId(), false);
            }
        }
    }

}
