package pl.cebula.smp.feature.clan.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.feature.armor.ClanArmorHandler;

public class ClanManager {

    public static void addMember(Clan clan, String string) {
        clan.getMemberArrayList().add(string);
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.getWorld().getPlayers().stream().filter(nearbyPlayer -> !nearbyPlayer.equals(player)).forEach(nearbyPlayer -> {
                if (clan.getMemberArrayList().contains(nearbyPlayer.getName()) ||
                        clan.getMemberArrayList().contains(player.getName()) ||
                        clan.getOwnerName().equals(player.getName())) {
                    ClanArmorHandler.sendArmorPacket(player, nearbyPlayer);
                }
            });
        });
    }

    public static void removeMember(Clan clan, final String string) {
        clan.getMemberArrayList().remove(string);
        Player player = Bukkit.getPlayer(string);
        if (player == null) return;
        Bukkit.getOnlinePlayers().forEach(target -> {
            ClanArmorHandler.refreshArmorPacket(player, target);
        });
    }
}
