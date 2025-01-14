package pl.cebula.smp.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.Setter;
import pl.cebula.smp.util.MessageUtil;

@Getter
@Setter
public class ClanConfiguration extends OkaeriConfig {

    @Comment("Czy TNT działa na cuboidach podczas wojny")
    public boolean war = false;

    @Comment("Godzina rozpoczęcia wojny (tylko godzina, w formacie HH:mm)")
    public String warStartHour = "21:35";

    @Comment("Godzina zakończenia wojny (tylko godzina, w formacie HH:mm)")
    public String warEndHour = "23:00";

    @Comment("Wiadomość na bossbarze podczas wojny")
    public String bossBarMessage = MessageUtil.smallTextToColor("&cAtakowanie klanów włączone codziennie od &4&l20:00 &cdo &4&l23:00 &8!");
}

