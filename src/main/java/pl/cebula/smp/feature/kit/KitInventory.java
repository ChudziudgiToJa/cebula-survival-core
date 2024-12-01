package pl.cebula.smp.feature.kit;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.configuration.implementation.PluginConfiguration;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.*;

import java.util.Arrays;
import java.util.HashMap;

public class KitInventory {

    private final SurvivalPlugin survivalPlugin;
    private final PluginConfiguration pluginConfiguration;
    private final UserService userService;

    public KitInventory(SurvivalPlugin survivalPlugin, PluginConfiguration pluginConfiguration, UserService userService) {
        this.survivalPlugin = survivalPlugin;
        this.pluginConfiguration = pluginConfiguration;
        this.userService = userService;
    }


    public void show(final Player player) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 6, MessageUtil.smallTextToColor("&fzestawy:"));
        Inventory inventory = simpleInventory.getInventory();

        Integer[] glassBlueSlots = new Integer[]{
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 50, 52, 0, 8, 45, 53, 49
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemStackBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .toItemStack()));

        for (Kit kit : this.pluginConfiguration.kitSettings.kitList) {
            inventory.addItem(kit.getIcon());
        }


        simpleInventory.click(event -> {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;

            for (Kit kit : this.pluginConfiguration.kitSettings.kitList) {
                if (event.getCurrentItem() != null && event.getCurrentItem().isSimilar(kit.getIcon())) {
                    this.showPrewiew(player, kit);
                    player.playSound(player, Sound.BLOCK_LEVER_CLICK, 5, 5);
                    break;
                }
            }
        });

        player.openInventory(inventory);
    }

    public void showPrewiew(final Player player, final Kit kit) {
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
                this.show(player);
                player.playSound(player, Sound.BLOCK_BAMBOO_WOOD_DOOR_CLOSE, 5, 5);
            }

            if (event.getSlot() == 52) {

                if (!player.hasPermission(kit.getPermission())) {
                    MessageUtil.sendTitle(player, "", "&cNie posiadasz wymaganej rangi", 20, 50, 20);
                    player.closeInventory();
                    return;
                }

                for (KitData kitData : user.getKits()) {
                    if (kitData.getName().equals(kit.getName())) {
                        if (kitData.getTime() > System.currentTimeMillis()) {
                            MessageUtil.sendTitle(player, "", "&cmożesz odebrać za: " + DurationUtil.getTimeFormat(kitData.getTime() - System.currentTimeMillis()), 20, 50, 20);
                            player.closeInventory();
                            player.playSound(player, Sound.ENTITY_BEE_HURT, 5, 5);
                            return;
                        }
                        user.getKits().remove(kitData);
                        break;
                    }
                }
                user.getKits().add(new KitData(kit.getName(), kit.getCoolDownTime() + System.currentTimeMillis()));
                for (ItemStack itemStack : kit.getItemStackArrayList()) {
                    HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(itemStack);
                    leftover.values().forEach(remaining ->
                            player.getWorld().dropItemNaturally(player.getLocation(), remaining)
                    );
                }
                MessageUtil.sendTitle(player, "", "&aodebrano zestaw", 20, 50, 20);
                player.closeInventory();
            }
        });
        player.openInventory(inventory);
    }
}
