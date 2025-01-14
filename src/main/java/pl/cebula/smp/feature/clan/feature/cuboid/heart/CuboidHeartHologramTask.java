package pl.cebula.smp.feature.clan.feature.cuboid.heart;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.service.ClanService;
import pl.cebula.smp.util.MessageUtil;

import java.util.ArrayList;
import java.util.List;

public class CuboidHeartHologramTask extends BukkitRunnable {

    private final SurvivalPlugin survivalPlugin;
    private final ClanService clanService;

    public CuboidHeartHologramTask(SurvivalPlugin survivalPlugin, ClanService clanService) {
        this.survivalPlugin = survivalPlugin;
        this.clanService = clanService;
        this.runTaskTimerAsynchronously(this.survivalPlugin, 50, 0);
    }

    @Override
    public void run() {
        clanService.getAllClans().forEach(clan -> {
            Hologram hologram = DHAPI.getHologram(clan.getTag());
            if (hologram != null) {
                List<String> description = new ArrayList<>();
                description.add(MessageUtil.smallTextToColor(clan.getTag()));
                description.add(MessageUtil.smallTextToColor("&a" + clan.getOnlineMembersCount() + "&8/&7" + (clan.getMemberArrayList().size() + 1) + " &f członków online"));
                description.add(MessageUtil.smallTextToColor("&fżycie &d" + clan.getCuboidHearthValue()));
                DHAPI.setHologramLines(hologram, description);
            }
        });
    }
}
