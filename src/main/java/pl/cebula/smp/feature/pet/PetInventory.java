package pl.cebula.smp.feature.pet;

import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.pet.object.Pet;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.util.ItemBuilder;
import pl.cebula.smp.util.MessageUtil;
import pl.cebula.smp.util.SimpleInventory;

import java.util.Arrays;

public class PetInventory {

    public void showPetInventory(SurvivalPlugin survivalPlugin, Player player, User user) {
        SimpleInventory simpleInventory = new SimpleInventory(survivalPlugin, 9 * 6, "&3&lPety: " + user.getNickName());
        Inventory inventory = simpleInventory.getInventory();

        Integer[] glassBlueSlots = new Integer[]{
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 50, 52, 0, 8, 45, 53
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .build()));

        inventory.setItem(49, new ItemBuilder(Material.BARRIER).setName("&ccofnij").build());


        user.getPetDataArrayList().forEach(pet -> {
            inventory.addItem(PetUtil.createItemStackPet(pet.getPetData()));
        });

        simpleInventory.click(event -> {
            ItemStack clickedItem = event.getCurrentItem();
            event.setCancelled(true);

            if (clickedItem != null && clickedItem.hasItemMeta()) {

                if (event.getSlot() == 49) {
                    player.closeInventory();
                    player.playSound(player, Sound.BLOCK_CHEST_CLOSE, 5, 5);
                    return;
                }

                String itemName = clickedItem.getItemMeta().getDisplayName();
                for (Pet Pet : user.getPetDataArrayList())
                    if (itemName.equals(PetUtil.createItemStackPet(Pet.getPetData()).getItemMeta().getDisplayName())) {
                        if (player.getInventory().firstEmpty() == -1) {
                            MessageUtil.sendTitle(player, "&c", "&cNie masz miejsca w ekwipunku, aby ściągnąć zwierzaka!", 20, 50, 20);
                            player.closeInventory();
                            return;
                        }
                        DHAPI.removeHologram(Pet.getUuid().toString());
                        user.getPetDataArrayList().remove(Pet);
                        player.getInventory().addItem(PetUtil.createItemStackPet(Pet.getPetData()));
                        player.openInventory(inventory);
                        break;
                    }
            }
        });


        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 5, 5);
        player.openInventory(inventory);
    }
}
