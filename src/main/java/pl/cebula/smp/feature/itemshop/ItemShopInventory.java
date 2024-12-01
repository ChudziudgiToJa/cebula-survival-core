package pl.cebula.smp.feature.itemshop;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.configuration.implementation.PluginConfiguration;
import pl.cebula.smp.feature.kit.Kit;
import pl.cebula.smp.feature.shop.object.Shop;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.ItemStackBuilder;
import pl.cebula.smp.util.MessageUtil;
import pl.cebula.smp.util.SimpleInventory;

import java.util.Arrays;

public class ItemShopInventory {
    private final SurvivalPlugin survivalPlugin;
    private final PluginConfiguration pluginConfiguration;
    private final ItemShopManager itemShopManager;
    private final UserService userService;

    public ItemShopInventory(SurvivalPlugin survivalPlugin, PluginConfiguration pluginConfiguration, ItemShopManager itemShopManager, UserService userService) {
        this.survivalPlugin = survivalPlugin;
        this.pluginConfiguration = pluginConfiguration;
        this.itemShopManager = itemShopManager;
        this.userService = userService;
    }

    public void show(final Player player) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 6, MessageUtil.smallTextToColor("&6&lITEMSHOP"));
        Inventory inventory = simpleInventory.getInventory();


        Integer[] glassBlueSlots = new Integer[]{
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 50, 52, 0, 8, 45, 53, 49
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemStackBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .toItemStack()));

        for (ItemShop itemShop : this.pluginConfiguration.itemShopSettings.shops) {
            inventory.addItem(itemShop.getItemStack());
        }


        simpleInventory.click(event -> {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;

            for (ItemShop itemShop : this.pluginConfiguration.itemShopSettings.shops) {
                if (event.getCurrentItem() != null && event.getCurrentItem().equals(itemShop.getItemStack())) {
                    showAreYouSure(player, itemShop);
                    player.playSound(player, Sound.BLOCK_LEVER_CLICK, 5, 5);
                    break;
                }
            }
        });

        player.openInventory(inventory);
    }


    public void showAreYouSure(final Player player, final ItemShop shop) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 3, MessageUtil.smallTextToColor("&6&lITEMSHOP &7czy napewno?"));
        Inventory inventory = simpleInventory.getInventory();
        User user = this.userService.findUserByNickName(player.getName());

        Integer[] glassGreenSlots = new Integer[]{
                1,2,0,
                9,10,11,
                18,19,20
        };

        Integer[] glassRedSlots = new Integer[]{
                6,7,8,
                15,16,17,
                24,25,26
        };

        Arrays.stream(glassGreenSlots).forEach(slot -> inventory.setItem(slot,
                new ItemStackBuilder(Material.LIME_STAINED_GLASS_PANE)
                        .setName("&a&lTAK")
                        .toItemStack()));

        Arrays.stream(glassRedSlots).forEach(slot -> inventory.setItem(slot,
                new ItemStackBuilder(Material.RED_STAINED_GLASS_PANE)
                        .setName("&4&lNIE")
                        .toItemStack()));

        inventory.setItem(13, shop.getItemStack());


        simpleInventory.click(event -> {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;

            if (Arrays.asList(glassRedSlots).contains(event.getSlot())) {
                show(player);
                player.playSound(player, Sound.BLOCK_BARREL_CLOSE, 5 ,5);
                return;
            }

            if (Arrays.asList(glassGreenSlots).contains(event.getSlot())) {
                this.itemShopManager.onBuyItem(user, shop.getPrice(), shop);
                player.closeInventory();
            }
        });

        player.openInventory(inventory);
    }
}
