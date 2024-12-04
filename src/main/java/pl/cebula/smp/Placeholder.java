package pl.cebula.smp;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.DataUtil;
import pl.cebula.smp.util.DecimalUtil;
import pl.cebula.smp.util.DurationUtil;

import java.time.Duration;

public class Placeholder extends PlaceholderExpansion {

    private final UserService userService;

    public Placeholder(UserService userService) {
        this.userService = userService;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "survival";
    }

    @Override
    public @NotNull String getAuthor() {
        return "chudziudgi";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        User user = userService.findUserByNickName(player.getName());
        if(params.startsWith("monety")) {
            return DecimalUtil.getFormat(user.getMoney());
        }
        if(params.startsWith("czas")) {
            return DurationUtil.format(Duration.ofSeconds(user.getSpentTime()));
        }
        if(params.startsWith("vpln")) {
            return DecimalUtil.getFormat(user.getVpln());
        }
        return "";
    }

}
