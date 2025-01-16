package pl.cebula.smp.feature.clan.feature.cuboid.heart;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.service.ClanService;
import pl.cebula.smp.util.MessageUtil;

import java.util.ArrayList;
import java.util.List;

public class ClanCuboidHeartHologramTask extends BukkitRunnable {

    private final ClanService clanService;

    public ClanCuboidHeartHologramTask(SurvivalPlugin survivalPlugin, ClanService clanService) {
        this.clanService = clanService;
        this.runTaskTimerAsynchronously(survivalPlugin, 20*30, 20);
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
