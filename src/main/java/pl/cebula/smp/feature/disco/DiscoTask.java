package pl.cebula.smp.feature.disco;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;

import java.util.Random;

public class DiscoTask extends BukkitRunnable {

    private final DiscoService discoService;
    private final Random random;

    public DiscoTask(SurvivalPlugin survivalPlugin, DiscoService discoService, Random random) {
        this.discoService = discoService;
        this.random = random;
        this.runTaskTimerAsynchronously(survivalPlugin, 20 * 30, 0);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            Disco disco = this.discoService.findByUuid(player.getUniqueId().toString());
            if (disco == null) return;
            if (disco.getDiscoType().equals(DiscoType.CLEAR)) return;
            if (!player.hasPermission("cebulasmp.disco")) return;

            Bukkit.getOnlinePlayers().stream()
                    .filter(nearbyPlayer -> !nearbyPlayer.equals(player))
                    .forEach(nearbyPlayer -> {
                if (!player.isSneaking()) {
                    DiscoPackethandler.refreshArmorPacket(player, player);
                }

                switch (disco.getDiscoType()) {
                    case TURBO -> {
                        if (player.isSneaking()) {
                            DiscoPackethandler.sendArmorPacket(player, player, DiscoColorHandler.getRandomColor(random));
                        } else {
                            DiscoPackethandler.refreshArmorPacket(player, player);
                        }
                        DiscoPackethandler.sendArmorPacket(player, nearbyPlayer, DiscoColorHandler.getRandomColor(random));
                    }
                    case SMOOTH -> {
                        int r = (int) (Math.sin(System.currentTimeMillis() / 1000.0) * 127 + 128);
                        int g = (int) (Math.sin(System.currentTimeMillis() / 1000.0 + 2 * Math.PI / 3) * 127 + 128);
                        int b = (int) (Math.sin(System.currentTimeMillis() / 1000.0 + 4 * Math.PI / 3) * 127 + 128);
                        Color smoothColor = Color.fromRGB(r, g, b);
                        if (player.isSneaking()) {
                            DiscoPackethandler.sendArmorPacket(player, player, smoothColor);
                        }
                        DiscoPackethandler.sendArmorPacket(player, nearbyPlayer, smoothColor);
                    }
                    case RANDOM -> {
                        if (player.isSneaking()) {
                            DiscoPackethandler.sendHelmetPacket(player, player, DiscoColorHandler.getRandomColor(this.random));
                            DiscoPackethandler.sendChestPlatePacket(player, player, DiscoColorHandler.getRandomColor(this.random));
                            DiscoPackethandler.sendLeggingsPacket(player, player, DiscoColorHandler.getRandomColor(this.random));
                            DiscoPackethandler.sendBootsPacket(player, player, DiscoColorHandler.getRandomColor(this.random));
                        }
                        DiscoPackethandler.sendHelmetPacket(player, nearbyPlayer, DiscoColorHandler.getRandomColor(this.random));
                        DiscoPackethandler.sendChestPlatePacket(player, nearbyPlayer, DiscoColorHandler.getRandomColor(this.random));
                        DiscoPackethandler.sendLeggingsPacket(player, nearbyPlayer, DiscoColorHandler.getRandomColor(this.random));
                        DiscoPackethandler.sendBootsPacket(player, nearbyPlayer, DiscoColorHandler.getRandomColor(this.random));
                    }
                }
            });
        });
    }
}