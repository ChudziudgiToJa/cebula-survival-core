package pl.cebula.smp.feature.disco;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.service.ClanService;

import java.util.Collection;
import java.util.Random;

public class DiscoTask extends BukkitRunnable {

    private final DiscoService discoService;
    private final Random random;
    private final ClanService clanService;

    public DiscoTask(SurvivalPlugin survivalPlugin, DiscoService discoService, Random random, ClanService clanService) {
        this.discoService = discoService;
        this.random = random;
        this.clanService = clanService;
        this.runTaskTimerAsynchronously(survivalPlugin, 0, 6);
    }

    @Override
    public void run() {
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();

        onlinePlayers.forEach(player -> {
            Disco disco = this.discoService.findByUuid(player.getUniqueId().toString());
            if (disco == null || !player.hasPermission("cebulasmp.disco")) {
                return;
            }

            if (player.isSneaking()) {
                handleDiscoForPlayer(player, player, disco);
            } else {
                DiscoPackethandler.refreshArmorPacket(player, player);
            }

            Clan playerClan = this.clanService.findClanByMember(player.getName());
            onlinePlayers.stream()
                    .filter(nearbyPlayer -> !nearbyPlayer.equals(player))
                    .filter(nearbyPlayer -> nearbyPlayer.getWorld().equals(player.getWorld()))
                    .filter(nearbyPlayer -> nearbyPlayer.getLocation().distance(player.getLocation()) <= 30)
                    .filter(nearbyPlayer -> {
                        Clan nearbyPlayerClan = this.clanService.findClanByMember(nearbyPlayer.getName());
                        return playerClan == null || !playerClan.equals(nearbyPlayerClan);
                    })
                    .forEach(nearbyPlayer -> handleDiscoForPlayer(player, nearbyPlayer, disco));
        });
    }

    private void handleDiscoForPlayer(Player sourcePlayer, Player targetPlayer, Disco disco) {
        switch (disco.getDiscoType()) {
            case TURBO -> {
                DiscoPackethandler.sendArmorPacket(sourcePlayer, targetPlayer, DiscoColorHandler.getRandomColor(random));
            }
            case SMOOTH -> {
                int r = (int) (Math.sin(System.currentTimeMillis() / 1000.0) * 127 + 128);
                int g = (int) (Math.sin(System.currentTimeMillis() / 1000.0 + 2 * Math.PI / 3) * 127 + 128);
                int b = (int) (Math.sin(System.currentTimeMillis() / 1000.0 + 4 * Math.PI / 3) * 127 + 128);
                Color smoothColor = Color.fromRGB(r, g, b);
                DiscoPackethandler.sendArmorPacket(sourcePlayer, targetPlayer, smoothColor);
            }
            case RANDOM -> {
                DiscoPackethandler.sendHelmetPacket(sourcePlayer, targetPlayer, DiscoColorHandler.getRandomColor(this.random));
                DiscoPackethandler.sendChestPlatePacket(sourcePlayer, targetPlayer, DiscoColorHandler.getRandomColor(this.random));
                DiscoPackethandler.sendLeggingsPacket(sourcePlayer, targetPlayer, DiscoColorHandler.getRandomColor(this.random));
                DiscoPackethandler.sendBootsPacket(sourcePlayer, targetPlayer, DiscoColorHandler.getRandomColor(this.random));
            }
            case CLEAR -> {
                DiscoPackethandler.refreshArmorPacket(sourcePlayer, targetPlayer);
            }
        }
    }
}