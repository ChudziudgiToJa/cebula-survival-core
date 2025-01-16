package pl.cebula.smp.feature.clan.feature.cuboid.visual;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.service.ClanService;
import pl.cebula.smp.util.MessageUtil;

import java.util.HashMap;
import java.util.UUID;


public class ClanCuboidJoinQuitTask extends BukkitRunnable {
    private final ClanService clanService;
    private final HashMap<UUID, Boolean> map;

    public ClanCuboidJoinQuitTask(ClanService clanService, SurvivalPlugin survivalPlugin) {
        this.clanService = clanService;
        this.map = new HashMap<>();
        this.runTaskTimerAsynchronously(survivalPlugin, 20, 0);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            Location playerLocation = player.getLocation();
            Clan clan = this.clanService.findClanByLocation(playerLocation);

            if (clan != null) {
                if (!map.containsKey(player.getUniqueId()) || !map.get(player.getUniqueId())) {
                    map.put(player.getUniqueId(), true);
                    MessageUtil.sendTitle(player, "", "&fwchodzisz na teren klanu&8: &a" + clan.getTag(), 20,20,20);
                }
            } else {
                if (map.containsKey(player.getUniqueId()) && map.get(player.getUniqueId())) {
                    map.put(player.getUniqueId(), false);
                    MessageUtil.sendTitle(player, "", "&cwchodzisz z terenu klanu", 20,20,20);

                }
            }
        });
    }
}
