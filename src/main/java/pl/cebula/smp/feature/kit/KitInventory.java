package pl.cebula.smp.feature.kit;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.configuration.implementation.PluginConfiguration;
import pl.cebula.smp.util.ItemStackBuilder;
import pl.cebula.smp.util.MessageUtil;
import pl.cebula.smp.util.SimpleInventory;

import java.util.Arrays;

public class KitInventory {

    private final SurvivalPlugin survivalPlugin;
    private final PluginConfiguration pluginConfiguration;
    private final KitPreviewInventory kitPreviewInventory;

    public KitInventory(SurvivalPlugin survivalPlugin, PluginConfiguration pluginConfiguration, KitPreviewInventory kitPreviewInventory) {
        this.survivalPlugin = survivalPlugin;
        this.pluginConfiguration = pluginConfiguration;
        this.kitPreviewInventory = kitPreviewInventory;
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
                    this.kitPreviewInventory.show(player, kit);
                    player.playSound(player, Sound.BLOCK_LEVER_CLICK, 5, 5);
                    break;
                }
            }
        });

        player.openInventory(inventory);
    }
}
