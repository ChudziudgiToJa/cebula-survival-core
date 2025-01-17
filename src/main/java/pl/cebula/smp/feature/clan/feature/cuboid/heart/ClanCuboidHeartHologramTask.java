package pl.cebula.smp.feature.clan.feature.cuboid.heart;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.service.ClanService;
import pl.cebula.smp.util.ItemBuilder;
import pl.cebula.smp.util.MessageUtil;

import java.util.ArrayList;
import java.util.List;

public class ClanCuboidHeartHologramTask extends BukkitRunnable {

    private final ClanService clanService;

    public ClanCuboidHeartHologramTask(SurvivalPlugin survivalPlugin, ClanService clanService) {
        this.clanService = clanService;
        this.runTaskTimerAsynchronously(survivalPlugin, 20*10, 20);
    }



    @Override
    public void run() {
        clanService.getAllClans().forEach(clan -> {
            List<String> description = new ArrayList<>();
            description.add("#SMALLHEAD: DRAGON_EGG");
            description.add(MessageUtil.smallTextToColor(clan.getTag()));
            description.add(MessageUtil.smallTextToColor("&a" + clan.getOnlineMembersCount() + "&8/&7" + (clan.getMemberArrayList().size() + 1) + " &f członków online"));
            description.add(MessageUtil.smallTextToColor("&fżycie &d" + clan.getCuboidHearthValue()));

            Hologram hologram = DHAPI.getHologram(clan.getTag());
            if (hologram != null) {
                DHAPI.setHologramLines(hologram, description);
                return;
            }
            DHAPI.createHologram(clan.getTag(), new Location(Bukkit.getWorlds().getFirst(), clan.getLocation().getX() + 0.5, clan.getLocation().getY() + 2.6, clan.getLocation().getZ() + 0.5),false, description);
            description.clear();
        });
    }
}
