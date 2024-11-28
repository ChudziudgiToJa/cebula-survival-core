package pl.cebula.smp.feature.job;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.MessageUtil;

import java.util.Random;

public class JobController implements Listener {

    private final UserService userService;
    private final Random random;

    public JobController(UserService userService, Random random) {
        this.userService = userService;
        this.random = random;
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {
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
        Player player = event.getPlayer();
        Material blockType = event.getBlock().getType();
        User user = this.userService.findUserByNickName(player.getName());

        if (user.getJobType() == JobType.MINER) {
            if (isOre(blockType) && random.nextDouble() < 0.20) {
                user.addMoney(15);
                MessageUtil.sendTitle(player, "", "&2+&a15 monet", 20,50,20);
            }
        }
    }

    @EventHandler
    public void onBlockBreakLog(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material blockType = event.getBlock().getType();
        User user = this.userService.findUserByNickName(player.getName());

        if (user.getJobType() == JobType.LUMBERJACK) {
            if (isLog(blockType) && random.nextDouble() < 0.10) {
                user.addMoney(5);
                MessageUtil.sendTitle(player, "", "&2+&a5 monet", 20,50,20);
            }
        }
    }

    @EventHandler
    public void onBlockBreakFarm(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material blockType = event.getBlock().getType();
        User user = this.userService.findUserByNickName(player.getName());

        if (user.getJobType() == JobType.FARMER) {
            if (isCrop(blockType) && random.nextDouble() < 0.20) {
                user.addMoney(10);
                MessageUtil.sendTitle(player, "", "&2+&a10 monet", 20,50,20);
            }
        }
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        Player player = event.getPlayer();
        User user = this.userService.findUserByNickName(player.getName());

        if (user.getJobType() == JobType.FISHER) {
            if (event.getCaught() != null && random.nextDouble() < 0.20) {
                user.addMoney(20);
                MessageUtil.sendTitle(player, "", "&2+&a20 monet", 20,50,20);            }
        }
    }

    private boolean isOre(Material material) {
        return material == Material.DIAMOND_ORE || material == Material.DEEPSLATE_DIAMOND_ORE ||
                material == Material.GOLD_ORE || material == Material.DEEPSLATE_GOLD_ORE ||
                material == Material.IRON_ORE || material == Material.DEEPSLATE_IRON_ORE ||
                material == Material.COAL_ORE || material == Material.DEEPSLATE_COAL_ORE ||
                material == Material.EMERALD_ORE || material == Material.DEEPSLATE_EMERALD_ORE ||
                material == Material.COPPER_ORE || material == Material.DEEPSLATE_COPPER_ORE ||
                material == Material.LAPIS_ORE || material == Material.DEEPSLATE_LAPIS_ORE ||
                material == Material.REDSTONE_ORE || material == Material.DEEPSLATE_REDSTONE_ORE ||
                material == Material.NETHER_QUARTZ_ORE || material == Material.NETHER_GOLD_ORE ||
                material == Material.ANCIENT_DEBRIS;
    }

    private boolean isLog(Material material) {
        return material == Material.OAK_LOG || material == Material.SPRUCE_LOG ||
                material == Material.BIRCH_LOG || material == Material.JUNGLE_LOG ||
                material == Material.ACACIA_LOG || material == Material.DARK_OAK_LOG ||
                material == Material.MANGROVE_LOG || material == Material.CHERRY_LOG ||
                material == Material.CRIMSON_STEM || material == Material.WARPED_STEM;
    }


    private boolean isCrop(Material material) {
        return material == Material.WHEAT || material == Material.CARROTS ||
                material == Material.POTATOES || material == Material.BEETROOTS ||
                material == Material.MELON_STEM || material == Material.PUMPKIN_STEM ||
                material == Material.SWEET_BERRY_BUSH || material == Material.NETHER_WART ||
                material == Material.COCOA || material == Material.BAMBOO_SAPLING;
    }

}
