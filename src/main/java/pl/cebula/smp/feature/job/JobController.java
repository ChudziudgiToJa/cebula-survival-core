package pl.cebula.smp.feature.job;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.MessageUtil;

import java.util.Map;
import java.util.Random;
import java.util.Set;

public class JobController implements Listener {

    private final UserService userService;
    private final Random random;

    public JobController(UserService userService, Random random) {
        this.userService = userService;
        this.random = random;
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getEntity().getKiller() == null) return;
        Player killer = event.getEntity().getKiller();
        User user = this.userService.findUserByNickName(killer.getName());

        if (user.getJobType() == JobType.KILLER) {
            if (random.nextDouble() < 0.20) {
                user.addMoney(10);
                MessageUtil.sendTitle(killer, "", "&2+&a10 monet", 20,50,20);
            }
        }
    }

    @EventHandler
    public void onBlockBreakOre(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        Material blockType = event.getBlock().getType();
        User user = this.userService.findUserByNickName(player.getName());

        if (player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
            return;
        }

        if (user.getJobType() == JobType.MINER) {
            if (ore.contains(blockType) && random.nextDouble() < 0.20) {
                user.addMoney(15);
                MessageUtil.sendTitle(player, "", "&2+&a15 monet", 20, 50, 20);
            }
        }
    }

    @EventHandler
    public void onBlockBreakLog(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        Material blockType = event.getBlock().getType();
        User user = this.userService.findUserByNickName(player.getName());

        if (user.getJobType() == JobType.LUMBERJACK) {
            if (log.contains(blockType) && random.nextDouble() < 0.10) {
                user.addMoney(5);
                MessageUtil.sendTitle(player, "", "&2+&a5 monet", 20, 50, 20);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        Block block = event.getBlock();
        Material material = block.getType();
        User user = this.userService.findUserByNickName(player.getName());
        if (user.getJobType() == JobType.FARMER && cropMaxAge.containsKey(material)) {
            if (block.getBlockData() instanceof Ageable ageable) {
                int maxAge = cropMaxAge.get(material);
                if (ageable.getAge() == maxAge) {
                    user.addMoney(10);
                    MessageUtil.sendTitle(player, "", "&2+&a10 monet", 20, 50, 20);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        if (event.isCancelled() || event.getState() != PlayerFishEvent.State.CAUGHT_FISH) {
            return;
        }

        Player player = event.getPlayer();
        User user = this.userService.findUserByNickName(player.getName());

        if (user.getJobType() == JobType.FISHER && random.nextDouble() < 0.20) {
            user.addMoney(20);
            MessageUtil.sendTitle(player, "", "&2+&a20 monet", 20, 50, 20);
        }
    }


    private static final Set<Material> ore = Set.of(
            Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE,
            Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE,
            Material.IRON_ORE, Material.DEEPSLATE_IRON_ORE,
            Material.COAL_ORE, Material.DEEPSLATE_COAL_ORE,
            Material.EMERALD_ORE, Material.DEEPSLATE_EMERALD_ORE,
            Material.COPPER_ORE, Material.DEEPSLATE_COPPER_ORE,
            Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE,
            Material.REDSTONE_ORE, Material.DEEPSLATE_REDSTONE_ORE,
            Material.NETHER_QUARTZ_ORE, Material.NETHER_GOLD_ORE,
            Material.ANCIENT_DEBRIS, Material.GILDED_BLACKSTONE
    );

    private static final Set<Material> log = Set.of(
            Material.OAK_LOG, Material.SPRUCE_LOG, Material.BIRCH_LOG,
            Material.JUNGLE_LOG, Material.ACACIA_LOG, Material.DARK_OAK_LOG,
            Material.MANGROVE_LOG, Material.CHERRY_LOG,
            Material.CRIMSON_STEM, Material.WARPED_STEM,
            Material.STRIPPED_OAK_LOG, Material.STRIPPED_SPRUCE_LOG,
            Material.STRIPPED_BIRCH_LOG, Material.STRIPPED_JUNGLE_LOG,
            Material.STRIPPED_ACACIA_LOG, Material.STRIPPED_DARK_OAK_LOG,
            Material.STRIPPED_MANGROVE_LOG, Material.STRIPPED_CHERRY_LOG,
            Material.STRIPPED_CRIMSON_STEM, Material.STRIPPED_WARPED_STEM
    );

    private final Map<Material, Integer> cropMaxAge = Map.of(
            Material.WHEAT, 7,
            Material.CARROTS, 7,
            Material.POTATOES, 7,
            Material.BEETROOTS, 3,
            Material.MELON_STEM, 7,
            Material.PUMPKIN_STEM, 7,
            Material.COCOA, 2
    );

}
