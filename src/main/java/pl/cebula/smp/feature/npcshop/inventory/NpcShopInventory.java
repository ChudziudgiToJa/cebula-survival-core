package pl.cebula.smp.feature.npcshop.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.npcshop.object.ItemToInteract;
import pl.cebula.smp.feature.npcshop.object.Shop;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.DecimalUtil;
import pl.cebula.smp.util.ItemBuilder;
import pl.cebula.smp.util.MessageUtil;
import pl.cebula.smp.util.SimpleInventory;

import java.util.Arrays;
import java.util.HashMap;

public class NpcShopInventory {

    private final SurvivalPlugin survivalPlugin;
    private final UserService userService;

    public NpcShopInventory(SurvivalPlugin survivalPlugin, UserService userService) {
        this.survivalPlugin = survivalPlugin;
        this.userService = userService;
    }

    public void show(final Player player, final Shop shop) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 54, MessageUtil.smallText("&fsklep: " + shop.getName()));
        Inventory inventory = simpleInventory.getInventory();
        User user = userService.findUserByNickName(player.getName());


        Integer[] glassBlueSlots = new Integer[]{
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 50, 52, 0, 8, 45, 53, 49
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .build()));

        for (ItemToInteract itemToInteract : shop.getItemToInteracts()) {

            ItemStack itemBuilder = new ItemBuilder(itemToInteract.getItemStack().getType())
                    .setName("&l&f" + itemToInteract.getItemStack().getType())
                    .addLore(
                            " ",
                            "&e&lppm &7- &ekliknij aby sprzedać za &f" + DecimalUtil.getFormat(itemToInteract.getSellPrice()) + " monet",
                            "&6&lppm + shift &7- &akliknij aby sprzedać 64 za &f" + DecimalUtil.getFormat(64 * itemToInteract.getBuyPrice()) + " monet",
                            "&a&llpm &7- &akliknij aby kupić za &f" + DecimalUtil.getFormat(itemToInteract.getBuyPrice()) + " monet",
                            "&2&llpm + shift &7- &akliknij aby kupić 64 za &f" + DecimalUtil.getFormat(64 * itemToInteract.getBuyPrice()) + " monet"

                    )
                    .build();

            inventory.setItem(itemToInteract.getItemSlot(), itemBuilder);
        }

        simpleInventory.click(event -> {
            event.setCancelled(true);

            for (ItemToInteract itemToInteract : shop.getItemToInteracts()) {

                if (event.getSlot() == itemToInteract.getItemSlot()) {

                    if (event.getClick().equals(ClickType.LEFT)) { // Kupowanie pojedynczego przedmiotu
                        if (user.getMoney() >= itemToInteract.getBuyPrice()) {
                            user.setMoney(user.getMoney() - itemToInteract.getBuyPrice());
                            ItemStack item = new ItemStack(itemToInteract.getItemStack().getType(), 1);

                            HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(item);
                            if (!leftover.isEmpty()) {
                                player.getWorld().dropItemNaturally(player.getLocation(), item);
                            }

                            MessageUtil.sendMessage(player, "&aKupiłeś przedmiot: " + item.getType() + " za " + itemToInteract.getBuyPrice() + "!");
                        } else {
                            MessageUtil.sendTitle(player, "", "&cNie masz wystarczająco pieniędzy, aby kupić ten przedmiot.", 20, 50, 20);
                            player.closeInventory();
                        }
                        return;
                    }

                    if (event.getClick().equals(ClickType.SHIFT_LEFT)) { // Kupowanie 64 przedmiotów
                        double totalPrice = itemToInteract.getBuyPrice() * 64;
                        if (user.getMoney() >= totalPrice) {
                            user.setMoney(user.getMoney() - totalPrice);
                            ItemStack item = new ItemStack(itemToInteract.getItemStack().getType(), 64);

                            HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(item);
                            if (!leftover.isEmpty()) {
                                leftover.values().forEach(remaining -> player.getWorld().dropItemNaturally(player.getLocation(), remaining));
                            }

                            MessageUtil.sendMessage(player, "&aKupiłeś 64 przedmioty: " + item.getType() + " za " + totalPrice + "!");
                        } else {
                            MessageUtil.sendTitle(player, "", "&cNie masz wystarczająco pieniędzy, aby kupić 64 przedmioty.", 20, 50, 20);
                            player.closeInventory();
                        }
                        return;
                    }

                    if (event.getClick().equals(ClickType.RIGHT)) { // Sprzedawanie pojedynczego przedmiotu
                        ItemStack itemToSell = new ItemStack(itemToInteract.getItemStack().getType(), 1);
                        if (player.getInventory().containsAtLeast(itemToSell, 1)) {
                            player.getInventory().removeItem(itemToSell);
                            user.setMoney(user.getMoney() + itemToInteract.getSellPrice());
                            MessageUtil.sendMessage(player, "&aSprzedałeś przedmiot: " + itemToSell.getType() + " za " + itemToInteract.getSellPrice() + "!");
                        } else {
                            MessageUtil.sendTitle(player, "", "&cNie masz tego przedmiotu w ekwipunku, aby go sprzedać.", 20, 50, 20);
                            player.closeInventory();
                        }
                        return;
                    }

                    if (event.getClick().equals(ClickType.SHIFT_RIGHT)) { // Sprzedawanie 64 przedmiotów
                        ItemStack itemToSell = new ItemStack(itemToInteract.getItemStack().getType(), 64);
                        if (player.getInventory().containsAtLeast(itemToSell, 64)) {
                            player.getInventory().removeItem(itemToSell);
                            double totalPrice = itemToInteract.getSellPrice() * 64;
                            user.setMoney(user.getMoney() + totalPrice);
                            MessageUtil.sendMessage(player, "&aSprzedałeś 64 przedmioty: " + itemToSell.getType() + " za " + totalPrice + "!");
                        } else {
                            MessageUtil.sendTitle(player, "", "&cNie masz 64 tych przedmiotów w ekwipunku, aby je sprzedać.", 20, 50, 20);
                            player.closeInventory();
                        }
                        return;
                    }
                }
            }
        });
        player.openInventory(inventory);
    }
}
