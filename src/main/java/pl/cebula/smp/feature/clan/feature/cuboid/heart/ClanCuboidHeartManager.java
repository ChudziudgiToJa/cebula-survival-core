package pl.cebula.smp.feature.clan.feature.cuboid.heart;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.feature.cuboid.bossbar.ClanCuboidBossBarManager;
import pl.cebula.smp.feature.clan.service.ClanService;
import pl.cebula.smp.util.MessageUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClanCuboidHeartManager {

    public static void createHearth(final Clan clan) {
        Location clanHeart = new Location(Bukkit.getWorlds().getFirst(), (int) clan.getLocation().getX(), (int) clan.getLocation().getY(), (int) clan.getLocation().getZ());
        pasteSchematic((int) clan.getLocation().getX(), (int) clan.getLocation().getY(), (int) clan.getLocation().getZ(), "cuboid.schem");
        clanHeart.getBlock().setType(Material.BEE_NEST);
    }


    public static void pasteSchematic(int X, int y, int Z, String schematicName) {
        File myfile = new File(SurvivalPlugin.getInstance().getDataFolder(), schematicName);
        ClipboardFormat format = ClipboardFormats.findByFile(myfile);
        if (format == null) {
            System.out.println("Format schematu jest null dla pliku: " + myfile.getPath());
            return;
        }

        com.sk89q.worldedit.world.World adaptedWorld = BukkitAdapter.adapt(Bukkit.getWorlds().getFirst());

        try (ClipboardReader reader = format.getReader(new FileInputStream(myfile))) {
            Clipboard clipboard = reader.read();
            try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(adaptedWorld,
                    -1)) {
                Operation operation = new ClipboardHolder(clipboard).createPaste(editSession)
                        .to(BlockVector3.at(X, y, Z)).build();
                try {
                    Operations.complete(operation);

                    editSession.flushSession();
                } catch (WorldEditException ignored) {
                }
            }

        } catch (IOException ignored) {
        }
    }

    public static void handleClanHeartDamage(Player player, Location blockLocation, Clan targetClan, SurvivalPlugin survivalPlugin) {
        targetClan.setCuboidHearthValue(targetClan.getCuboidHearthValue() - 1);
        Bukkit.getScheduler().runTask(survivalPlugin, () -> {
            blockLocation.getBlock().setType(Material.BEDROCK);
        });

        Bukkit.getScheduler().runTaskLater(survivalPlugin, () -> {
                blockLocation.getBlock().setType(Material.BEE_NEST);
        }, 10L);

        targetClan.getMemberArrayList().stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .forEach(target -> MessageUtil.sendMessage(target, "&4⚠ &4&lUWAGA &cktoś atakuje twoje serce klanu!"));

        MessageUtil.sendTitle(player, "", "&fżycie klanu &c&l" + targetClan.getTag() + " &d" + targetClan.getCuboidHearthValue(), 10, 20, 10);
    }

    public static void handleClanHeartDestruction(Player player, Location blockLocation, Clan targetClan, ClanService clanService) {
        blockLocation.getWorld().getPlayers().stream()
                .filter(nearbyPlayer -> nearbyPlayer.getLocation().distance(blockLocation) < 80)
                .forEach(nearbyPlayer -> {
                    BossBar bossBar = ClanCuboidBossBarManager.getBossBar(nearbyPlayer.getUniqueId());
                    if (bossBar != null) {
                        bossBar.removePlayer(nearbyPlayer);
                        ClanCuboidBossBarManager.removeBossBar(nearbyPlayer.getUniqueId());
                    }
                });

        Clan clan1 = clanService.findClanByMember(player.getName());

        Bukkit.getOnlinePlayers().forEach(player1 -> {
            MessageUtil.sendMessage(player1, "&4⚠ &2&l" + clan1.getTag() + " &cpodbija: &4" + targetClan.getTag());
        });

        DHAPI.removeHologram(targetClan.getTag());
        clanService.removeClan(targetClan);

        Location clanHeart = new Location(player.getWorld(), targetClan.getLocation().getX(), targetClan.getLocation().getY(), targetClan.getLocation().getZ());
        player.getWorld().playSound(clanHeart, Sound.ITEM_GOAT_HORN_SOUND_0, 1, 1);
    }
}
