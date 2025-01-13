package pl.cebula.smp.feature.clan.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.feature.armor.ClanArmorHandler;
import pl.cebula.smp.feature.clan.service.ClanService;

import java.util.ArrayList;
import java.util.List;

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


    public static List<String> formatPlayerStatus(List<String> playerNames) {
        List<String> formattedNames = new ArrayList<>();
        for (String name : playerNames) {
            Player player = Bukkit.getPlayerExact(name);
            if (player != null && player.isOnline()) {
                formattedNames.add("&a" + name);
            } else {
                formattedNames.add("&7" + name);
            }
        }

        return formattedNames;
    }
}
