package pl.cebula.smp.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import pl.cebula.smp.feature.voucher.Voucher;
import pl.cebula.smp.util.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

public class VoucherConfiguration extends OkaeriConfig {

    public ArrayList<Voucher> voucherArrayList = new ArrayList<>();

}
