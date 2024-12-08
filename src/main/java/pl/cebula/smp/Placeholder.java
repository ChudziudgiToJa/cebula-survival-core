package pl.cebula.smp;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.cebula.smp.feature.abyss.AbyssManager;
import pl.cebula.smp.feature.abyss.AbyssTask;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.service.ClanService;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.DataUtil;
import pl.cebula.smp.util.DecimalUtil;
import pl.cebula.smp.util.DurationUtil;
import pl.cebula.smp.util.MessageUtil;

import java.time.Duration;

public class Placeholder extends PlaceholderExpansion {

    private final UserService userService;
    private final ClanService clanService;

    public Placeholder(UserService userService, ClanService clanService) {
        this.userService = userService;
        this.clanService = clanService;
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
        Clan clanOwner = this.clanService.findClanByOwner(player.getName());
        Clan clanMember = this.clanService.findClanByMember(player.getName());

        if(params.startsWith("monety")) {
            return DecimalUtil.getFormat(user.getMoney());
        }
        if(params.startsWith("czas")) {
            return DurationUtil.format(Duration.ofSeconds(user.getSpentTime()));
        }
        if(params.startsWith("vpln")) {
            return DecimalUtil.getFormat(user.getVpln());
        }
        if(params.startsWith("kills")) {
            return "" + user.getKill();
        }
        if(params.startsWith("deaths")) {
            return "" + user.getDead();
        }
        if(params.startsWith("place_block")) {
            return "" + user.getPlaceBlock();
        }
        if(params.startsWith("break_block")) {
            return "" + user.getBreakBlock();
        }
        if(params.startsWith("kosz")) {
            return MessageUtil.smallText(DurationUtil.convertLong(AbyssManager.time));
        }
        if(params.startsWith("kd")) {
            if (user.getDead() == 0) {
                return "0.0";
            }
            return String.valueOf((double) user.getKill() / user.getDead());

        }
        if(params.startsWith("lider")) {
            if (clanOwner != null) {
                return "&6anga lider do oraxena &8|";
            }
        }
        if(params.startsWith("clan")) {
            if (clanMember != null) {
                if (clanMember.getMemberArrayList().contains(player.getName()) || clanMember.getOwnerName().equals(player.getName())) {
                    return " &a&l" + MessageUtil.smallText(clanMember.getTag().toUpperCase());
                } else {
                    return " &4&l" + MessageUtil.smallText(clanMember.getTag().toUpperCase());
                }
            }
            return "";
        }
        return "";
    }

}
