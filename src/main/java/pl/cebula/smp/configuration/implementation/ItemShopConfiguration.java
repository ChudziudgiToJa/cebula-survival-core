package pl.cebula.smp.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;
import pl.cebula.smp.feature.itemshop.ItemShop;
import pl.cebula.smp.util.ItemBuilder;

import java.util.List;

public class ItemShopConfiguration extends OkaeriConfig {
    public List<ItemShop> shops = List.of(
            new ItemShop(
                    new ItemBuilder(Material.IRON_HELMET)
                            .setName("ꑅ &8(&f30dni&8)")
                            .addLore(
                                    "",
                                    "&7Cena&8: &f9.99 &avpln",
                                    "",
                                    "&aKliknij aby kupić usługę."
                            )
                            .build(),
                    9.99,
                    List.of("lp user {PLAYER} parent addtemp vip 30d")
            ),
            new ItemShop(
                    new ItemBuilder(Material.DIAMOND_HELMET)
                            .setName("ꑇ &8(&f30dni&8)")
                            .addLore(
                                    "",
                                    "&7Cena&8: &f19.99 &avpln",
                                    "",
                                    "&aKliknij aby kupić usługę."
                            )
                            .build(),
                    19.99,
                    List.of("lp user {PLAYER} parent addtemp mvip 30d")
            ),
            new ItemShop(
                    new ItemBuilder(Material.NETHERITE_HELMET)
                            .setName("ꑍ &8(&f30dni&8)")
                            .addLore(
                                    "",
                                    "&7Cena&8: &f49.99 &avpln",
                                    "",
                                    "&aKliknij aby kupić usługę."
                            )
                            .build(),
                    49.99,
                    List.of("lp user {PLAYER} parent addtemp cebulak 30d")
            ),
            new ItemShop(
                    new ItemBuilder(Material.TRIAL_KEY)
                            .setName("&b&lMALY &7zestaw kluczy")
                            .addLore(
                                    "",
                                    "&8| &fposiada w sobie klucze do:",
                                    " &d2x wedrowca, &e1x smoka",
                                    "",
                                    "&7Cena&8: &f4.99 &avpln",
                                    "",
                                    "&aKliknij aby kupić usługę."
                            )
                            .build(),
                    4.99,
                    List.of(
                            "case klucz {PLAYER} wedrowca 2",
                            "case klucz {PLAYER} smoka 1"
                    )            ),
            new ItemShop(
                    new ItemBuilder(Material.TRIAL_KEY)
                            .setName("&b&lSREDNI &7zestaw kluczy")
                            .addLore(
                                    "",
                                    "&8| &fposiada w sobie klucze do:",
                                    " &d4x wedrowca, &e2x smoka, &c1x piekiel",
                                    "",
                                    "&7Cena&8: &f6.99 &avpln",
                                    "",
                                    "&aKliknij aby kupić usługę."
                            )
                            .build(),
                    6.99,
                    List.of(
                            "case klucz {PLAYER} wedrowca 4",
                            "case klucz {PLAYER} smoka 2",
                            "case klucz {PLAYER} piekiel 1"
                    )
            ),
            new ItemShop(
                    new ItemBuilder(Material.OMINOUS_TRIAL_KEY)
                            .setName("&b&lDUZY &7zestaw kluczy")
                            .addLore(
                                    "",
                                    "&8| &fposiada w sobie klucze do:",
                                    " &d10x wedrowca, &e5x smoka, &c3x piekieł",
                                    "",
                                    "&7Cena&8: &f14.99 &avpln",
                                    "",
                                    "&aKliknij aby kupić usługę."
                            )
                            .build(),
                    14.99,
                    List.of(
                            "case klucz {PLAYER} wedrowca 10",
                            "case klucz {PLAYER} smoka 5",
                            "case klucz {PLAYER} piekiel 3"
                    )
            )
            ,
            new ItemShop(
                    new ItemBuilder(Material.GOLD_NUGGET)
                            .setName("&6&lDOLADOWANIE MONET &f1tyś")
                            .addLore(
                                    "",
                                    "&7Cena&8: &f2.00 &avpln",
                                    "",
                                    "&aKliknij aby kupić usługę."
                            )
                            .build(),
                    2.00,
                    List.of(
                            "eco ADD {PLAYER} 1000.0"
                    )
            ),
            new ItemShop(
                    new ItemBuilder(Material.GOLD_INGOT)
                            .setName("&6&lDOLADOWANIE MONET &f5tyś")
                            .addLore(
                                    "",
                                    "&7Cena&8: &f8.99 &avpln",
                                    "",
                                    "&aKliknij aby kupić usługę."
                            )
                            .build(),
                    8.99,
                    List.of(
                            "eco ADD {PLAYER} 5000.0"
                    )
            )
    );
}
