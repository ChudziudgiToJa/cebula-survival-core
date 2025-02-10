package pl.cebula.smp.feature.job;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.configuration.implementation.PluginConfiguration;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.ItemBuilder;
import pl.cebula.smp.util.MessageUtil;
import pl.cebula.smp.util.SimpleInventory;

import java.util.Arrays;

public class JobInventory {

    private final SurvivalPlugin survivalPlugin;
    private final UserService userService;
    private final PluginConfiguration pluginConfiguration;

    public JobInventory(SurvivalPlugin survivalPlugin, UserService userService, PluginConfiguration pluginConfiguration) {
        this.survivalPlugin = survivalPlugin;
        this.userService = userService;
        this.pluginConfiguration = pluginConfiguration;
    }

    public void show(final Player player) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9*6, MessageUtil.smallText("&fprace:"));
        Inventory inventory = simpleInventory.getInventory();
        User user = this.userService.findUserByNickName(player.getName());

        Integer[] glassBlueSlots = new Integer[]{
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 50, 52, 0, 8, 45, 53
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .build()));

        inventory.addItem(itemStack(user, JobType.KILLER, Material.NETHERITE_SWORD));
        inventory.addItem(itemStack(user, JobType.LUMBERJACK, Material.STONE_AXE));
        inventory.addItem(itemStack(user, JobType.FISHER, Material.FISHING_ROD));
        inventory.addItem(itemStack(user, JobType.MINER, Material.DIAMOND_PICKAXE));
        inventory.addItem(itemStack(user, JobType.FARMER, Material.GOLDEN_HOE));
        inventory.setItem(49, itemStack(user, JobType.CLEAR, Material.BARRIER));

        simpleInventory.click(event -> {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem == null || !clickedItem.hasItemMeta() || !clickedItem.getItemMeta().hasDisplayName()) {
                return;
            }

            String displayName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
            JobType selectedJob = JobType.CLEAR;

            for (JobType jobType : JobType.values()) {
                String jobTypeScs = MessageUtil.smallText(jobType.getPolishName());
                if (jobTypeScs.equals(displayName)) {
                    selectedJob = jobType;
                    break;
                }
            }

            if (event.getClick().equals(ClickType.LEFT)) {

                if (user.getJobType().equals(selectedJob)) {
                    return;
                }
                if (selectedJob.equals(JobType.CLEAR)) {
                    user.setJobType(JobType.CLEAR);
                    MessageUtil.sendTitle(player, "", "&aTwój zawód został usunięty.", 20, 50, 20);
                } else {
                    user.setJobType(selectedJob);
                    MessageUtil.sendTitle(player, "", "&aTwój nowy zawód to: &f" + selectedJob.getPolishName() + "&a.", 20, 50, 20);
                }
                player.closeInventory();
                return;
            }

            if (event.getClick().equals(ClickType.RIGHT)) {
                show(player, selectedJob);
            }
        });

        player.openInventory(inventory);
    }

    public void show(final Player player, JobType jobType) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9*6, MessageUtil.smallText("&fDrop z pracy: " + jobType.getPolishName()));
        Inventory inventory = simpleInventory.getInventory();
        User user = this.userService.findUserByNickName(player.getName());

        Integer[] glassBlueSlots = new Integer[]{
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 50, 52, 0, 8, 45, 53
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .build()));


        for (JobDropChance itemStack : this.pluginConfiguration.jobSettings.jobItems.get(jobType)){
            inventory.addItem(new ItemBuilder(itemStack.getItemStack().getType())
                            .setLore("","&7szansa na drop: &a" + itemStack.getCahnce())
                    .build());
        }

        inventory.setItem(49, new ItemBuilder(Material.BARRIER)
                        .setName("&4cofnij")
                .build()
        );

        simpleInventory.click(event -> {
            event.setCancelled(true);

            if (event.getSlot() == 49) {
                this.show(player);
                return;
            }
        });

        player.openInventory(inventory);
    }




    public ItemStack itemStack(User user, JobType jobType, Material material) {
        ItemBuilder itemBuilder = new ItemBuilder(material)
                .setName("&f&l" + jobType.getPolishName());

        if (user.getJobType().equals(jobType)) {
            if (jobType.equals(JobType.CLEAR)) {
                itemBuilder.setLore(
                        "",
                        "&9LPM &8- &cPosiadasz już ten zawód"
                );
            } else {
                itemBuilder.setLore(
                        "",
                        "&9LPM &8- &cPosiadasz już ten zawód",
                        "&6PPM &8- &fLista dropu z pracy."
                );
            }
        } else {
            if (jobType.equals(JobType.CLEAR)) {
                itemBuilder.setLore(
                        "",
                        "&9LPM &8- &aKliknij aby zatrudnić się"
                );
            } else {
                itemBuilder.setLore(
                        "",
                        "&9LPM &8- &aKliknij aby zatrudnić się",
                        "&6PPM &8- &fLista dropu z pracy."
                );
            }
        }
        return itemBuilder.build();
    }
}

