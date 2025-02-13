package pl.cebula.smp.feature.disco;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.util.ItemBuilder;
import pl.cebula.smp.util.MessageUtil;
import pl.cebula.smp.util.SimpleInventory;

public class DiscoInventory {

    private final SurvivalPlugin survivalPlugin;

    public DiscoInventory(SurvivalPlugin survivalPlugin) {
        this.survivalPlugin = survivalPlugin;
    }

    public void show(final Player player, Disco disco) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, InventoryType.HOPPER, MessageUtil.smallText("&fDisco:"));
        Inventory inventory = simpleInventory.getInventory();

        inventory.setItem(0, new ItemBuilder(Material.EMERALD_BLOCK)
                .setName("&ftryb: &aturbo")
                .setLore("", "", "&akliknij aby zalożyć.")
                .build());

        inventory.setItem(1, new ItemBuilder(Material.REDSTONE)
                .setName("&ftryb: &apłynny")
                .setLore("", "&akliknij aby zalożyć.")
                .build());

        inventory.setItem(2, new ItemBuilder(Material.DIAMOND_BLOCK)
                .setName("&ftryb: &alosowy")
                .setLore("", "&akliknij aby zalożyć.")
                .build());

        inventory.setItem(4, new ItemBuilder(Material.BARRIER)
                .setName("&ckliknij aby wyłączyć.")
                .build());


        simpleInventory.click(event -> {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;

            if (!player.hasPermission("cebulasmp.disco")) {
                MessageUtil.sendTitle(player, "&a", "&cnie posiadasz wykupionej disco zbroi &8/itemshop", 20, 50, 20);
                return;
            }

            switch (event.getSlot()) {
                case 0 -> {
                    player.closeInventory();
                    disco.setDiscoType(DiscoType.TURBO);
                    MessageUtil.sendTitle(player, "&a", "&aUstawiono tryb: &flosowy", 20, 50, 20);
                }
                case 1 -> {
                    player.closeInventory();
                    disco.setDiscoType(DiscoType.SMOOTH);
                    MessageUtil.sendTitle(player, "&a", "&aUstawiono tryb: &fpłynny", 20, 50, 20);
                }
                case 2 -> {
                    player.closeInventory();
                    disco.setDiscoType(DiscoType.RANDOM);
                    MessageUtil.sendTitle(player, "&a", "&aUstawiono tryb: &flosowy", 20, 50, 20);
                }
                case 4 -> {
                    player.closeInventory();
                    disco.setDiscoType(DiscoType.CLEAR);
                    MessageUtil.sendTitle(player, "&a", "&cwyłączono disco", 20, 50, 20);
                }
            }
        });
        player.openInventory(inventory);
    }
}
