package pl.cebula.smp.feature.pet;

import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.configuration.implementation.PetConfiguration;
import pl.cebula.smp.feature.pet.object.Pet;
import pl.cebula.smp.feature.pet.object.PetData;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.MessageUtil;

import java.util.UUID;

public class PetController implements Listener {

    private final UserService userService;
    private final PetConfiguration petconfiguration;
    private final SurvivalPlugin survivalPlugin;

    public PetController(UserService userService, PetConfiguration petconfiguration, SurvivalPlugin survivalPlugin) {
        this.userService = userService;
        this.petconfiguration = petconfiguration;
        this.survivalPlugin = survivalPlugin;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        User user = this.userService.findUserByUUID(event.getPlayer().getUniqueId());
        if (user.getPetDataArrayList() == null) return;
        if (user.getPetDataArrayList().isEmpty()) return;
        if (user.isVanish()) return;
        user.getPetDataArrayList().forEach(petData -> {
            PetHologramHandler.create(event.getPlayer(), user, petData);
        });
    }


    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        User user = this.userService.findUserByUUID(player.getUniqueId());
        if (user == null) return;
        if (user.getPetDataArrayList().isEmpty()) return;
        if (user.isVanish()) return;
        user.getPetDataArrayList().forEach(petData -> {
            DHAPI.removeHologram(petData.getUuid().toString());
            PetHologramHandler.create(player, user, petData);
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        User user = this.userService.findUserByUUID(event.getPlayer().getUniqueId());
        if (user == null) return;
        user.getPetDataArrayList().forEach(petData -> {
            DHAPI.removeHologram(petData.getUuid().toString());
        });
    }


    @EventHandler
    public void onDead(PlayerDeathEvent event) {
        User user = this.userService.findUserByUUID(event.getPlayer().getUniqueId());
        Player player = event.getPlayer();
        if (user.getPetDataArrayList().isEmpty()) return;
        user.getPetDataArrayList().forEach(pet -> {
            player.getWorld().dropItemNaturally(player.getLocation(), PetUtil.createItemStackPet(pet.getPetData()));
            DHAPI.removeHologram(pet.getUuid().toString());
        });
        user.getPetDataArrayList().clear();;
    }

    @EventHandler
    public void onClickPet(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.PLAYER_HEAD || item.getItemMeta() == null) {
            return;
        }
        PetData petData = PetUtil.getPetFromItem(this.petconfiguration, event.getItem().getItemMeta().getDisplayName());

        if (petData != null) {
            event.setCancelled(true);
            User user = this.userService.findUserByUUID(player.getUniqueId());
            int petCount = user.getPetDataArrayList().size();

            if (petCount >= 2) {
                MessageUtil.sendTitle(player, "&cLimit petów", "&cNie możesz mieć więcej niż &42 &cpetów!", 20, 60, 20);
                return;
            }

            if (item.getAmount() > 1) {
                item.setAmount(item.getAmount() - 1);
            } else {
                player.getInventory().removeItem(event.getItem());
            }

            final Pet Pet = new Pet(petData, UUID.randomUUID());
            user.getPetDataArrayList().add(Pet);
            PetHologramHandler.create(player, user, Pet);
            MessageUtil.sendTitle(player, "&a", "&fPomyślnie dodano &a" + petData.getName() + "&f do Twojej kolekcji!", 20, 60, 20);
        }
    }
}
