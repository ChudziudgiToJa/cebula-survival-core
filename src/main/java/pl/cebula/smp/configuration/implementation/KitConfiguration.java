package pl.cebula.smp.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import pl.cebula.smp.feature.kit.Kit;
import pl.cebula.smp.util.ItemBuilder;

import java.util.List;

public class KitConfiguration extends OkaeriConfig {

    @Comment("## czas podajemy w milisekundach bo to liczy na czasie aktualnym świata")
    public List<Kit> kitList = List.of(
            new Kit(
                    "gracz",
                    900000,
                    new ItemBuilder(Material.LEATHER_HELMET)
                            .setName("⚙")
                            .addLore(
                                    "",
                                    "&aKliknij aby otworzyć podgląd."
                            )
                            .build(),
                    "cebulasmp.kit.gracz",
                    List.of(
                            new ItemBuilder(Material.STONE_AXE).build(),
                            new ItemBuilder(Material.STONE_PICKAXE).build(),
                            new ItemBuilder(Material.LEATHER_CHESTPLATE).build(),
                            new ItemBuilder(Material.LEATHER_LEGGINGS).build(),
                            new ItemBuilder(Material.COOKED_BEEF, 16).build()
                    )
            ),
            new Kit(
                    "vip",
                    86400000,
                    new ItemBuilder(Material.IRON_HELMET)
                            .setName("ꑅ")
                            .addLore(
                                    "",
                                    "&aKliknij aby otworzyć podgląd."
                            )
                            .build(),
                    "cebulasmp.kit.vip",
                    List.of(
                            new ItemBuilder(Material.IRON_AXE)
                                    .addEnchant(Enchantment.UNBREAKING, 1)
                                    .build(),
                            new ItemBuilder(Material.IRON_PICKAXE)
                                    .addEnchant(Enchantment.UNBREAKING, 2)
                                    .build(),
                            new ItemBuilder(Material.SHEARS)
                                    .addEnchant(Enchantment.UNBREAKING, 2)
                                    .build(),
                            new ItemBuilder(Material.COOKED_BEEF, 32).build(),
                            new ItemBuilder(Material.GOLDEN_APPLE, 2).build(),
                            new ItemBuilder(Material.CHAINMAIL_CHESTPLATE)
                                    .build(),
                            new ItemBuilder(Material.CHAINMAIL_LEGGINGS)
                                    .build()

                    )
            ),
            new Kit(
                    "mvip",
                    86400000,
                    new ItemBuilder(Material.DIAMOND_HELMET)
                            .setName("ꑇ")
                            .addLore(
                                    "",
                                    "&aKliknij aby otworzyć podgląd."
                            )
                            .build(),
                    "cebulasmp.kit.mvip",
                    List.of(
                            new ItemBuilder(Material.IRON_AXE)
                                    .addEnchant(Enchantment.UNBREAKING, 1)
                                    .build(),
                            new ItemBuilder(Material.IRON_PICKAXE)
                                    .addEnchant(Enchantment.UNBREAKING, 2)
                                    .build(),
                            new ItemBuilder(Material.SHEARS)
                                    .addEnchant(Enchantment.UNBREAKING, 2)
                                    .build(),
                            new ItemBuilder(Material.COOKED_BEEF, 32).build(),
                            new ItemBuilder(Material.GOLDEN_APPLE, 2).build(),
                            new ItemBuilder(Material.IRON_CHESTPLATE)
                                    .addEnchant(Enchantment.PROTECTION, 1)
                                    .build(),
                            new ItemBuilder(Material.IRON_LEGGINGS)
                                    .addEnchant(Enchantment.PROTECTION, 1)
                                    .build()

                    )
            ),
            new Kit(
                    "cebulak",
                    86400000,
                    new ItemBuilder(Material.NETHERITE_HELMET)
                            .setName("ꑍ")
                            .addLore(
                                    "",
                                    "&aKliknij aby otworzyć podgląd."
                            )
                            .build(),
                    "cebulasmp.kit.cebulak",
                    List.of(
                            new ItemBuilder(Material.IRON_AXE)
                                    .addEnchant(Enchantment.EFFICIENCY, 1)
                                    .addEnchant(Enchantment.UNBREAKING, 2)
                                    .build(),
                            new ItemBuilder(Material.DIAMOND_PICKAXE)
                                    .addEnchant(Enchantment.UNBREAKING, 3)
                                    .build(),
                            new ItemBuilder(Material.SHEARS)
                                    .addEnchant(Enchantment.UNBREAKING, 3)
                                    .build(),
                            new ItemBuilder(Material.COOKED_BEEF, 32).build(),
                            new ItemBuilder(Material.GOLDEN_APPLE, 3).build(),
                            new ItemBuilder(Material.IRON_CHESTPLATE)
                                    .addEnchant(Enchantment.PROTECTION, 2)
                                    .build(),
                            new ItemBuilder(Material.IRON_LEGGINGS)
                                    .addEnchant(Enchantment.PROTECTION, 2)
                                    .build()
                    )
            )
    );
}
