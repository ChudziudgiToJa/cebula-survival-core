package pl.cebula.smp.feature.clan.command;

import com.comphenix.protocol.ProtocolManager;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.feature.armor.ClanArmorHandler;
import pl.cebula.smp.feature.clan.feature.cuboid.heart.CuboidHeartManager;
import pl.cebula.smp.feature.clan.feature.delete.ClanDeleteInventory;
import pl.cebula.smp.feature.clan.feature.invite.ClanInviteService;
import pl.cebula.smp.feature.clan.manager.ClanManager;
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
    private final ProtocolManager protocolManager;

    public ClanCommand(UserService userService, ClanService clanService, ClanDeleteInventory clanDeleteInventory, ClanInviteService clanInviteService, ProtocolManager protocolManager) {
        this.userService = userService;
        this.clanService = clanService;
        this.clanDeleteInventory = clanDeleteInventory;
        this.clanInviteService = clanInviteService;
        this.protocolManager = protocolManager;
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
        MessageUtil.sendMessage(player, "&a/klan ulepszenia &7- Otwiera menu ulepszeń klanu.");
    }


    @Execute(name = "stwórz")
    void create(@Context Player player, @Arg String tag) {
        Clan clan = this.clanService.findClanByOwner(player.getName());

        if (clan != null) {
            MessageUtil.sendMessage(player, "&cPosiadasz juz klan.");
            return;
        }

        if (tag.length() < 2 || tag.length() > 5) {
            MessageUtil.sendMessage(player, "&cTag klanu musi mieć od 2 do 5 znaków");
            return;
        }

        if (!tag.matches("[a-zA-Z]+")) {
            MessageUtil.sendMessage(player, "&cTag klanu może zawierać tylko litery alfabetu (bez cyfr i znaków specjalnych)");
            return;
        }

        if (!(player.getLocation().getY() == 40)) {
            MessageUtil.sendMessage(player, "&cMusisz stac na 40 kratce wysokości aby stworzyć klanu");
            return;
        }

        if (clanService.isNearAnotherClan(player.getLocation())) {
            MessageUtil.sendMessage(player, "&cNie możesz stworzyć klanu w pobliżu innego klanu.");
            return;
        }

        double distanceFromOrigin = player.getLocation().distance(new Location(player.getWorld(), 0, 0, 0));
        if (distanceFromOrigin < 200) {
            MessageUtil.sendMessage(player, "&cMusisz być co najmniej 200 kratek od spawna");
            return;
        }

        double worldBorderSize = player.getWorld().getWorldBorder().getSize() / 2;
        double playerX = player.getLocation().getX();
        double playerZ = player.getLocation().getZ();

        if (Math.abs(playerX) >= worldBorderSize - 100 || Math.abs(playerZ) >= worldBorderSize - 100) {
            MessageUtil.sendMessage(player, "&cNie możesz stworzyć klanu w pobliżu granicy świata. Musisz być co najmniej 100 kratek od granicy.");
            return;
        }

        WorldBorder worldBorder = player.getWorld().getWorldBorder();
        double distanceFromBorder = player.getLocation().distance(new Location(player.getWorld(), worldBorder.getCenter().getX(), player.getLocation().getY(), worldBorder.getCenter().getZ()));
        if (distanceFromBorder < 100) {
            MessageUtil.sendMessage(player, "&cMusisz być co najmniej 100 kratek od granicy świata.");
            return;
        }

        User user = this.userService.findUserByUUID(player.getUniqueId());

        if (user.getMoney() < 3500) {
            MessageUtil.sendMessage(player, "&cNie stać cię! kosz klanu to &43500 &cmonet");
            return;
        }

        user.setMoney(user.getMoney() - 3500);
        Clan newClan = new Clan(player, tag.toLowerCase());
        this.clanService.createClan(newClan);
        CuboidHeartManager.createHearth(newClan);
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

        if (clan.getMemberArrayList().size() >= 8) {
            MessageUtil.sendMessage(player, "&cKlan osiągnął maksymalny limit graczy (8).");
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

        if (clan.getMemberArrayList().size() >= 8) {
            MessageUtil.sendMessage(player, "&cKlan osiągnął maksymalny limit graczy (8).");
            return;
        }

        this.clanInviteService.inviteToClan(clan, target.getName());
        MessageUtil.sendMessage(player, "&aZaproszono do klanu: &f" + target.getName());

        Component targetMessage1 = Component.text("§aOtrzymałeś/aś zaproszenie do klanu: " + clan.getTag())
                .clickEvent(ClickEvent.runCommand("/klan dołącz " + clan.getTag()));
        target.sendMessage(targetMessage1);

        Component targetMessage2 = Component.text("      §7§npo 30s zaproszenie wygasa! §b[klik]")
                .clickEvent(ClickEvent.runCommand("/klan dołącz " + clan.getTag()));
        target.sendMessage(targetMessage2);
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
        Clan clan = this.clanService.findClanByMember(player.getName());

        if (clan == null) {
            MessageUtil.sendMessage(player, "&cNie masz klanu.");
            return;
        }

        if (!clan.getOwnerName().equals(player.getName())) {
            MessageUtil.sendMessage(player, "&cNie jesteś liderem klanu.");
            return;
        }
        clan.setPvp(!clan.isPvp());
        MessageUtil.sendMessage(player, "&7Zmieniono status pvp na: " + (clan.isPvp() ? "&cwyłączony" : "&awłączony"));
    }


    @Execute(name = "info")
    void infoOther(@Context Player player, @OptionalArg String string) {
        if (string == null || string.isBlank() || string.isEmpty()) {

            Clan clan = this.clanService.findClanByMember(player.getName());

            if (clan == null) {
                MessageUtil.sendMessage(player, "&cNie masz klanu.");
                return;
            }
            MessageUtil.sendMessage(player, "&fklan: &a&l" + clan.getTag());
            MessageUtil.sendMessage(player, "&fkordy: &a&l" + clan.getLocation().toString());
            MessageUtil.sendMessage(player, "&fzałożyciel: &a&l" + clan.getOwnerName());
            MessageUtil.sendMessage(player, "&fLista graczy w klanie&8: &7" + ClanManager.formatPlayerStatus(clan.getMemberArrayList()));
            return;
        }

        Clan targetClan = this.clanService.findClanByTag(string.toLowerCase());

        if (targetClan == null) {
            MessageUtil.sendMessage(player, "&cNie ma takiego klanu.");
            return;
        }
        MessageUtil.sendMessage(player, "&fklan: &a&l" + targetClan.getTag());
        MessageUtil.sendMessage(player, "&fzałożyciel: &a&l" + targetClan.getOwnerName());
        MessageUtil.sendMessage(player, "&fLista graczy w klanie&8: &7" + ClanManager.formatPlayerStatus(targetClan.getMemberArrayList()));
    }


//    @Execute(name = "admin")
//    @Permission("cebulasmp.command.clan.admin")
//    void adminCommands(@Context Player player) {
//
//    }
}
