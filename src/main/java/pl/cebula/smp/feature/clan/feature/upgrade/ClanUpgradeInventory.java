package pl.cebula.smp.feature.clan.feature.upgrade;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.util.DecimalUtil;
import pl.cebula.smp.util.ItemBuilder;
import pl.cebula.smp.util.MessageUtil;
import pl.cebula.smp.util.SimpleInventory;

import java.util.Arrays;

public class ClanUpgradeInventory {

    public ClanUpgradeInventory(SurvivalPlugin survivalPlugin) {
        this.survivalPlugin = survivalPlugin;
    }

    private final SurvivalPlugin survivalPlugin;

    public void show(final Player player, final Clan clan) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 54, MessageUtil.smallText("&7aktualny status pvp: " + (clan.isPvp() ? "&awłączony" : "&cWyczłaczony")));
        Inventory inventory = simpleInventory.getInventory();

        Integer[] glassBlueSlots = new Integer[]{
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 50, 52, 0, 8, 45, 53, 49
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .build()));



        inventory.setItem(20, new ItemBuilder(Material.ALLIUM)
                        .setName("&a&lulepszenie miejsc w klanie")
                        .addLore("","&8| &faktualne miejsca: &7" + clan.getMemberArrayList().size() + "&8/&a" + clan.getClanMemberLimitType().getSlotLimit(),
                                "",
                                "&8| &fCena powiększenia: " + DecimalUtil.getFormat(clan.getClanMemberLimitType().getNext().getPrice()) + " &amonet.",
                                "",
                                "&aKliknij aby kupić."
                        )
                .build());

        simpleInventory.click(event -> {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;


        });
        player.openInventory(inventory);
    }

}
