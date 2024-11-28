package pl.cebula.smp.util;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.Serializable;
@Getter
@Setter
public class LocationSerializer implements Serializable {

    private final String world;
    private final int x;
    private int y;
    private final int z;
    private float yaw;
    private float pitch;
    private int size;

    public LocationSerializer(String world, int x, int y, int z, float yaw, float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }
    public LocationSerializer(String world, int x, int y, int z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public LocationSerializer(String world, int x, int y, int z, int size) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = size;
    }

    public static Location toLocation(LocationSerializer locationSerializer){
        return new Location(Bukkit.getWorld(locationSerializer.getWorld()), locationSerializer.getX(), locationSerializer.getY(), locationSerializer.getZ(), locationSerializer.getYaw(), locationSerializer.getPitch());
    }

    public static LocationSerializer fromLocation(Location location){
        return new LocationSerializer(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getYaw(), location.getPitch());
    }

    public void addY(int Y){
        this.y += y;
    }
}
