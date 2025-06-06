package pl.cebula.smp.feature.clan.feature.delete;

import com.comphenix.protocol.ProtocolManager;
import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.feature.armor.ClanArmorHandler;
import pl.cebula.smp.feature.clan.feature.cuboid.bossbar.ClanCuboidBossBarManager;
import pl.cebula.smp.feature.clan.service.ClanService;
import pl.cebula.smp.util.ItemBuilder;
import pl.cebula.smp.util.MessageUtil;
import pl.cebula.smp.util.SimpleInventory;

import java.util.Arrays;

public class ClanDeleteInventory {

    private final SurvivalPlugin survivalPlugin;
    private final ClanService clanService;
    private final ProtocolManager protocolManager;

    public ClanDeleteInventory(SurvivalPlugin survivalPlugin, ClanService clanService, ProtocolManager protocolManager) {
        this.survivalPlugin = survivalPlugin;
        this.clanService = clanService;
        this.protocolManager = protocolManager;
    }

    public void showDeleteInventory(final Player player, final Clan clan) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 3, MessageUtil.smallText("&6&lKLAN &7czy na pewno?"));
        Inventory inventory = simpleInventory.getInventory();

        Integer[] glassGreenSlots = new Integer[]{
                1, 2, 0,
                9, 10, 11,
                18, 19, 20
        };

        Integer[] glassRedSlots = new Integer[]{
                6, 7, 8,
                15, 16, 17,
                24, 25, 26
        };

        Arrays.stream(glassGreenSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                        .setName("&a&lTAK")
                        .build()));

        Arrays.stream(glassRedSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                        .setName("&4&lNIE")
                        .build()));


        simpleInventory.click(event -> {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;

            if (Arrays.asList(glassRedSlots).contains(event.getSlot())) {
                player.closeInventory();
                player.playSound(player, Sound.BLOCK_BARREL_CLOSE, 5, 5);
                return;
            }

            if (Arrays.asList(glassGreenSlots).contains(event.getSlot())) {
                DHAPI.removeHologram(clan.getTag());
                this.clanService.removeClan(clan);
                Bukkit.getOnlinePlayers().forEach(player1 -> {
                    ClanArmorHandler.refreshArmorPacket(player, player1);
                    ClanArmorHandler.refreshArmorPacket(player1, player);

                    BossBar bossBar = ClanCuboidBossBarManager.getBossBar(player1.getUniqueId());
                    if (bossBar == null) return;
                    bossBar.removePlayer(player1);
                    ClanCuboidBossBarManager.removeBossBar(player1.getUniqueId());
                });
                player.closeInventory();
                MessageUtil.sendTitle(player, "", "&aUsunięto klan.", 20, 50, 20);
            }
        });

        player.openInventory(inventory);
    }
}
