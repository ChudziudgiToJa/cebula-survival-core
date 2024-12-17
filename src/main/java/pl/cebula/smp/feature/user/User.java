package pl.cebula.smp.feature.user;

import it.unimi.dsi.fastutil.Hash;
import lombok.Getter;
import lombok.Setter;

import org.bukkit.entity.Player;
import pl.cebula.smp.database.repository.Identifiable;
import pl.cebula.smp.feature.backup.Backup;
import pl.cebula.smp.feature.job.JobType;
import pl.cebula.smp.feature.kit.KitData;

import java.io.Serializable;
import java.util.ArrayList;


@Getter
@Setter
public class User implements Serializable, Identifiable<String> {

    private String uuid;
    private String nickName;

    private double vpln;

    private Double money;

    private Integer spentTime;
    private Integer progress;

    private JobType jobType;

    private long dailyFreeVpln;

    private ArrayList<KitData> kits;
    private ArrayList<Backup> backups;

    private int dead;
    private int kill;
    private int breakBlock;
    private int placeBlock;


    public User(Player player) {
        this.uuid = String.valueOf(player.getUniqueId());
        this.nickName = player.getName();

        this.vpln = 0.0;

        this.money = 0.0;

        this.spentTime = 0;
        this.progress = 0;

        this.jobType = JobType.CLEAR;

        this.dailyFreeVpln = 0;

        this.dead = 0;
        this.kill = 0;
        this.breakBlock = 0;
        this.placeBlock = 0;

        this.kits = new ArrayList<>();

        this.backups = new ArrayList<>();
    }


    @Override
    public String getId() {
        return this.uuid;
    }

    public void addOnlineTime(int onlineTime) {
        this.spentTime += onlineTime;
    }

    public void addMoney(double money) {
        this.money += money;
    }


    public void removeMoney(double money) {
        this.money -= money;
    }

    public void addProgress(int progress) {
        this.progress += progress;
    }

}
