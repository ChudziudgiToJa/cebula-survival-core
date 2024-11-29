package pl.cebula.smp.feature.kit;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.*;

import java.util.Arrays;
import java.util.HashMap;

public class KitPreviewInventory {

    private final UserService userService;
    private final SurvivalPlugin survivalPlugin;

    public KitPreviewInventory(UserService userService, SurvivalPlugin survivalPlugin) {
        this.userService = userService;
        this.survivalPlugin = survivalPlugin;
    }


    public void show(final Player player, final Kit kit) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 6, MessageUtil.smallTextToColor("&fpodgląd zestawu: " + kit.getName()));
        Inventory inventory = simpleInventory.getInventory();
        User user = this.userService.findUserByNickName(player.getName());

        Integer[] glassBlueSlots = new Integer[]{
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 50, 52, 0, 8, 45, 53, 49
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemStackBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .toItemStack()));

        inventory.setItem(51,
                new ItemBuilder(Material.RED_DYE)
                        .setTitle("kliknij aby cofnąć")
                        .build()
        );

        inventory.setItem(52,
                new ItemBuilder(Material.GREEN_DYE)
                        .setTitle("kliknij aby odebrac zestaw")
                        .build()
        );

        for (ItemStack itemStack : kit.getItemStackArrayList()) {
            inventory.addItem(itemStack);
        }


        simpleInventory.click(event -> {
            event.setCancelled(true);

            if (event.getSlot() == 51) {
                player.closeInventory();
                player.playSound(player, Sound.BLOCK_BAMBOO_WOOD_DOOR_CLOSE, 5, 5);
            }

            if (event.getSlot() == 52) {

                for (KitData kitData : user.getKits()) {
                    if (kitData.getKit().equals(kit.getName())) {
                        if (kitData.getTime() <= System.currentTimeMillis()) {
                            MessageUtil.sendTitle(player, "", "&cmożesz odebrać za: " + DurationUtil.getTimeFormat(kitData.getTime()- System.currentTimeMillis()), 20, 50, 20);
                            player.closeInventory();
                            player.playSound(player, Sound.ENTITY_BEE_HURT, 5, 5);
                            return;
                        }
                    }
                    user.getKits().remove(kitData);
                }

                for (ItemStack itemStack : kit.getItemStackArrayList()) {
                    HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(itemStack);
                    leftover.values().forEach(remaining ->
                            player.getWorld().dropItemNaturally(player.getLocation(), remaining)
                    );
                }
                MessageUtil.sendTitle(player, "", "&aodebrano zestaw", 20, 50, 20);
                player.closeInventory();
                user.getKits().add(new KitData(kit.getName(), System.currentTimeMillis() + kit.getCoolDownTime()));
            }
        });
        player.openInventory(inventory);
    }
}
