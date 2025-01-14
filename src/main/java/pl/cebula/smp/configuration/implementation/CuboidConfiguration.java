package pl.cebula.smp.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import pl.cebula.smp.util.MessageUtil;

public class CuboidConfiguration extends OkaeriConfig {

    @Comment("Czy TNT działa na cuboidach podczas wojny")
    public boolean war = false;

    @Comment("Godzina rozpoczęcia wojny (w formacie HH:mm)")
    public String warStartTime = "19:32";

    @Comment("Godzina zakończenia wojny (w formacie HH:mm)")
    public String warEndTime = "19:40";

    @Comment("Wiadomość na bossbarze podczas wojny")
    public String bossBarMessage = MessageUtil.smallTextToColor("&cAtakowanie klanów włączone od godziny &8( &4&l17:00-20:00 &8)");
}
