package pl.cebula.smp.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.cebula.smp.feature.job.JobDropChance;
import pl.cebula.smp.feature.job.JobType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class PluginConfiguration extends OkaeriConfig {


    public BlockerSettings BlockerSettings = new BlockerSettings();
    public RandomTeleportSettings randomTeleportSettings = new RandomTeleportSettings();
    public JobSettings jobSettings = new JobSettings();

    public int freePlnNpcID = 16;
    public int blackSmithID = 31;
    public String discordUrl = "https://dc.cebulasmp.pl/";

    public static class BlockerSettings extends OkaeriConfig {
        @Comment("Lista zablokowanych przedmiotów do craftingów  interackjci")
        public List<Material> materials = List.of(
                Material.WRITABLE_BOOK,
                Material.ARMOR_STAND,
                Material.TNT_MINECART,
                Material.ARMOR_STAND
        );
    }

    public static class RandomTeleportSettings extends OkaeriConfig {
        public ArrayList<Location> buttonsLocations = new ArrayList<>();
        public List<Material> allowButtonsToSetRtp = List.of(Material.STONE_BUTTON);
    }

    public class JobSettings extends OkaeriConfig {
        public final Map<JobType, List<JobDropChance>> jobItems = new EnumMap<>(JobType.class);

        public JobSettings() {
            if (jobItems.isEmpty()) {
                for (JobType jobType : JobType.values()) {
                    jobItems.put(jobType, new ArrayList<>());
                }
            }
        }

        public void addItem(JobType jobType, final JobDropChance item) {
            if (item == null) {
                return;
            }
            jobItems.get(jobType).add(item);
        }

        public void removeItem(JobType jobType, int i) {
            jobItems.get(jobType).remove(i + 1);
        }
    }
}
