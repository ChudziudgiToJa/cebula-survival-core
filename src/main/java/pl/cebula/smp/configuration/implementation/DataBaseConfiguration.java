package pl.cebula.smp.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;

@Getter
public class DataBaseConfiguration extends OkaeriConfig {

    public String mongoUrl = "xyz";

}
