package pl.cebula.smp.feature.equipItem;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import pl.cebula.smp.util.MessageUtil;

public class EquipItemController implements Listener {

    @EventHandler
    public void onClickItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();
        if (itemStack == null) {
            return;
        }
        if (!itemStack.getType().equals(Material.PAPER)) {
            return;
        }
        if (!itemStack.hasItemMeta()) {
            return;
        }
        ItemStack helmet = player.getInventory().getHelmet();
        if (helmet != null && !helmet.getType().equals(Material.AIR)) {
            MessageUtil.sendMessage(player, "Nie możesz założyć tego przedmiotu, ponieważ masz już coś na głowie!");
            return;
        }
        player.getInventory().removeItem(itemStack);
        player.getInventory().setHelmet(itemStack);
    }
}
