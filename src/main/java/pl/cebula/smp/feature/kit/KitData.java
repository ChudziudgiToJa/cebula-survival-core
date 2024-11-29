package pl.cebula.smp.feature.kit;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
public class KitData implements Serializable {
    private final String kit;
    private final long time;


    public KitData(String kit, long time) {
        this.kit = kit;
        this.time = time;
    }
}
