package pl.cebula.smp;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.clip.placeholderapi.expansion.Relational;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.cebula.smp.configuration.implementation.WorldsSettings;
import pl.cebula.smp.feature.abyss.AbyssManager;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.service.ClanService;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.DecimalUtil;
import pl.cebula.smp.util.DurationUtil;
import pl.cebula.smp.util.MessageUtil;

import java.time.Duration;

public class Placeholder extends PlaceholderExpansion implements Relational {

    private final UserService userService;
    private final ClanService clanService;
    private final WorldsSettings worldsSettings;

    public Placeholder(UserService userService, ClanService clanService, WorldsSettings worldsSettings) {
        this.userService = userService;
        this.clanService = clanService;
        this.worldsSettings = worldsSettings;
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
        if(params.startsWith("vanished")) {
            return user.isVanish() ? MessageUtil.smallText(" &b&lvanish&f") : "";
        }
        if(params.startsWith("clan")) {
            return MessageUtil.smallTextToColor(" &5"+clanMember.getTag().toUpperCase());
        }
        if(params.startsWith("kd")) {
            if (user.getKill() == 0.0 || user.getDead() == 0.0) {
                return "0.0";
            }
            return String.format("%.1f", (double) user.getKill() / user.getDead());
        }
        if(params.startsWith("nameclan")) {
            if (clanMember != null) {
                return MessageUtil.smallText(clanMember.getTag() + " &8(&7" + clanMember.getOwnerName()+ "&8)");
            }
            return MessageUtil.smallText("&cbrak &7/klan");
        }
        if(params.startsWith("end")) {
            return MessageUtil.smallTextToColor(this.worldsSettings.netherJoinStatus ? "&awłączony" : "&cwyłączony");
        }
        return "";
    }

    @Override
    public String onPlaceholderRequest(Player one, Player two, String params) {
        if (one == null || two == null || !params.equalsIgnoreCase("clans")) {
            return null;
        }
        Clan clanOne = this.clanService.findClanByMember(one.getName());
        Clan clanTwo = this.clanService.findClanByMember(two.getName());

        if (clanOne != null && clanTwo == null) {
            return "";
        }
        if (clanOne == null && clanTwo != null) {
            return MessageUtil.smallTextToColor(" &c&l" + clanTwo.getTag());
        }
        if (clanOne == null) {
            return "";
        }
        if (clanOne.equals(clanTwo)) {
            return MessageUtil.smallTextToColor(" &a&l" + clanOne.getTag());
        } else {
            return MessageUtil.smallTextToColor(" &c&l" + clanTwo.getTag());
        }
    }

}