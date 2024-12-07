package pl.cebula.smp.feature.clan.feature.armor;


import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.service.ClanService;

public class ClanArmorTask extends BukkitRunnable {

    private final ClanService clanService;

    public ClanArmorTask(final SurvivalPlugin survivalPlugin, ClanService clanService) {
        this.clanService = clanService;
        this.runTaskTimerAsynchronously(survivalPlugin, 20, 20);
    }


    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.getWorld().getPlayers().stream()
                    .filter(nearbyPlayer -> nearbyPlayer.getLocation().distance(player.getLocation()) < 30)
                    .filter(nearbyPlayer -> !nearbyPlayer.equals(player))
                    .forEach(nearbyPlayer -> {
                        Clan clan = this.clanService.findClanByMember(nearbyPlayer.getName());
                        if (clan == null) return;
                        if (clan.getMemberArrayList().contains(nearbyPlayer.getName()) ||
                                clan.getMemberArrayList().contains(player.getName()) ||
                                clan.getOwnerName().equals(player.getName())) {
                            ClanArmorUtil.sendArmorPacket(player, nearbyPlayer);
                        }
                    });
        });
    }

}
