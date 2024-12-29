package pl.cebula.smp.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;
import pl.cebula.smp.feature.npcshop.object.ItemToInteract;
import pl.cebula.smp.feature.npcshop.object.Shop;
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
                                    2.0, // Sprzedaż
                                    1.0  // Za  kup
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.COAL).build(),
                                    11,
                                    3.0,
                                    1.5
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.COPPER_INGOT).build(),
                                    12,
                                    6.0,
                                    3.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.IRON_INGOT).build(),
                                    13,
                                    12.0,
                                    7.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.LAPIS_LAZULI).build(),
                                    14,
                                    15.0,
                                    8.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.GOLD_INGOT).build(),
                                    15,
                                    18.0,
                                    10.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.EMERALD).build(),
                                    16,
                                    50.0,
                                    30.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.POINTED_DRIPSTONE).build(),
                                    19,
                                    4.0,
                                    2.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.FLINT).build(),
                                    20,
                                    1.5,
                                    0.8
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.AMETHYST_SHARD).build(),
                                    21,
                                    10.0,
                                    5.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.DIAMOND).build(),
                                    22,
                                    100.0,
                                    60.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.NETHERITE_SCRAP).build(),
                                    23,
                                    500.0,
                                    300.0
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
                                    3.0, // Sprzedaż
                                    1.5  // Zakup
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.POTATO).build(),
                                    11,
                                    4.0,
                                    2.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.CARROT).build(),
                                    12,
                                    4.5,
                                    2.5
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.BEETROOT).build(),
                                    13,
                                    5.0,
                                    2.5
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.PUMPKIN).build(),
                                    14,
                                    6.0,
                                    3.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.MELON_SLICE).build(),
                                    15,
                                    1.5,
                                    0.8
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.GLOW_BERRIES).build(),
                                    16,
                                    10.0,
                                    6.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.SWEET_BERRIES).build(),
                                    19,
                                    6.0,
                                    4.0
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
                                    4.0, // Sprzedaż
                                    2.0  // Zakup
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.SPIDER_EYE).build(),
                                    11,
                                    6.0,
                                    3.5
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.ENDER_PEARL).build(),
                                    12,
                                    100.0,
                                    50.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.ROTTEN_FLESH).build(),
                                    13,
                                    2.0,
                                    0.5
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.FEATHER).build(),
                                    14,
                                    3.0,
                                    1.5
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.BONE).build(),
                                    15,
                                    3.5,
                                    2.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.FERMENTED_SPIDER_EYE).build(),
                                    16,
                                    12.0,
                                    6.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.GLASS_BOTTLE).build(),
                                    19,
                                    2.5,
                                    1.5
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
                                    12.0, // Sprzedaż
                                    8.0  // Zakup
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.SALMON).build(),
                                    11,
                                    14.0,
                                    9.5
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.TROPICAL_FISH).build(),
                                    12,
                                    10.0,
                                    6.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.PUFFERFISH).build(),
                                    13,
                                    18.0,
                                    12.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.NAME_TAG).build(),
                                    14,
                                    75.0,
                                    35.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.SADDLE).build(),
                                    15,
                                    60.0,
                                    30.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.LILY_PAD).build(),
                                    16,
                                    8.0,
                                    4.5
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.INK_SAC).build(),
                                    19,
                                    10.0,
                                    4.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.BOWL).build(),
                                    20,
                                    5.0,
                                    2.5
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.TRIPWIRE_HOOK).build(),
                                    21,
                                    40.0,
                                    15.0
                            ),
                            new ItemToInteract(
                                    new ItemBuilder(Material.LEATHER).build(),
                                    22,
                                    7.0,
                                    3.0
                            )
                    )
            )
    );
}
