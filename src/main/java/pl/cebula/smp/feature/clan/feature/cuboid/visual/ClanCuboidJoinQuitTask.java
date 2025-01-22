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


public class ClanCuboidJoinQuitTask extends BukkitRunnable {
    private final ClanService clanService;
    private final Map<UUID, Boolean> playerClanStatusMap;
    private final UserService userService;

    public ClanCuboidJoinQuitTask(ClanService clanService, SurvivalPlugin survivalPlugin, UserService userService) {
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
                    return;
                }

                String playerName = player.getName();
                clan.getMemberArrayList().stream()
                        .map(Bukkit::getPlayer)
                        .filter(Objects::nonNull)
                        .filter(clanMember -> !clanMember.getName().equalsIgnoreCase(playerName))
                        .forEach(clanMember -> MessageUtil.sendActionbar(clanMember, "&c" + playerName + " wchodzi na teren twojego klanu!"));
            } else if (!isInClanTerritory && wasInClanTerritory) {
                playerClanStatusMap.put(player.getUniqueId(), false);
            }
        }
    }
}
