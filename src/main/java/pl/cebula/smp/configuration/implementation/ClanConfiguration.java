package pl.cebula.smp.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.List;

@Getter
@Setter
public class ClanConfiguration extends OkaeriConfig {

    @Comment("Czy TNT działa na cuboidach podczas wojny")
    public boolean war = false;

    public List<String> blockCommandList = List.of("tpaaccept","tpaccept", "sethome");

    public List<Material> blockBreakList = List.of(Material.OBSIDIAN, Material.CRYING_OBSIDIAN, Material.ENCHANTING_TABLE);
}

