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
                                    "&7Cena&8: &f4.99 &avpln",
                                    "",
                                    "&aKliknij aby kupić usługę."
                            )
                            .build(),
                    4.99,
                    List.of("lp user {PLAYER} parent addtemp vip 30d")
            ),
            new ItemShop(
                    new ItemBuilder(Material.DIAMOND_HELMET)
                            .setName("ꑇ &8(&f30dni&8)")
                            .addLore(
                                    "",
                                    "&7Cena&8: &f9.99 &avpln",
                                    "",
                                    "&aKliknij aby kupić usługę."
                            )
                            .build(),
                    9.99,
                    List.of("lp user {PLAYER} parent addtemp mvip 30d")
            ),
            new ItemShop(
                    new ItemBuilder(Material.NETHERITE_HELMET)
                            .setName("ꑍ &8(&f30dni&8)")
                            .addLore(
                                    "",
                                    "&7Cena&8: &f14.99 &avpln",
                                    "",
                                    "&aKliknij aby kupić usługę."
                            )
                            .build(),
                    14.99,
                    List.of("lp user {PLAYER} parent addtemp cebulak 30d")
            )
    );
}
