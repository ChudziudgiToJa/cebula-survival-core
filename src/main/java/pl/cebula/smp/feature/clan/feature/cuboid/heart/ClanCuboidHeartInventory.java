package pl.cebula.smp.feature.clan.feature.cuboid.heart;

import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.feature.armor.ClanArmorHandler;
import pl.cebula.smp.feature.clan.feature.cuboid.bossbar.ClanCuboidBossBarManager;
import pl.cebula.smp.feature.clan.feature.cuboid.visual.ClanCuboidBorderPacketHandler;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.util.ItemBuilder;
import pl.cebula.smp.util.MessageUtil;
import pl.cebula.smp.util.SimpleInventory;

import java.util.Arrays;

public class ClanCuboidHeartInventory {

    private final SurvivalPlugin survivalPlugin;

    public ClanCuboidHeartInventory(SurvivalPlugin survivalPlugin) {
        this.survivalPlugin = survivalPlugin;
    }

    public void showHealClanInventory(final Player player, final Clan clan, final User user) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 3, MessageUtil.smallText("&6&lULECZ KLAN &7czy na pewno?"));
        Inventory inventory = simpleInventory.getInventory();

        Integer[] glassGreenSlots = new Integer[]{
                1, 2, 0,
                9, 10, 11,
                18, 19, 20
        };

        Integer[] glassRedSlots = new Integer[]{
                6, 7, 8,
                15, 16, 17,
                24, 25, 26
        };

        Arrays.stream(glassGreenSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                        .setName("&a&lTAK")
                        .build()));

        inventory.setItem(13, new ItemBuilder(Material.GOLD_INGOT)
                        .setName("&fkoszt&8: &77000 &amonet.")
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
                player.playSound(player, Sound.BLOCK_BARREL_CLOSE, 5, 5);
                return;
            }

            if (Arrays.asList(glassGreenSlots).contains(event.getSlot())) {
                if (user.getMoney() >= 7000) {
                    MessageUtil.sendTitle(player, "", "&aserce klanu zostało naprawione.", 20,50,20);
                    clan.setCuboidHearthValue(200);
                    player.closeInventory();
                    return;
                } else {
                    MessageUtil.sendTitle(player, "", "&cNie stać cię!", 20,50,20);
                    player.closeInventory();
                    return;
                }
            }
        });
        player.openInventory(inventory);
    }
}
