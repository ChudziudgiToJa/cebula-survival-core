package pl.cebula.smp.feature.pet.object;

import lombok.Getter;
import org.bukkit.potion.PotionEffectType;

import java.io.Serializable;

@Getter
public class PetData implements Serializable {

    private final String name;
    private final String SkinValue;
    private final String potionEffect;
    private final String stringArrayList;

    public PetData(String name, String skinValue, String potionEffect, String stringArrayList) {
        this.name = name;
        SkinValue = skinValue;
        this.potionEffect = potionEffect;
        this.stringArrayList = stringArrayList;
    }
}
