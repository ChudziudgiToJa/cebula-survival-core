package pl.cebula.smp.feature.voucher;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.configuration.implementation.VoucherConfiguration;
import pl.cebula.smp.util.ItemStackSerializable;
import pl.cebula.smp.util.MessageUtil;
import pl.cebula.smp.util.SimpleInventory;

import java.util.Objects;

public class VoucherInventory {

    private final SurvivalPlugin survivalPlugin;
    private final VoucherConfiguration voucherConfiguration;


    public VoucherInventory(SurvivalPlugin survivalPlugin, VoucherConfiguration voucherConfiguration) {
        this.survivalPlugin = survivalPlugin;
        this.voucherConfiguration = voucherConfiguration;
    }

    public void show(final Player player) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 3, MessageUtil.smallText("&fvouchery:"));
        Inventory inventory = simpleInventory.getInventory();

        for (Voucher voucher : this.voucherConfiguration.voucherArrayList) {
            inventory.addItem(Objects.requireNonNull(ItemStackSerializable.readItemStack(voucher.getItemStackString())));
        }

        player.openInventory(inventory);
    }
}
