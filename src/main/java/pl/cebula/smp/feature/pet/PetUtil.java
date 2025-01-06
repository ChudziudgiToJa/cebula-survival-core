package pl.cebula.smp.feature.pet;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.cebula.smp.configuration.implementation.PetConfiguration;
import pl.cebula.smp.feature.pet.object.PetData;
import pl.cebula.smp.util.ItemBuilder;
import pl.cebula.smp.util.MessageUtil;

public class PetUtil {

    public static PetData getPetFromItem(final PetConfiguration petconfiguration, String name) {
        return petconfiguration.petDataDataList.stream()
                .filter(petData -> name.equals(MessageUtil.smallTextToColor(petData.getName())))
                .findFirst()
                .orElse(null);
    }


    public static ItemStack createItemStackPet(final PetData petData) {
        return new ItemBuilder(Material.PLAYER_HEAD)
                .setHeadOwner(petData.getSkinValue())
                .setName(petData.getName())
                .addLore("")
                .addLore(String.valueOf(petData.getStringArrayList()))
                .addLore("")
                .addLore("&akliknij aby założyć.")
                .build();
    }
}
