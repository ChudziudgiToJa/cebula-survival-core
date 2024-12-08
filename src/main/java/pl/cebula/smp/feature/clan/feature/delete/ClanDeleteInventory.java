package pl.cebula.smp.feature.clan.feature.delete;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.service.ClanService;
import pl.cebula.smp.util.ItemStackBuilder;
import pl.cebula.smp.util.MessageUtil;
import pl.cebula.smp.util.SimpleInventory;

import java.util.Arrays;

public class ClanDeleteInventory {

    private final SurvivalPlugin survivalPlugin;
    private final ClanService clanService;

    public ClanDeleteInventory(SurvivalPlugin survivalPlugin, ClanService clanService) {
        this.survivalPlugin = survivalPlugin;
        this.clanService = clanService;
    }

    public void showDeleteInventory(final Player player, final Clan clan) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 3, MessageUtil.smallTextToColor("&6&lKLAN &7czy na pewno?"));
        Inventory inventory = simpleInventory.getInventory();

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


        simpleInventory.click(event -> {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;

            if (Arrays.asList(glassRedSlots).contains(event.getSlot())) {
                player.closeInventory();
                player.playSound(player, Sound.BLOCK_BARREL_CLOSE, 5 ,5);
                return;
            }

            if (Arrays.asList(glassGreenSlots).contains(event.getSlot())) {
                this.clanService.removeClan(clan);
                player.closeInventory();
                MessageUtil.sendTitle(player, "", "&aUsunięto klan.", 20,50,20);
            }
        });

        player.openInventory(inventory);
    }
}
