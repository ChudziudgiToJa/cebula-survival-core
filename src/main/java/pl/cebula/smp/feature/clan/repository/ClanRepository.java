package pl.cebula.smp.feature.clan.repository;

import pl.cebula.smp.database.DatabaseRepository;
import pl.cebula.smp.feature.clan.Clan;

public class ClanRepository extends DatabaseRepository<String, Clan> {

    public ClanRepository() {
        super(Clan.class, "uuid", "clans");
    }

}
