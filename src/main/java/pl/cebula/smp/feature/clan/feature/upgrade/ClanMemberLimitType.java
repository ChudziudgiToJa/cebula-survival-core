package pl.cebula.smp.feature.clan.feature.upgrade;

import lombok.Getter;

@Getter
public enum ClanMemberLimitType {
    SMALL(3, 0),
    NORMAL(6, 2500),
    BIG(9, 5000),
    LARGE(12, 1000);

    private final int slotLimit;
    private final double price;

    ClanMemberLimitType(int slotLimit, double price) {
        this.slotLimit = slotLimit;
        this.price = price;
    }

    public ClanMemberLimitType getNext() {
        if (this.ordinal() < ClanMemberLimitType.values().length - 1) {
            return ClanMemberLimitType.values()[this.ordinal() + 1];
        }
        return null;
    }
}
