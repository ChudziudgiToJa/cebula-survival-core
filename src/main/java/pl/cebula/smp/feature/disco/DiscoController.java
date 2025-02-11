package pl.cebula.smp.feature.disco;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class DiscoController implements Listener {

    private final DiscoService discoService;

    public DiscoController(DiscoService discoService) {
        this.discoService = discoService;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Disco disco = this.discoService.findByUuid(player.getUniqueId().toString());

        if (disco == null) {
            this.discoService.create(new Disco(player.getUniqueId().toString(), DiscoType.CLEAR));
        }
    }

}
