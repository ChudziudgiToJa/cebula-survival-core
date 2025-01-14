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
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.util.MessageUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CuboidHeartManager {

    public static void createHearth(final Clan clan) {
        Location clanHeart = new Location(Bukkit.getWorlds().getFirst(), (int) clan.getLocation().getX(), (int) clan.getLocation().getY(), (int) clan.getLocation().getZ());
        pasteSchematic((int) clan.getLocation().getX(), (int) clan.getLocation().getY(), (int) clan.getLocation().getZ(), "cuboid.schem");
        clanHeart.getBlock().setType(Material.BEE_NEST);
        createHolo(clan);
    }

    public static void createHolo(final Clan clan) {
        List<String> description = new ArrayList<>();
        description.add(MessageUtil.smallTextToColor(clan.getTag()));
        description.add(MessageUtil.smallTextToColor("&a" + clan.getOnlineMembersCount() + "&8/&7" + (clan.getMemberArrayList().size() + 1) + " &f członków online"));
        description.add(MessageUtil.smallTextToColor("&fżycie &d" + clan.getCuboidHearthValue()));
        DHAPI.createHologram(clan.getTag(), new Location(Bukkit.getWorlds().getFirst(),clan.getLocation().getX() + 0.5,clan.getLocation().getY() + 2,clan.getLocation().getZ() + 0.5), false, description);
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
}
