package pl.cebula.smp.feature.clan.feature.war;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.cebula.smp.configuration.implementation.ClanConfiguration;

public class ClanWarController implements Listener {

    private final ClanConfiguration clanConfiguration;
    private final ClanWarManager clanWarManager;

    public ClanWarController(ClanConfiguration clanConfiguration, ClanWarManager clanWarManager) {
        this.clanConfiguration = clanConfiguration;
        this.clanWarManager = clanWarManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (this.clanConfiguration.isWar()) {
            this.clanWarManager.addBossBar(event.getPlayer());
        }
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent  event) {
        if (this.clanConfiguration.isWar()) {
            this.clanWarManager.removeBossBar(event.getPlayer());
        }
    }
}
