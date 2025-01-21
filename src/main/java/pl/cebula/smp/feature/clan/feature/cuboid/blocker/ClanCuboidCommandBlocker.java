package pl.cebula.smp.feature.clan.feature.cuboid.blocker;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.cebula.smp.configuration.implementation.ClanConfiguration;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.service.ClanService;
import pl.cebula.smp.util.MessageUtil;

public class ClanCuboidCommandBlocker implements Listener {

    private final ClanService clanService;
    private final ClanConfiguration clanConfiguration;

    public ClanCuboidCommandBlocker(ClanService clanService, ClanConfiguration clanConfiguration) {
        this.clanService = clanService;
        this.clanConfiguration = clanConfiguration;
    }

    @EventHandler
    public void onSendCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        Clan clan = this.clanService.findClanByLocation(player.getLocation());

        if (clan == null) {
            return;
        }

        String command = event.getMessage().split(" ")[0].substring(1).toLowerCase();

        if (this.clanConfiguration.blockCommandList.contains(command)) {
            event.setCancelled(true);
            MessageUtil.sendTitle(player, "", "&akomenda jest zablokowana na terenie klanu.", 20, 50, 20);
        }
    }
}
