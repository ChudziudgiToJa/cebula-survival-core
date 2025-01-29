package pl.cebula.smp.feature.clan;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.cebula.smp.database.repository.Identifiable;
import pl.cebula.smp.feature.clan.feature.cuboid.ClanCuboidHearthLocation;

import java.io.Serializable;
import java.util.ArrayList;


@Getter
@Setter
public class Clan implements Serializable, Identifiable<String> {

    private String uuid;
    private String tag;
    private String ownerName;

    private ClanCuboidHearthLocation location;
    private int cuboidHearthValue;

    private ArrayList<String> memberArrayList;

    private boolean pvp;

    public Clan(Player player, String tag) {
        this.uuid = player.getUniqueId().toString();
        this.ownerName = player.getName();
        this.tag = tag.toUpperCase();

        this.location = new ClanCuboidHearthLocation(player.getLocation().getX() - 1.5, player.getLocation().getY(), player.getLocation().getZ() - 1.5);
        this.cuboidHearthValue = 200;

        this.memberArrayList = new ArrayList<>();
        this.memberArrayList.add(player.getName());

        this.pvp = false;
    }


    @Override
    public String getId() {
        return this.uuid;
    }

    public int getOnlineMembersCount() {
        int onlineCount = 0;
        Player owner = Bukkit.getPlayer(this.ownerName);
        if (owner != null) {
            onlineCount++;
        }

        for (String playerName : memberArrayList) {
            Player player = Bukkit.getPlayer(playerName);
            if (player != null && player.isOnline()) {
                onlineCount++;
            }
        }

        return onlineCount;
    }

    public Location getClanLocation() {
        return new Location(Bukkit.getWorlds().getFirst(), this.getLocation().getX(), this.getLocation().getY(), this.getLocation().getZ());
    }
}
