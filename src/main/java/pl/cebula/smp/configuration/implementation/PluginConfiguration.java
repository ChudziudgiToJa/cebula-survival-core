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


    public BlockerSettings BlockerSettings = new BlockerSettings();

    public int freePlnNpcID =16;

    public static class BlockerSettings extends OkaeriConfig {
        @Comment("Lista zablokowanych przedmiotów do craftingów  interackjci")
        public List<Material> materials = List.of(
                Material.WRITABLE_BOOK,
                Material.ARMOR_STAND
        );
    }
}
