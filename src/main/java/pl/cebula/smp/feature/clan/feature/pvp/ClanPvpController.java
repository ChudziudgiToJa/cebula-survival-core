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
        if(event.getEntity().getType() == EntityType.PLAYER) {
            Player player = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();
            Clan clan = this.clanService.findClanByMember(damager.getName());
            if (!clan.isPvp()) {
                if (clan.getMemberArrayList().contains(player.getName()) || clan.getOwnerName().equals(player.getName())) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
