package pl.cebula.smp.feature.disco;

import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;

public class DiscoSaveTask extends BukkitRunnable {

    private final SurvivalPlugin survivalPlugin;
    private final DiscoService discoService;

    public DiscoSaveTask(SurvivalPlugin survivalPlugin, DiscoService discoService) {
        this.survivalPlugin = survivalPlugin;
        this.discoService = discoService;
        this.runTaskTimerAsynchronously(this.survivalPlugin, 300, 0);
    }

    @Override
    public void run() {
        this.discoService.saveAll();
    }
}
