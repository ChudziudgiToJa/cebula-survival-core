package pl.cebula.smp.feature.disco;

import pl.cebula.smp.database.DatabaseRepository;
import pl.cebula.smp.feature.clan.Clan;

public class DiscoRepository extends DatabaseRepository<String, Disco> {

    public DiscoRepository() {
        super(Disco.class, "uuid", "disco");
    }

}