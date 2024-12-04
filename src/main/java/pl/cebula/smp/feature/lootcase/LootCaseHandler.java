package pl.cebula.smp.feature.lootcase;

import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Location;
import org.bukkit.Particle;
import pl.cebula.smp.configuration.implementation.PluginConfiguration;
import pl.cebula.smp.util.MessageUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LootCaseHandler {

    private final PluginConfiguration pluginConfiguration;


    public LootCaseHandler(PluginConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration;
    }


    public void createLootCaseHolograms() {
        List<String> description = new ArrayList<>();
        this.pluginConfiguration.lootCaseSettings.lootCases.forEach(lootCase -> {
            description.add(MessageUtil.smallTextToColor(lootCase.getString()));
            description.add(MessageUtil.smallTextToColor("&f"));
            description.add(MessageUtil.smallTextToColor("&akliknij aby otworzyć skrzynie"));
            DHAPI.createHologram(UUID.randomUUID().toString(), new Location(lootCase.getLocation().getWorld(), lootCase.getLocation().getX(), lootCase.getLocation().getY(), lootCase.getLocation().getZ()).add(0.500, 2.5,0.500), false, description);
            description.clear();
        });
    }
}
