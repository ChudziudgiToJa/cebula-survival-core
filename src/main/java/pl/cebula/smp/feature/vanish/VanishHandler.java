package pl.cebula.smp.feature.vanish;

import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.util.MessageUtil;

public class VanishHandler {

    private final ProtocolManager protocolManager;

    public VanishHandler(ProtocolManager protocolManager) {
        this.protocolManager = protocolManager;
    }

    public void toggleVanish(Player player, User user) {
        user.setVanish(!user.isVanish());
        if (user.isVanish()) {
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
                if (!onlinePlayer.hasPermission("cebulasmp.vanish.see")) {
                    onlinePlayer.hidePlayer(player);
                }
            });
            MessageUtil.sendTitle(player, "", "&bvanish jest &aaktywny", 20, 50, 20);
        } else {
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
                onlinePlayer.showPlayer(player);
                sendLightningPacket(onlinePlayer, player.getLocation());
            });
            player.playSound(player.getLocation(), Sound.ENTITY_BAT_LOOP, 10,10);
            MessageUtil.sendTitle(player, "", "&bvanish jest &cwyłączony", 20, 50, 20);
        }
    }

    public void sendLightningPacket(Player player, Location location) {
        try {
            PacketContainer packet = protocolManager.createPacket(com.comphenix.protocol.PacketType.Play.Server.SPAWN_ENTITY);

            packet.getIntegers()
                    .write(0, (int) (Math.random() * Integer.MAX_VALUE))
                    .write(1, 1)
                    .write(2, location.getBlockX())
                    .write(3, location.getBlockY())
                    .write(4, location.getBlockZ());

            protocolManager.sendServerPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
