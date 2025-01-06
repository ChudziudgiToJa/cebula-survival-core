package pl.cebula.smp.feature.clan.task;

import org.bukkit.Bukkit;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.service.ClanService;

public class ClanSaveTask implements Runnable {

    private final ClanService clanService;

    public ClanSaveTask(final SurvivalPlugin survivalPlugin , ClanService clanService) {
        this.clanService = clanService;
        Bukkit.getScheduler().runTaskTimerAsynchronously(survivalPlugin, this, 600L, 20L);
    }


    @Override
    public void run() {
        this.clanService.saveAllClans();
    }
}