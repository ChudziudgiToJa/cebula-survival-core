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
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 50, 52, 0, 8, 45, 53, 49
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemStackBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .toItemStack()));


        ItemStackBuilder helpItem = new ItemStackBuilder(Material.PAPER, 1)
                .setName(" ")
                .addLore(MessageUtil.smallText("&2&lKOMENDY POMOC"))
                .addLore("")
                .addLore(MessageUtil.smallText("&8• &e/market &8- &fotwiera market"))
                .addLore(MessageUtil.smallText("&8• &e/spawn &8- &fteleport na spawn"))
                .addLore(MessageUtil.smallText("&8• &e/kit &8- &fmenu zestawow"))
                .addLore(MessageUtil.smallText("&8• &e/warp &8- &fotwiera menu"))
                .addLore(MessageUtil.smallText("&8• &e/repair (ᴀʟʟ) (armor) &8- &fnapraw stwoje przedmioty"))
                .addLore(MessageUtil.smallText("&8• &e/ec &8- &fotwiera enderchest"))
                .addLore(MessageUtil.smallText("&8• &e/kosz &8- &fotwiera kosz"))
                .addLore(MessageUtil.smallText("&8• &e/vip /mvip /cebulak &8- &fDostepne rangi na serwerze"))
                .addLore(MessageUtil.smallText("&8• &e/helpop (wiadomosc) &8- &fWysyla wiadomosc do administracji"))
                .addLore(MessageUtil.smallText("&8• &e/msg (nick) (wiadomosc) &8- &fWysyla prywatna wiadomosc do gracza"))
                .addLore(MessageUtil.smallText("&8• &e/r (wiadomosc) &8- &fOdpowiada na ostatnia prywatna wiadomosc"))
                .addLore(MessageUtil.smallText("&8• &e/itemshop &8- &fOtwiera ItemShop serwera"))
                .addLore(MessageUtil.smallText("&8• &e/sethome /home &8- &fsystem domów"))

                .addLore("");

        inventory.setItem(13, helpItem.toItemStack());

        ItemStackBuilder vipItem = new ItemStackBuilder(Material.GOLDEN_CHESTPLATE, 1)
                .setName(" ")
                .addLore(MessageUtil.smallText("&2&lRANGA &fꑅ"))
                .addLore("")
                .addLore(MessageUtil.smallText("&8• &fUprawnienia:"))
                .addLore(MessageUtil.smallText("&8• &acodzienne vpn &8- &fdrop od 0.05 do 0.10"))
                .addLore(MessageUtil.smallText("&8• &adrop w afk zone &8- &f20%"))
                .addLore(MessageUtil.smallText("&8• &a/kit vip &8- &fDostep do kit vip"))
                .addLore(MessageUtil.smallText("&8• &a/repair &8- &fDostep do repair"))
                .addLore(MessageUtil.smallText("&8• &a/feed &8- &fDostep do feed"))
                .addLore(MessageUtil.smallText("&8• &a/heal &8- &fDostep do heal"))
                .addLore(MessageUtil.smallText("&8• &a/hat &8- &fMozliwosc zalozenia itemku na glowe"))
                .addLore(MessageUtil.smallText("&8• &a/ec &8- &fDostep do enderchesta"))
                .addLore(MessageUtil.smallText("&8• &a/wb &8- &fDostep do craftingu"))
                .addLore(MessageUtil.smallText("&8"))
                .addLore(MessageUtil.smallText("&8• &fmozliwosc ustawienia 2 domow"))
                .addLore(MessageUtil.smallText("&8• &fUnikalny prefix na czacie i tabliscie"))
                .addLore(MessageUtil.smallText("&8"))
                .addLore(MessageUtil.smallText("&8• &fRangę zakupisz pod &e/itemshop"))
                .addLore("");

        inventory.setItem(30, vipItem.toItemStack());

        ItemStackBuilder svipItem = new ItemStackBuilder(Material.IRON_CHESTPLATE, 1)
                .setName(" ")
                .addLore(MessageUtil.smallText("&2&lRANGA &fꑇ"))
                .addLore("")
                .addLore(MessageUtil.smallText("&8• &fUprawnienia:"))
                .addLore(MessageUtil.smallText("&8• &acodzienne vpn &8- &fdrop od 0.10 do 0.30"))
                .addLore(MessageUtil.smallText("&8• &adrop w afk zone &8- &f30%"))
                .addLore(MessageUtil.smallText("&8• &a/kit mvip &8- &fDostep do kit mvip"))
                .addLore(MessageUtil.smallText("&8• &a/kit vip &8- &fDostep do kit vip"))
                .addLore(MessageUtil.smallText("&8• &a/repair &8- &fDostep do repair"))
                .addLore(MessageUtil.smallText("&8• &a/feed &8- &fDostep do feed"))
                .addLore(MessageUtil.smallText("&8• &a/ec &8- &fDostep do enderchesta"))
                .addLore(MessageUtil.smallText("&8• &a/wb &8- &fDostep do craftingu"))
                .addLore(MessageUtil.smallText("&8• &a/heal &8- &fDostep do heal"))
                .addLore(MessageUtil.smallText("&8• &a/hat &8- &fMozliwosc zalozenia itemku na glowe"))
                .addLore(MessageUtil.smallText("&8• &a/sethome &8- &fDostep do ustawienia domu"))
                .addLore(MessageUtil.smallText("&8"))
                .addLore(MessageUtil.smallText("&8• &fMozliwosc pisania na kolorowo"))
                .addLore(MessageUtil.smallText("&8• &fUnikalny prefix na czacie i tabliscie"))
                .addLore(MessageUtil.smallText("&8• &fmozliwosc ustawienia 4 domow"))
                .addLore(MessageUtil.smallText("&8"))
                .addLore(MessageUtil.smallText("&8• &fRangę zakupisz pod &e/itemshop"))
                .addLore("");

        inventory.setItem(31, svipItem.toItemStack());

        ItemStackBuilder sponsorItem = new ItemStackBuilder(Material.DIAMOND_CHESTPLATE, 1)
                .setName(" ")
                .addLore(MessageUtil.smallText("&2&lRANGA &fꑍ"))
                .addLore("")
                .addLore(MessageUtil.smallText("&8• &fUprawnienia:"))
                .addLore(MessageUtil.smallText("&8• &acodzienne vpn &8- &fdrop od 0.30 do 0.60"))
                .addLore(MessageUtil.smallText("&8• &adrop w afk zone &8- &f40%"))
                .addLore(MessageUtil.smallText("&8• &a/kit cebulak &8- &fDostep do kit cebulak"))
                .addLore(MessageUtil.smallText("&8• &a/kit mvip &8- &fDostep do kit mvip"))
                .addLore(MessageUtil.smallText("&8• &a/kit vip &8- &fDostep do kit vip"))
                .addLore(MessageUtil.smallText("&8• &a/repair &8- &fDostep do repair"))
                .addLore(MessageUtil.smallText("&8• &a/repairall &8- &fDostep do repairall"))
                .addLore(MessageUtil.smallText("&8• &a/feed &8- &fDostep do feed"))
                .addLore(MessageUtil.smallText("&8• &a/ec &8- &fDostep do enderchesta"))
                .addLore(MessageUtil.smallText("&8• &a/wb &8- &fDostep do craftingu"))
                .addLore(MessageUtil.smallText("&8• &a/heal &8- &fDostep do heal"))
                .addLore(MessageUtil.smallText("&8• &a/nick &8- &fDostep do zmiany nicku"))
                .addLore(MessageUtil.smallText("&8• &a/hat &8- &fMozliwosc zalozenia itemku na glowe"))
                .addLore(MessageUtil.smallText("&8• &a/sethome &8- &fDostep do ustawienia domu"))
                .addLore(MessageUtil.smallText("&8"))
                .addLore(MessageUtil.smallText("&8• &fMozliwosc pisania na kolorowo"))
                .addLore(MessageUtil.smallText("&8• &fUnikalny prefix na czacie i tabliscie"))
                .addLore(MessageUtil.smallText("&8• &fmozliwosc ustawienia 6 domow"))
                .addLore(MessageUtil.smallText("&8"))
                .addLore(MessageUtil.smallText("&8• &fRangę zakupisz pod &e/itemshop"))
                .addLore("");

        inventory.setItem(32, sponsorItem.toItemStack());

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