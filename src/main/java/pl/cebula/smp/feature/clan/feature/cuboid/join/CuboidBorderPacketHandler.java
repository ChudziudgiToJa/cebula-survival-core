package pl.cebula.smp.feature.clan.feature.cuboid.join;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import pl.cebula.smp.feature.clan.Clan;

public class CuboidBorderPacketHandler {

    public static void sendBorderPacket(Player player, Clan clan, ProtocolManager protocolManager) {
        try {
            PacketContainer centerPacket = new PacketContainer(PacketType.Play.Server.SET_BORDER_CENTER);
            centerPacket.getDoubles().write(0, clan.getLocation().getX());
            centerPacket.getDoubles().write(1, clan.getLocation().getZ());
            protocolManager.sendServerPacket(player, centerPacket);

            PacketContainer sizePacket = new PacketContainer(PacketType.Play.Server.SET_BORDER_SIZE);
            sizePacket.getDoubles().write(0, 42.50);
            protocolManager.sendServerPacket(player, sizePacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendBorderPacket(Player player, World world, ProtocolManager protocolManager) {
        try {
            Location center = world.getWorldBorder().getCenter();
            PacketContainer centerPacket = new PacketContainer(PacketType.Play.Server.SET_BORDER_CENTER);
            centerPacket.getDoubles().write(0, center.getX());
            centerPacket.getDoubles().write(1, center.getZ());
            protocolManager.sendServerPacket(player, centerPacket);


            PacketContainer sizePacket = new PacketContainer(PacketType.Play.Server.SET_BORDER_SIZE);
            sizePacket.getDoubles().write(0, world.getWorldBorder().getSize());
            protocolManager.sendServerPacket(player, sizePacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
