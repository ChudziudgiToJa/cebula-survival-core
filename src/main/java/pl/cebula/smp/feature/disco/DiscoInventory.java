package pl.cebula.smp.feature.disco;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.kit.Kit;
import pl.cebula.smp.util.ItemBuilder;
import pl.cebula.smp.util.MessageUtil;
import pl.cebula.smp.util.SimpleInventory;

import java.util.Arrays;

public class DiscoInventory {

    private final SurvivalPlugin survivalPlugin;

    public DiscoInventory(SurvivalPlugin survivalPlugin) {
        this.survivalPlugin = survivalPlugin;
    }

    public void show(final Player player, Disco disco) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, InventoryType.HOPPER, MessageUtil.smallText("&fDisco:"));
        Inventory inventory = simpleInventory.getInventory();

        inventory.setItem(0, new ItemBuilder(Material.EMERALD_BLOCK)
                        .setName(" ")
                        .setLore("","&8| &ftryb: &alosowy","", "&akliknij aby zalożyć.")
                .build());

        inventory.setItem(1, new ItemBuilder(Material.REDSTONE)
                .setName(" ")
                .setLore("","&8| &ftryb: &apłynny","", "&akliknij aby zalożyć.")
                .build());

        inventory.setItem(4, new ItemBuilder(Material.BARRIER)
                .setName(" ")
                .setLore("", "", "&ckliknij aby wyłączyć.")
                .build());



        simpleInventory.click(event -> {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;

            switch (event.getSlot()) {
                case 0 -> {
                    if (!player.hasPermission("cebulasmp.disco")) {
                        MessageUtil.sendTitle(player, "&a", "&cnie posiadasz wykupionej disco zbroi &8/itemshop", 20,50,20);
                        return;
                    }
                    player.closeInventory();
                    disco.setDiscoType(DiscoType.TURBO);
                    MessageUtil.sendTitle(player, "&a", "&aUstawiono tryb: &flosowy", 20,50,20);
                }
                case 1 -> {
                    if (!player.hasPermission("cebulasmp.disco")) {
                        MessageUtil.sendTitle(player, "&a", "&cnie posiadasz wykupionej disco zbroi &8/itemshop", 20,50,20);
                        return;
                    }
                    player.closeInventory();
                    disco.setDiscoType(DiscoType.SMOOTH);
                    MessageUtil.sendTitle(player, "&a", "&aUstawiono tryb: &fpłynny", 20,50,20);
                }
                case 4 -> {
                    player.closeInventory();
                    disco.setDiscoType(DiscoType.CLEAR);
                    MessageUtil.sendTitle(player, "&a", "&cwyłączono disco", 20,50,20);
                }
            }
        });
        player.openInventory(inventory);
    }
}
