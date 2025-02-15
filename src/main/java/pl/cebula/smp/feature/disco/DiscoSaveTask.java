package pl.cebula.smp.feature.disco;

import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;

public class DiscoSaveTask extends BukkitRunnable {

    private final DiscoService discoService;

    public DiscoSaveTask(SurvivalPlugin survivalPlugin, DiscoService discoService) {
        this.discoService = discoService;
        this.runTaskTimerAsynchronously(survivalPlugin, 20*5, 5);
    }

    @Override
    public void run() {
        this.discoService.saveAll();
    }
}
