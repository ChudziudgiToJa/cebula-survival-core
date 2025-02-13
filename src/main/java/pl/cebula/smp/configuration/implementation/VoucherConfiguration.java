package pl.cebula.smp.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import pl.cebula.smp.feature.voucher.Voucher;

import java.util.ArrayList;

public class VoucherConfiguration extends OkaeriConfig {

    public ArrayList<Voucher> voucherArrayList = new ArrayList<>();
}
