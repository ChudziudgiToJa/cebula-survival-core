package pl.cebula.smp.util;

import java.text.DecimalFormat;

public final class DecimalUtil {

    private static final String[] SYMBOLS = {"", "k", "m", "mld", "b", "bld", "tld"};

    private DecimalUtil() {
    }

    public static String getFormat(double amount) {
        int index = 0;

        while (amount >= 1000.0 && index < SYMBOLS.length - 1) {
            amount /= 1000.0;
            index++;
        }

        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        String symbol = SYMBOLS[index].toUpperCase();

        return decimalFormat.format(amount) + symbol;
    }
}
