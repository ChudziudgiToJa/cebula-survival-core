package pl.cebula.smp.feature.clan.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.feature.armor.ClanArmorHandler;
import pl.cebula.smp.feature.clan.feature.delete.ClanDeleteInventory;
import pl.cebula.smp.feature.clan.feature.invite.ClanInviteService;
import pl.cebula.smp.feature.clan.feature.pvp.ClanPvpInventory;
import pl.cebula.smp.feature.clan.service.ClanService;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.MessageUtil;

@Command(name = "klan")
public class ClanCommand {

    private final UserService userService;
    private final ClanService clanService;
    private final ClanDeleteInventory clanDeleteInventory;
    private final ClanInviteService clanInviteService;
    private final ClanPvpInventory clanPvpInventory;

    public ClanCommand(UserService userService, ClanService clanService, ClanDeleteInventory clanDeleteInventory, ClanInviteService clanInviteService, ClanPvpInventory clanPvpInventory) {
        this.userService = userService;
        this.clanService = clanService;
        this.clanDeleteInventory = clanDeleteInventory;
        this.clanInviteService = clanInviteService;
        this.clanPvpInventory = clanPvpInventory;
    }

    @Execute()
    void help(@Context Player player) {
        MessageUtil.sendMessage(player, "&7Lista dostępnych komend klanowych:");
        MessageUtil.sendMessage(player, "&a/klan stwórz <tag> &7- Tworzy nowy klan z podanym tagiem. Koszt: 3500 monet.");
        MessageUtil.sendMessage(player, "&a/klan usuń &7- Usuwa twój klan (jeśli jesteś właścicielem).");
        MessageUtil.sendMessage(player, "&a/klan zaproś <gracz> &7- Zaprasza podanego gracza do twojego klanu.");
        MessageUtil.sendMessage(player, "&a/klan dołącz <tag> &7- Akceptuje zaproszenie do klanu o podanym tagu.");
        MessageUtil.sendMessage(player, "&a/klan opuść &7- Opuszcza obecny klan (jeśli nie jesteś właścicielem).");
        MessageUtil.sendMessage(player, "&a/klan wyrzuć <gracz> &7- Wyrzuca gracza z twojego klanu.");
        MessageUtil.sendMessage(player, "&a/klan pvp &7- Otwiera menu zmiany ustawień PvP klanu.");
    }


    @Execute(name = "stwórz")
    void create(@Context Player player, @Arg String tag) {
        Clan clan = this.clanService.findClanByOwner(player.getName());

        if (clan != null) {
            MessageUtil.sendMessage(player, "&cPosiadasz juz klan.");
            return;
        }

        if (!(tag.length() == 4)) {
            MessageUtil.sendMessage(player, "&ctag klanu musi mieć 5 znaków");
            return;
        }

        if (!tag.matches("[a-zA-Z]+")) {
            MessageUtil.sendMessage(player, "&cTag klanu może zawierać tylko litery alfabetu (bez cyfr i znaków specjalnych)");
            return;
        }
        User user = this.userService.findUserByUUID(player.getUniqueId());

        if (user.getMoney() < 3500) {
            MessageUtil.sendMessage(player, "&cNie stać cię! kosz klanu to &43500 &cmonet");
            return;
        }
        user.setMoney(user.getMoney() - 3500);
        this.clanService.createClan(new Clan(player, tag));
        Bukkit.getOnlinePlayers().forEach(player1 -> MessageUtil.sendMessage(player1, player.getName() + " &astworzył nowy klan &2" + tag.toUpperCase()));
    }

    @Execute(name = "usuń")
    void delete(@Context Player player) {
        Clan clan = this.clanService.findClanByOwner(player.getName());

        if (clan == null) {
            MessageUtil.sendMessage(player, "&cNie masz klanu.");
            return;
        }
        this.clanDeleteInventory.showDeleteInventory(player, clan);
    }

    @Execute(name = "zaproś")
    void invinteMember(@Context Player player, @Arg Player target) {
        Clan clan = this.clanService.findClanByOwner(player.getName());

        if (clan == null) {
            MessageUtil.sendMessage(player, "&cNie masz klanu lub nie jesteś liderem.");
            return;
        }

        if (target == null) {
            MessageUtil.sendMessage(player, "&cGracz nie jest aktywny");
            return;
        }

        Clan targetClan = this.clanService.findClanByMember(target.getName());

        if (targetClan != null) {
            MessageUtil.sendMessage(player, "&cGracz posiada już klan");
            return;
        }
        this.clanInviteService.inviteToClan(clan, target.getName());
        MessageUtil.sendMessage(player, "&aZaproszono do klanu: &f" + target.getName());
        MessageUtil.sendMessage(target, "&aOtrzymałeś/aś zaproszenie do klanu: " + clan.getTag());
        MessageUtil.sendMessage(target, "      &7&npo 15s zaprosenie wygasa!");
    }

    @Execute(name = "dołącz")
    void acceptClanInvite(@Context Player player, @Arg String targetClan) {
        Clan playerClan = this.clanService.findClanByOwner(player.getName());
        if (playerClan != null) {
            MessageUtil.sendMessage(player, "&cPosiadasz już klan.");
            return;
        }
        Clan clan = this.clanService.findClanByTag(targetClan);
        if (clan == null) {
            MessageUtil.sendMessage(player, "&cTaki klan nie istnieje.");
            return;
        }
        if (!this.clanInviteService.clanInviteConcurrentHashMap.containsKey(clan) ||
                !this.clanInviteService.clanInviteConcurrentHashMap.get(clan).equals(player.getName())) {
            MessageUtil.sendMessage(player, "&cNie masz zaproszenia do tego klanu.");
            return;
        }
        clan.getMemberArrayList().add(player.getName());
        this.clanInviteService.clanInviteConcurrentHashMap.remove(clan, player.getName());
        MessageUtil.sendMessage(player, "&aPomyślnie dołączyłeś do klanu " + clan.getTag() + ".");
        clan.getMemberArrayList().forEach(s -> {
            Player clanMember = Bukkit.getPlayer(s);
            if (clanMember == null) return;
            MessageUtil.sendMessage(clanMember, "&f" + player.getName() + " &adołączył do klanu.");
        });
    }


    @Execute(name = "opuść")
    void quitClan(@Context Player player) {
        Clan clan = this.clanService.findClanByMember(player.getName());

        if (clan == null) {
            MessageUtil.sendMessage(player, "&cNie masz klanu.");
            return;
        }

        if (clan.getOwnerName().equals(player.getName())) {
            MessageUtil.sendMessage(player, "&cNie możesz opuścić swojego klanu");
            return;
        }

        clan.getMemberArrayList().remove(player.getName());
        Bukkit.getOnlinePlayers().forEach(player1 -> {
            ClanArmorHandler.refreshArmorPacket(player, player1);
            ClanArmorHandler.refreshArmorPacket(player1, player);
        });
        MessageUtil.sendMessage(player, "&aopuszczono klan: &f" + clan.getTag());
    }

    @Execute(name = "wyrzuć")
    void removeMember(@Context Player player, @Arg String target) {
        Clan clan = this.clanService.findClanByOwner(player.getName());

        if (clan == null) {
            MessageUtil.sendMessage(player, "&cNie masz klanu lub nie jesteś liderem.");
            return;
        }

        Clan targetClan = this.clanService.findClanByMember(target);
        if (targetClan == null) {
            MessageUtil.sendMessage(player, "&cGracz nie posiada klan");
            return;
        }

        if (!clan.getMemberArrayList().contains(target)) {
            MessageUtil.sendMessage(player, "&cgracz nie jest w twoim klanie.");
            return;
        }

        clan.getMemberArrayList().remove(target);
        Player targetPlayer = Bukkit.getPlayer(target);
        if (targetPlayer != null) {
            Bukkit.getOnlinePlayers().forEach(player1 -> {
                ClanArmorHandler.refreshArmorPacket(player, player1);
                ClanArmorHandler.refreshArmorPacket(player1, player);
            });
        }
        MessageUtil.sendMessage(player, "&awyrzucono z klanu: " + target);
    }

    @Execute(name = "pvp")
    void changePvp(@Context Player player) {
        Clan clan = this.clanService.findClanByOwner(player.getName());

        if (clan == null) {
            MessageUtil.sendMessage(player, "&cNie masz klanu.");
            return;
        }

        if (!clan.getOwnerName().equals(player.getName())) {
            MessageUtil.sendMessage(player, "&cnie jesteś liderem klanu.");
            return;
        }
        this.clanPvpInventory.showChangePvp(player, clan);
    }
}
