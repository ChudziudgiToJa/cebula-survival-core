package pl.cebula.smp.feature.clan.feature.armor;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.service.ClanService;
import pl.cebula.smp.feature.disco.Disco;
import pl.cebula.smp.feature.disco.DiscoService;
import pl.cebula.smp.feature.disco.DiscoType;

public class ClanArmorTask extends BukkitRunnable {

    private final ClanService clanService;
    private final DiscoService discoService;

    public ClanArmorTask(final SurvivalPlugin survivalPlugin, ClanService clanService, DiscoService discoService) {
        this.clanService = clanService;
        this.discoService = discoService;
        this.runTaskTimerAsynchronously(survivalPlugin, 40, 40);
    }


    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.getWorld().getPlayers().stream()
                    .filter(nearbyPlayer -> nearbyPlayer.getLocation().distance(player.getLocation()) < 10)
                    .filter(nearbyPlayer -> !nearbyPlayer.equals(player))
                    .forEach(nearbyPlayer -> {
                        Disco disco = this.discoService.findByUuid(player.getUniqueId().toString());
                        if (disco != null && !disco.getDiscoType().equals(DiscoType.CLEAR)) return;
                        Clan playerClan = this.clanService.findClanByMember(player.getName());
                        Clan nearbyPlayerClan = this.clanService.findClanByMember(nearbyPlayer.getName());
                        if (playerClan != null && playerClan.equals(nearbyPlayerClan)) {
                            ClanArmorHandler.sendArmorPacket(player, nearbyPlayer);
                        }
                    });
        });
    }
}