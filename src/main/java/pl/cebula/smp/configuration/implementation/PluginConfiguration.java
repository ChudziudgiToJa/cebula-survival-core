package pl.cebula.smp.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class PluginConfiguration extends OkaeriConfig {


    public BlockerSettings BlockerSettings = new BlockerSettings();
    public RandomTeleportSettings randomTeleportSettings = new RandomTeleportSettings();

    public int freePlnNpcID = 16;
    public int blackSmithID = 31;
    public String discordUrl = "https://discord.gg/Y5v5JSMFkN";

    public static class BlockerSettings extends OkaeriConfig {
        @Comment("Lista zablokowanych przedmiotów do craftingów  interackjci")
        public List<Material> materials = List.of(
                Material.WRITABLE_BOOK,
                Material.ARMOR_STAND,
                Material.TNT_MINECART
        );
    }

    public static class RandomTeleportSettings extends OkaeriConfig {
        public ArrayList<Location> buttonsLocations = new ArrayList<>();
        public List<Material> allowButtonsToSetRtp = List.of(Material.STONE_BUTTON);
    }
}
