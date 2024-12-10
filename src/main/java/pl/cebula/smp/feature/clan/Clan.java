package pl.cebula.smp.feature.clan;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.cebula.smp.database.repository.Identifiable;
import pl.cebula.smp.feature.backup.Backup;
import pl.cebula.smp.feature.clan.feature.armor.ClanArmorHandler;
import pl.cebula.smp.feature.job.JobType;
import pl.cebula.smp.feature.kit.KitData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;


@Getter
@Setter
public class Clan implements Serializable, Identifiable<String> {

    private String uuid;
    private String tag;
    private String ownerName;

    private ArrayList<String> memberArrayList;

    private boolean pvp;

    public Clan(Player player, String tag) {
        this.uuid = player.getUniqueId().toString();
        this.ownerName = player.getName();
        this.tag = tag.toUpperCase();

        this.memberArrayList = new ArrayList<>();

        this.pvp = false;
    }


    @Override
    public String getId() {
        return this.uuid;
    }
}
