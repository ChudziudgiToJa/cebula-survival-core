package pl.cebula.smp.feature.statistic;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.ItemBuilder;
import pl.cebula.smp.util.MessageUtil;
import pl.cebula.smp.util.SimpleInventory;

import java.util.Arrays;

public class StatisticInventory {

    private final SurvivalPlugin survivalPlugin;
    private final UserService userService;

    public StatisticInventory(SurvivalPlugin survivalPlugin, UserService userService) {
        this.survivalPlugin = survivalPlugin;
        this.userService = userService;
    }

    public void showAreYouSure(final Player player) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 3, MessageUtil.smallText("&6&lSTATYSTYKI &7czy na pewno?"));
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
                new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                        .setName("&a&lTAK")
                        .build()));

        inventory.setItem(13, new ItemBuilder(Material.GOLD_INGOT)
                        .setName("&fcena resetu statystyk&7: &a" + 1000 + " &fmonet")
                .build()
        );

        Arrays.stream(glassRedSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                        .setName("&4&lNIE")
                        .build()));


        simpleInventory.click(event -> {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;

            if (Arrays.asList(glassRedSlots).contains(event.getSlot())) {
                player.closeInventory();
                player.playSound(player, Sound.BLOCK_BARREL_CLOSE, 5 ,5);
                return;
            }

            if (Arrays.asList(glassGreenSlots).contains(event.getSlot())) {

                if (user.getMoney() >= 1000) {
                    user.setMoney(user.getMoney() - 1000);
                    user.setBreakBlock(0);
                    user.setPlaceBlock(0);
                    user.setDead(0);
                    user.setKill(0);
                    player.closeInventory();
                    MessageUtil.sendTitle(player, "", "&atwoje statystyki zostały zresetowane.",20,50,20);
                    return;
                } else {
                    MessageUtil.sendTitle(player, "", "&cNie posiadasz tylu monet.",20,50,20);
                    player.closeInventory();
                }
            }
        });

        player.openInventory(inventory);
    }
}
