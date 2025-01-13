package pl.cebula.smp.feature.itemshop;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.util.MessageUtil;

public class ItemShopManager {

    public void onBuyItem(final User user, final double price, final ItemShop shop) {
        Player player = Bukkit.getPlayer(user.getNickName());
        if (player == null) return;

        if (user.getVpln() < price) {
            MessageUtil.sendTitle(player, "", "&cNie stać cię na tą usługę", 20, 50, 20);
            player.playSound(player, Sound.ENTITY_VILLAGER_NO, 5, 5);
        } else {
            user.setVpln(user.getVpln() - price);
            sendCommand(player, shop);
            MessageUtil.sendTitle(player, "", "&aZakupiono&2: &f" + shop.getItemStack().getItemMeta().getDisplayName(), 20, 50, 20);
            Bukkit.getOnlinePlayers().forEach(all -> {
                MessageUtil.sendMessage(all, " ");
                MessageUtil.sendMessage(all, "&6&lITEMSHOP &8(/itemshop)");
                MessageUtil.sendMessage(all, " ");
                MessageUtil.sendMessage(all, " &f" + user.getNickName() + " &azakupił usługe: &f" + shop.getItemStack().getItemMeta().getDisplayName());
                MessageUtil.sendMessage(all, " &b&lgz &fna chacie.... &4<3");
                MessageUtil.sendMessage(all, " ");
                all.playSound(all, Sound.ENTITY_ENDER_DRAGON_DEATH, 1, 1);
            });
        }
    }

    public void sendCommand(final Player player, final ItemShop shop) {
        for (String s : shop.getCommandList()) {
            String command = s.replace("{PLAYER}", player.getName());
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }
}
