package pl.cebula.smp.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;
import pl.cebula.smp.feature.shop.object.ItemToInteract;
import pl.cebula.smp.feature.shop.object.Shop;
import pl.cebula.smp.util.ItemBuilder;

import java.util.List;

public class PluginConfiguration extends OkaeriConfig {

    public NpcShop npcShop = new NpcShop();

    public static class NpcShop extends OkaeriConfig {
        public List<Shop> shops = List.of(
                new Shop(
                        "&0&lGornik",
                        9,
                        List.of(
                                new ItemToInteract(
                                        new ItemBuilder(Material.COAL).build(),
                                        10,
                                        5.0,
                                        3.0
                                ),
                                new ItemToInteract(
                                        new ItemBuilder(Material.COPPER_INGOT).build(),
                                        11,
                                        7.0,
                                        5.0
                                ),
                                new ItemToInteract(
                                        new ItemBuilder(Material.IRON_INGOT).build(),
                                        12,
                                        12.0,
                                        7.0
                                )
                        )

                )
        );
    }
}
