package pl.cebula.smp.feature.backup;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.ItemStackSerializable;

import java.time.Instant;
import java.util.ArrayList;

public class BackupController implements Listener {

    private final UserService userService;

    public BackupController(UserService userService) {
        this.userService = userService;
    }

    @EventHandler
    public void onDead(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        User user = this.userService.findUserByNickName(player.getName());

        if (user.getBackups().size() >= 21) {
            user.getBackups().remove(user.getBackups().size());
        }

        ItemStack[] inventory = player.getInventory().getContents();
        ArrayList<ItemStack> itemList = new ArrayList<>();

        for (ItemStack itemStack : inventory) {
            if (itemStack != null) {
                itemList.add(itemStack);
            }
        }

        if (itemList.isEmpty()) return;

        user.getBackups().add(new Backup(
                Instant.now(),
                player.getLevel(),
                player.getExp(),
                ItemStackSerializable.write(itemList)
        ));
    }
}
