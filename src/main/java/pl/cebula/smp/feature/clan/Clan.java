package pl.cebula.smp.feature.clan;

import eu.okaeri.configs.yaml.bukkit.serdes.serializer.LocationSerializer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.cebula.smp.database.repository.Identifiable;
import pl.cebula.smp.feature.clan.feature.cuboid.CuboidHearthLocation;

import java.io.Serializable;
import java.util.ArrayList;


@Getter
@Setter
public class Clan implements Serializable, Identifiable<String> {

    private String uuid;
    private String tag;
    private String ownerName;

    private CuboidHearthLocation location;

    private ArrayList<String> memberArrayList;

    private boolean pvp;

    public Clan(Player player, String tag) {
        this.uuid = player.getUniqueId().toString();
        this.ownerName = player.getName();
        this.tag = tag.toUpperCase();

        this.location = new CuboidHearthLocation(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());

        this.memberArrayList = new ArrayList<>();

        this.pvp = false;
    }


    @Override
    public String getId() {
        return this.uuid;
    }
}
