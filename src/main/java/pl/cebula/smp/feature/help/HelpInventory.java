package pl.cebula.smp.feature.help;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.util.ItemBuilder;
import pl.cebula.smp.util.ItemStackBuilder;
import pl.cebula.smp.util.MessageUtil;
import pl.cebula.smp.util.SimpleInventory;

import java.util.Arrays;

public class HelpInventory {

    private final SurvivalPlugin survivalPlugin;

    public HelpInventory(SurvivalPlugin survivalPlugin) {
        this.survivalPlugin = survivalPlugin;
    }

    public void show(final Player player) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 54, MessageUtil.smallTextToColor("&6&lPOMOC"));
        Inventory inventory = simpleInventory.getInventory();


        Integer[] glassBlueSlots = new Integer[]{
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51
        };
        Integer[] glassBlackSlots = new Integer[]{
                2, 4, 6, 18, 26, 36, 44, 46, 48, 50, 52
        };
        Integer[] glassWhiteSlots = new Integer[]{
                0, 8, 45, 53
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemStackBuilder(Material.BLUE_STAINED_GLASS_PANE)
                        .setName(" ")
                        .toItemStack()));

        Arrays.stream(glassBlackSlots).forEach(slot -> inventory.setItem(slot,
                new ItemStackBuilder(Material.BLACK_STAINED_GLASS_PANE)
                        .setName(" ")
                        .toItemStack()));

        Arrays.stream(glassWhiteSlots).forEach(slot -> inventory.setItem(slot,
                new ItemStackBuilder(Material.WHITE_STAINED_GLASS_PANE)
                        .setName(" ")
                        .toItemStack()));


        ItemStackBuilder helpItem = new ItemStackBuilder(Material.PAPER, 1)
                .setName(" ")
                .addLore(MessageUtil.smallText("&9&lKOMENDY POMOC"))
                .addLore("")
                .addLore(MessageUtil.smallText("&8• &3/market &8- &fotwiera market"))
                .addLore(MessageUtil.smallText("&8• &3/spawn &8- &fteleport na spawn"))
                .addLore(MessageUtil.smallText("&8• &3/kit &8- &fmenu zestawow"))
                .addLore(MessageUtil.smallText("&8• &3/warp &8- &fotwiera menu"))
                .addLore(MessageUtil.smallText("&8• &3/repair (ᴀʟʟ) (armor) &8- &fnapraw stwoje przedmioty"))
                .addLore(MessageUtil.smallText("&8• &3/ec &8- &fotwiera enderchest"))
                .addLore(MessageUtil.smallText("&8• &3/kosz &8- &fotwiera kosz"))
                .addLore(MessageUtil.smallText("&8• &3/rangi &8- &fDostepne rangi na serwerze"))
                .addLore(MessageUtil.smallText("&8• &3/helpop (wiadomosc) &8- &fWysyla wiadomosc do administracji"))
                .addLore(MessageUtil.smallText("&8• &3/msg (nick) (wiadomosc) &8- &fWysyla prywatna wiadomosc do gracza"))
                .addLore(MessageUtil.smallText("&8• &3/r (wiadomosc) &8- &fOdpowiada na ostatnia prywatna wiadomosc"))
                .addLore(MessageUtil.smallText("&8• &3/wymiana (nick) &8- &fWysyla prosbe o wymiane do innego gracza"))
                .addLore(MessageUtil.smallText("&8• &3/portfel &8- &fOtwiera ItemShop serwera"))
                .addLore("")
                .addLore(MessageUtil.smallText("&8• &fStrona: &bwww.paymc.pl"))
                .addLore(MessageUtil.smallText("&8• &fDiscord: &bdc.paymc.pl"))
                .addLore(MessageUtil.smallText("&8• &fFanpage: &bfb.paymc.pl"))
                .addLore("");

        inventory.setItem(12, helpItem.toItemStack());

        ItemStackBuilder vipItem = new ItemStackBuilder(Material.GOLDEN_CHESTPLATE, 1)
                .setName(" ")
                .addLore(MessageUtil.smallText("&9&lRANGA &e&lVIP"))
                .addLore("")
                .addLore(MessageUtil.smallText("&8• &fUprawnienia:"))
                .addLore(MessageUtil.smallText("&8• &3/kit vip &8- &fDostep do kit vip"))
                .addLore(MessageUtil.smallText("&8• &3/repair &8- &fDostep do repair"))
                .addLore(MessageUtil.smallText("&8• &3/feed &8- &fDostep do feed"))
                .addLore(MessageUtil.smallText("&8• &3/heal &8- &fDostep do heal"))
                .addLore(MessageUtil.smallText("&8• &3/hat &8- &fMozliwosc zalozenia itemku na glowe"))
                .addLore(MessageUtil.smallText("&8• &3/ec &8- &fDostep do enderchesta"))
                .addLore(MessageUtil.smallText("&8• &3/wb &8- &fDostep do craftingu"))
                .addLore(MessageUtil.smallText("&8• &3/sethome &8- &fDostep do ustawienia domu"))
                .addLore(MessageUtil.smallText("&8"))
                .addLore(MessageUtil.smallText("&8• &fmozliwosc ustawienia 2 domow"))
                .addLore(MessageUtil.smallText("&8• &fUnikalny prefix na czacie i tabliscie"))
                .addLore(MessageUtil.smallText("&8"))
                .addLore(MessageUtil.smallText("&8• &fRangę zakupisz pod &3/portfel"))
                .addLore("");

        inventory.setItem(31, vipItem.toItemStack());

        ItemStackBuilder svipItem = new ItemStackBuilder(Material.IRON_CHESTPLATE, 1)
                .setName(" ")
                .addLore(MessageUtil.smallText("&9&lRANGA &6&lM&e&lVIP"))
                .addLore("")
                .addLore(MessageUtil.smallText("&8• &fUprawnienia:"))
                .addLore(MessageUtil.smallText("&8• &3/kit mvip &8- &fDostep do kit mvip"))
                .addLore(MessageUtil.smallText("&8• &3/kit vip &8- &fDostep do kit vip"))
                .addLore(MessageUtil.smallText("&8• &3/repair &8- &fDostep do repair"))
                .addLore(MessageUtil.smallText("&8• &3/feed &8- &fDostep do feed"))
                .addLore(MessageUtil.smallText("&8• &3/ec &8- &fDostep do enderchesta"))
                .addLore(MessageUtil.smallText("&8• &3/wb &8- &fDostep do craftingu"))
                .addLore(MessageUtil.smallText("&8• &3/heal &8- &fDostep do heal"))
                .addLore(MessageUtil.smallText("&8• &3/hat &8- &fMozliwosc zalozenia itemku na glowe"))
                .addLore(MessageUtil.smallText("&8• &3/sethome &8- &fDostep do ustawienia domu"))
                .addLore(MessageUtil.smallText("&8"))
                .addLore(MessageUtil.smallText("&8• &fMozliwosc pisania na kolorowo"))
                .addLore(MessageUtil.smallText("&8• &fUnikalny prefix na czacie i tabliscie"))
                .addLore(MessageUtil.smallText("&8• &fmozliwosc ustawienia 4 domow"))
                .addLore(MessageUtil.smallText("&8"))
                .addLore(MessageUtil.smallText("&8• &fRangę zakupisz pod &3/portfel"))
                .addLore("");

        inventory.setItem(32, svipItem.toItemStack());

        ItemStackBuilder sponsorItem = new ItemStackBuilder(Material.DIAMOND_CHESTPLATE, 1)
                .setName(" ")
                .addLore(MessageUtil.smallText("&9&lRANGA &6&lCEBULAK"))
                .addLore("")
                .addLore(MessageUtil.smallText("&8• &fUprawnienia:"))
                .addLore(MessageUtil.smallText("&8• &3/kit cebulak &8- &fDostep do kit sponsor"))
                .addLore(MessageUtil.smallText("&8• &3/kit mvip &8- &fDostep do kit mvip"))
                .addLore(MessageUtil.smallText("&8• &3/kit vip &8- &fDostep do kit vip"))
                .addLore(MessageUtil.smallText("&8• &3/repair &8- &fDostep do repair"))
                .addLore(MessageUtil.smallText("&8• &3/repairall &8- &fDostep do repairall"))
                .addLore(MessageUtil.smallText("&8• &3/feed &8- &fDostep do feed"))
                .addLore(MessageUtil.smallText("&8• &3/ec &8- &fDostep do enderchesta"))
                .addLore(MessageUtil.smallText("&8• &3/wb &8- &fDostep do craftingu"))
                .addLore(MessageUtil.smallText("&8• &3/heal &8- &fDostep do heal"))
                .addLore(MessageUtil.smallText("&8• &3/nick &8- &fDostep do zmiany nicku"))
                .addLore(MessageUtil.smallText("&8• &3/hat &8- &fMozliwosc zalozenia itemku na glowe"))
                .addLore(MessageUtil.smallText("&8• &3/sethome &8- &fDostep do ustawienia domu"))
                .addLore(MessageUtil.smallText("&8"))
                .addLore(MessageUtil.smallText("&8• &fMozliwosc pisania na kolorowo"))
                .addLore(MessageUtil.smallText("&8• &fUnikalny prefix na czacie i tabliscie"))
                .addLore(MessageUtil.smallText("&8• &fmozliwosc ustawienia 6 domow"))
                .addLore(MessageUtil.smallText("&8"))
                .addLore(MessageUtil.smallText("&8• &fRangę zakupisz pod &3/portfel"))
                .addLore("");

        inventory.setItem(33, sponsorItem.toItemStack());

        inventory.setItem(49, new ItemBuilder(Material.BARRIER)
                .setTitle(MessageUtil.smallText("&czamknij"))
                .build());

        simpleInventory.click(event -> {
            event.setCancelled(true);
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;

            if (event.getSlot() == 49) {
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 5, 5);
                return;
            }

        });

        player.openInventory(inventory);
    }
}