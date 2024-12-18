package pl.cebula.smp.feature.clan.feature.pvp;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.service.ClanService;

public class ClanPvpController implements Listener {

    private final ClanService clanService;

    public ClanPvpController(ClanService clanService) {
        this.clanService = clanService;
    }

    @EventHandler
    public void onPlayerAttacked(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        if (!(event.getDamager() instanceof Player damager)) {
            return;
        }
        Clan clan = this.clanService.findClanByMember(damager.getName());
        if (clan == null) {
            return;
        }
        if (!clan.isPvp() && (clan.getMemberArrayList().contains(player.getName()) || clan.getOwnerName().equals(player.getName()))) {
            event.setCancelled(true);
        }
    }

}
