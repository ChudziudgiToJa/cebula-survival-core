package pl.cebula.smp.feature.clan.feature.pvp;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.service.ClanService;
import pl.cebula.smp.util.ItemBuilder;
import pl.cebula.smp.util.MessageUtil;
import pl.cebula.smp.util.SimpleInventory;

import java.util.Arrays;

public class ClanPvpInventory {

    private final SurvivalPlugin survivalPlugin;

    public ClanPvpInventory(SurvivalPlugin survivalPlugin, ClanService clanService) {
        this.survivalPlugin = survivalPlugin;
    }

    public void showChangePvp(final Player player, final Clan clan) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 3, MessageUtil.smallText("&7aktualny status pvp: " + (clan.isPvp() ? "&awłączony" : "&cWyczłaczony")));
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

        Arrays.stream(glassRedSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                        .setName("&4&lNIE")
                        .build()));


        simpleInventory.click(event -> {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;

            if (Arrays.asList(glassRedSlots).contains(event.getSlot())) {
                clan.setPvp(true);
                clan.getMemberArrayList().forEach(s -> {
                    Player member = Bukkit.getPlayer(s);
                    if (member == null) return;
                    MessageUtil.sendTitle(member, "", "&2Lider &azmienił status &cpvp&2 na &cwyłączony", 20, 50, 20);
                });
                MessageUtil.sendTitle(player, "", "&7zmieniono status pvp na &cwyłączony", 20, 50, 20);
                player.closeInventory();
                return;
            }

            if (Arrays.asList(glassGreenSlots).contains(event.getSlot())) {
                clan.setPvp(false);
                clan.getMemberArrayList().forEach(s -> {
                    Player member = Bukkit.getPlayer(s);
                    if (member == null) return;
                    MessageUtil.sendTitle(member, "", "&2Lider &azmienił status &cpvp&2 na &awyczłaczony", 20, 50, 20);
                });
                MessageUtil.sendTitle(player, "", "&7zmieniono status pvp na &awyczłaczony", 20, 50, 20);
                player.closeInventory();
            }
        });
        player.openInventory(inventory);
    }
}
