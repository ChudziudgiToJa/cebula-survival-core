package pl.cebula.smp.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import pl.cebula.smp.feature.itemshop.ItemShop;
import pl.cebula.smp.feature.kit.Kit;
import pl.cebula.smp.feature.lootcase.LootCase;
import pl.cebula.smp.feature.shop.object.ItemToInteract;
import pl.cebula.smp.feature.shop.object.Shop;
import pl.cebula.smp.util.ItemBuilder;

import java.util.List;

public class PluginConfiguration extends OkaeriConfig {


    public NpcShopSettings npcShopSettings = new NpcShopSettings();
    public KitSettings kitSettings = new KitSettings();
    public BlockerSettings BlockerSettings = new BlockerSettings();
    public ItemShopSettings itemShopSettings = new ItemShopSettings();
    public LootCaseSettings lootCaseSettings = new LootCaseSettings();

    public int freePlnNpcID =16;
    public String incognitoPlayerNickSkin = "Khris0126";

    public static class LootCaseSettings extends OkaeriConfig {
        public List<LootCase> lootCases = List.of(
                new LootCase(
                        "afk",
                        "skrzynia afk &8[&e⭐&7⭐⭐⭐⭐&8]",
                        new Location(Bukkit.getWorlds().getFirst(),30,72,28),
                        "",
                        Particle.HEART,
                        List.of(
                        )
                ),
                new LootCase(
                        "wedrowca",
                        "skrzynia wędrowca &8[&e⭐⭐&7⭐⭐⭐&8]",
                        new Location(Bukkit.getWorlds().getFirst(),26,72,25),
                        "",
                        Particle.NOTE,
                        List.of(
                        )
                ),
                new LootCase(
                        "smoka",
                        "skrzynia smoka &8[&e⭐⭐⭐&7⭐⭐&8]",
                        new Location(Bukkit.getWorlds().getFirst(),25,72,17),
                        "",
                        Particle.SCULK_SOUL,
                        List.of(
                        )
                ),
                new LootCase(
                        "piekiel",
                        "skrzynia piekieł &8[&e⭐⭐⭐⭐⭐&8]",
                        new Location(Bukkit.getWorlds().getFirst(),29,72,13),
                        "",
                        Particle.SMOKE,
                        List.of(
                        )
                )
        );
    }

    public static class ItemShopSettings extends OkaeriConfig {
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
                        "lp user {PLAYER} parent addtemp vip 30d"
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
                        "lp user {PLAYER} parent addtemp mvip 30d"
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
                        "lp user {PLAYER} parent addtemp cebulak 30d"
                )
        );
    }

    public static class BlockerSettings extends OkaeriConfig {
        @Comment("Lista zablokowanych przedmiotów do craftingów  interackjci")
        public List<Material> materials = List.of(
                Material.WRITABLE_BOOK,
                Material.ARMOR_STAND
        );
    }

    public static class NpcShopSettings extends OkaeriConfig {
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

    public static class KitSettings extends OkaeriConfig {
        @Comment("## czas podajemy w milisekundach bo to liczy na czasie aktualnym świata")
        public List<Kit> kitList = List.of(
                new Kit(
                        "gracz",
                        900000,
                        new ItemBuilder(Material.LEATHER_HELMET)
                                .setName("&7gracz")
                                .addLore(
                                        "",
                                        "&aKliknij aby otworzyć podgląd."
                                )
                                .build(),
                        "",
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
}
