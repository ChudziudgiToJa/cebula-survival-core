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
        if (player.hasPermission("cebulasmp.clan.admin")) return;

        String command = event.getMessage().split(" ")[0].substring(1).toLowerCase();

        if (command.equals("tpaaccept") || command.equals("tpaccept")) {
            String[] args = event.getMessage().split(" ");
            if (args.length > 1) {
                String targetPlayerName = args[1];
                if (clan.getMemberArrayList().contains(targetPlayerName) || clan.getOwnerName().equals(targetPlayerName)) {
                    return;
                }
            }
        }

        if (clan.getMemberArrayList().contains(player.getName()) || clan.getOwnerName().equals(player.getName())) {
            if (this.clanConfiguration.blockCommandListForClan.contains(command)) {
                event.setCancelled(true);
                MessageUtil.sendTitle(player, "", "&ckomenda jest zablokowana na terenie klanu.", 20, 50, 20);
            }
        } else {
            if (this.clanConfiguration.blockCommandList.contains(command)) {
                event.setCancelled(true);
                MessageUtil.sendTitle(player, "", "&ckomenda jest zablokowana na terenie klanu.", 20, 50, 20);
            }
        }
    }
}
