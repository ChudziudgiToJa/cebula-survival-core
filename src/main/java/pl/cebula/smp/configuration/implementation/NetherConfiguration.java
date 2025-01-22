package pl.cebula.smp.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.List;

@Getter
@Setter
public class NetherConfiguration extends OkaeriConfig {

    public boolean NetherJoinStatus;
    public Location netherSpawnLocation;
    public List<String> blockedCommandsOnNether = List.of("spawn", "warp", "tpa", "tpaccept", "tpaccept");
}
