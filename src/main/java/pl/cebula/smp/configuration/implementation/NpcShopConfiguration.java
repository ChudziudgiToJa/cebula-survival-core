package pl.cebula.smp.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;
import pl.cebula.smp.feature.shop.object.ItemToInteract;
import pl.cebula.smp.feature.shop.object.Shop;
import pl.cebula.smp.util.ItemBuilder;

import java.util.List;

public class NpcShopConfiguration extends OkaeriConfig {
    public List<Shop> shops = List.of(
            new Shop(
                    "&0&lGornik",
                    9,
                    List.of(
                            new ItemToInteract(
                                    new ItemBuilder(Material.CHARCOAL).build(),
                                    10,
                                    4.0,
                                    2.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.COAL).build(),
                                    11,
                                    5.0,
                                    3.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.COPPER_INGOT).build(),
                                    12,
                                    7.0,
                                    5.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.IRON_INGOT).build(),
                                    13,
                                    12.0,
                                    7.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.GOLD_INGOT).build(),
                                    14,
                                    55.0,
                                    15.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.GOLD_INGOT).build(),
                                    15,
                                    15.0,
                                    6.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.EMERALD).build(),
                                    16,
                                    40.0,
                                    10.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.DIAMOND).build(),
                                    19,
                                    40.0,
                                    10.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.NETHERITE_SCRAP).build(),
                                    20,
                                    250.0,
                                    130.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.POINTED_DRIPSTONE).build(),
                                    21,
                                    8.0,
                                    3.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.FLINT).build(),
                                    22,
                                    3.0,
                                    1.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.AMETHYST_SHARD).build(),
                                    23,
                                    4.0,
                                    1.0
                            )
                    )
            ),
            new Shop(
                    "&0&lFarmer",
                    10,
                    List.of(
                            new ItemToInteract(
                                    new ItemBuilder(Material.WHEAT).build(),
                                    10,
                                    4.0,
                                    2.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.POTATO).build(),
                                    11,
                                    6.0,
                                    3.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.CARROT).build(),
                                    12,
                                    5.0,
                                    3.5
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.BEETROOT).build(),
                                    13,
                                    6.0,
                                    4.5
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.PUMPKIN).build(),
                                    14,
                                    5.0,
                                    2.5
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.MELON_SLICE).build(),
                                    15,
                                    2.0,
                                    1.5
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.GLOW_BERRIES).build(),
                                    16,
                                    7.0,
                                    4.5
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.SWEET_BERRIES).build(),
                                    19,
                                    5.0,
                                    3.5
                            )
                    )
            ),
            new Shop(
                    "&0&lZabójca",
                    15,
                    List.of(
                            new ItemToInteract(
                                    new ItemBuilder(Material.STRING).build(),
                                    10,
                                    5.0,
                                    2.5
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.SPIDER_EYE).build(),
                                    11,
                                    8.0,
                                    4.5
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.ENDER_PEARL).build(),
                                    12,
                                    150.0,
                                    70.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.ROTTEN_FLESH).build(),
                                    13,
                                    3.0,
                                    1.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.FEATHER).build(),
                                    14,
                                    2.5,
                                    1.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.BONE).build(),
                                    15,
                                    3.5,
                                    1.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.FERMENTED_SPIDER_EYE).build(),
                                    16,
                                    10.5,
                                    3.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.GLASS_BOTTLE).build(),
                                    19,
                                    3.0,
                                    2.0
                            )
                    )
            ),
            new Shop(
                    "&0&lWędkarz",
                    8,
                    List.of(
                            new ItemToInteract(
                                    new ItemBuilder(Material.COD).build(),
                                    10,
                                    13.0,
                                    9.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.SALMON).build(),
                                    11,
                                    15.0,
                                    10.5
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.TROPICAL_FISH).build(),
                                    12,
                                    11.0,
                                    7.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.PUFFERFISH).build(),
                                    13,
                                    15.0,
                                    10.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.NAME_TAG).build(),
                                    14,
                                    90.5,
                                    30.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.SADDLE).build(),
                                    15,
                                    60.5,
                                    25.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.LILY_PAD).build(),
                                    16,
                                    10.5,
                                    4.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.INK_SAC).build(),
                                    19,
                                    13.0,
                                    5.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.BOWL).build(),
                                    20,
                                    10.0,
                                    5.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.TRIPWIRE_HOOK).build(),
                                    21,
                                    43.0,
                                    15.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.LEATHER).build(),
                                    22,
                                    8.0,
                                    3.0
                            )
                    )
            )
    );
}
