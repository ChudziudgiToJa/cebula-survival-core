package pl.cebula.smp.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClanConfiguration extends OkaeriConfig {

    @Comment("Czy TNT działa na cuboidach podczas wojny")
    public boolean war = false;
}

