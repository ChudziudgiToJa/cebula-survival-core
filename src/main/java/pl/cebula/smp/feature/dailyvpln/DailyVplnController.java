package pl.cebula.smp.feature.dailyvpln;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.cebula.smp.configuration.implementation.PluginConfiguration;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.DecimalUtil;
import pl.cebula.smp.util.DurationUtil;
import pl.cebula.smp.util.MessageUtil;

public class DailyVplnController implements Listener {

    private final UserService userService;
    private final PluginConfiguration pluginConfiguration;
    private final DailyVplnManager dailyVplnManager;

    public DailyVplnController(UserService userService, PluginConfiguration pluginConfiguration, DailyVplnManager dailyVplnManager) {
        this.userService = userService;
        this.pluginConfiguration = pluginConfiguration;
        this.dailyVplnManager = dailyVplnManager;
    }

    @EventHandler
    public void click(NPCRightClickEvent event) {
        Player player = event.getClicker();
        User user = this.userService.findUserByNickName(player.getName());
        if (user == null) return;

        if (event.getNPC().getId() == this.pluginConfiguration.freePlnNpcID) {
            if (user.getDailyFreeVpln() > System.currentTimeMillis()) {
                MessageUtil.sendTitle(player, "", "&cmożesz odebrać za: " + DurationUtil.getTimeFormat(user.getDailyFreeVpln() - System.currentTimeMillis()), 20, 50, 20);
                player.closeInventory();
                player.playSound(player, Sound.ENTITY_BEE_HURT, 5, 5);
                return;
            }

            double pln = this.dailyVplnManager.getRandomValueForPlayer(player);
            user.setVpln(user.getVpln() + pln);
            user.setDailyFreeVpln(86400000 + System.currentTimeMillis());
            MessageUtil.sendTitle(player, "", "&aOdebrano darmowe: " + DecimalUtil.getFormat(pln) + " vpln", 20,50,20);
            player.playSound(player, Sound.ENTITY_VILLAGER_YES, 5 ,5);
        }
    }
}
