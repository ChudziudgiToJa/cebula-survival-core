package pl.cebula.smp.feature.clan.feature.war;

import org.bukkit.boss.BossBar;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClanWarBossBarManager {
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
