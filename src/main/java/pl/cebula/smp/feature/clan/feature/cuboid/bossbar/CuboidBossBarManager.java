package pl.cebula.smp.feature.clan.feature.cuboid.bossbar;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CuboidBossBarManager {
    private static final Map<UUID, BossBar> bossBars = new HashMap<>();

    public static BossBar getBossBar(UUID uuid) {
        return bossBars.get(uuid);
    }

    public static void addBossBar(UUID uuid, BossBar bossBar) {
        bossBars.put(uuid, bossBar);
    }

    public static void removeBossBar(UUID uuid) {
        bossBars.remove(uuid);
    }
}
