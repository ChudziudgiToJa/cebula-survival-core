package pl.cebula.smp.feature.clan.feature.cuboid.visual;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Color;
import org.bukkit.Location;

import org.bukkit.entity.Player;

public class ClanCuboidPacketHandler {


    public static void sendParticle(Player player, Location location, float size) {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.WORLD_PARTICLES);
        packet.getParticles().write(0, EnumWrappers.Particle.REDSTONE);
        packet.getFloat().write(0, (float) location.getX()); // X
        packet.getFloat().write(1, (float) location.getY()); // Y
        packet.getFloat().write(2, (float) location.getZ()); // Z
        packet.getFloat().write(3, 0f); // Offset X
        packet.getFloat().write(4, 0f); // Offset Y
        packet.getFloat().write(5, 0f); // Offset Z
        packet.getFloat().write(6, 1f); // Particle speed
        packet.getIntegers().write(0, 1); // Particle count
        packet.getFloat().write(10, size); // Size
        ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
    }
}