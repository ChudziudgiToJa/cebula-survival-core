package pl.cebula.smp.feature.user;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.entity.Player;
import pl.cebula.smp.database.repository.Identifiable;
import pl.cebula.smp.feature.job.JobType;

import java.io.Serializable;


@Getter
@Setter
public class User implements Serializable, Identifiable<String> {

    private String uuid;
    private String nickName;
    private Double money;

    private Integer spentTime;
    private Integer progress;

    private JobType jobType;

    public User(Player player) {
        this.uuid = String.valueOf(player.getUniqueId());
        this.nickName = player.getName();

        this.money = 0.0;

        this.spentTime = 0;
        this.progress = 0;

        this.jobType = JobType.CLEAR;
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
