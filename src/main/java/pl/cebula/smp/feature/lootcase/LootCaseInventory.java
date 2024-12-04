package pl.cebula.smp.feature.lootcase;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.configuration.implementation.PluginConfiguration;
import pl.cebula.smp.util.ItemStackBuilder;
import pl.cebula.smp.util.ItemStackSerializable;
import pl.cebula.smp.util.MessageUtil;
import pl.cebula.smp.util.SimpleInventory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class LootCaseInventory {

    private final SurvivalPlugin survivalPlugin;

    public LootCaseInventory(SurvivalPlugin survivalPlugin) {
        this.survivalPlugin = survivalPlugin;
    }

    public void showPrewiew(final Player player, final LootCase lootCase) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 6, MessageUtil.smallTextToColor(lootCase.getString()));
        Inventory inventory = simpleInventory.getInventory();

        Integer[] glassBlueSlots = new Integer[]{
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 52, 0, 8, 45, 53
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemStackBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .toItemStack()));

        Integer[] openCaseItem = new Integer[]{
                48, 49, 50,
        };

        Arrays.stream(openCaseItem).forEach(slot -> inventory.setItem(slot,
                new ItemStackBuilder(Material.LIME_DYE)
                        .setName("&akliknij aby otworzyć")
                        .toItemStack()));

        for (LootCaseChance lootCaseChance : lootCase.getDropItems()) {
            ItemStack lootItemStack = ItemStackSerializable.readItemStack(lootCaseChance.getItemStackInString());
            ItemMeta itemMeta = lootItemStack.getItemMeta();
            itemMeta.setLore(List.of(
                    " ",
                    MessageUtil.smallTextToColor("&7szansa na wylosowanie: &a" + lootCaseChance.getChance() + "%"),
                    ""
            ));
            lootItemStack.setItemMeta(itemMeta);
            inventory.addItem(lootItemStack);
        }


        simpleInventory.click(event -> {
            event.setCancelled(true);

            if (Arrays.asList(openCaseItem).contains(event.getSlot())) {
                if (!player.getInventory().contains(lootCase.getKeyItemStack())) {
                    MessageUtil.sendTitle(player, "", "&cNie posiadasz klucza zakup pod &7/itemshop", 20, 50, 20);
                    player.closeInventory();
                    return;
                }

//                for (ItemStack item : player.getInventory().getContents()) {
//                    if (item != null && item.isSimilar(lootCase.getKeyItemStack())) {
//                        if (item.getAmount() > 1) {
//                            item.setAmount(item.getAmount() - 1);
//                        } else {
//                            player.getInventory().remove(item);
//                        }
//                        break;
//                    }
//                }

                ItemStack lootedItemStack = ItemStackSerializable.readItemStack(LootCaseManager.pickRandomItem(lootCase.getDropItems()).getItemStackInString());

                if (lootedItemStack == null) return;

                HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(lootedItemStack);
                leftover.values().forEach(remaining ->
                        player.getWorld().dropItemNaturally(player.getLocation(), remaining)
                );
                Bukkit.getOnlinePlayers().forEach(player1 -> {
                    MessageUtil.sendMessage(player1, player.getName() + " &aotworzył &2" + lootCase.getName() +  "&a skrzyie &8| &b" + lootedItemStack.getType());
                    player1.playSound(player1, Sound.ENTITY_CAT_HISS, 5, 5);
                });
            }
            player.closeInventory();
        });
        player.openInventory(inventory);
    }

}
