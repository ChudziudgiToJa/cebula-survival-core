package pl.cebula.smp.feature.clan.feature.war;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.cebula.smp.configuration.implementation.ClanConfiguration;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.manager.ClanManager;

import java.util.Iterator;

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

    @EventHandler
    public void onExplosion(EntityExplodeEvent event) {
        if (clanConfiguration.isWar()) return;
        event.setCancelled(true);
    }
}
