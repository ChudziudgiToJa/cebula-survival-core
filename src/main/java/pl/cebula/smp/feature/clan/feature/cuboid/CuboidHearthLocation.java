package pl.cebula.smp.feature.clan.feature.cuboid;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class CuboidHearthLocation implements Serializable {
    private double x;
    private double y;
    private double z;

    public CuboidHearthLocation(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
