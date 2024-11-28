package pl.cebula.smp.feature.job;

import lombok.Getter;

@Getter
public enum JobType {
    KILLER("Zabójca"),
    MINER("Górnik"),
    LUMBERJACK("Drwal"),
    FARMER("Rolnik"),
    FISHER("Rybak"),
    CLEAR("Bezrobotny");

    private final String polishName;

    JobType(String polishName) {
        this.polishName = polishName;
    }
}
