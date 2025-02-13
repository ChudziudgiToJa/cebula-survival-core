package pl.cebula.smp.feature.disco;

import pl.cebula.smp.database.UpdateType;
import pl.cebula.smp.feature.clan.Clan;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DiscoService {

    private final DiscoRepository discoRepository;
    private final Map<String, Disco> discoConcurrentHashMap = new ConcurrentHashMap<>();

    public DiscoService(DiscoRepository discoRepository) {
        this.discoRepository = discoRepository;
    }

    public Disco findByUuid(String uuid) {
        return this.discoConcurrentHashMap.values()
                .stream()
                .filter(disco -> disco.getOwner().contains(uuid))
                .findFirst()
                .orElse(null);
    }

    public void add(Disco clan) {
        this.discoConcurrentHashMap.put(clan.getId(), clan);
    }

    public void create(Disco disco) {
        this.discoConcurrentHashMap.put(disco.getId(), disco);
        discoRepository.update(disco, disco.getId(), UpdateType.CREATE);
    }

    public void save(Disco disco) {
        discoRepository.update(disco, disco.getId(), UpdateType.UPDATE);
    }

    public void remove(Disco clan) {
        this.discoConcurrentHashMap.remove(clan.getId());
        discoRepository.update(clan, clan.getId(), UpdateType.REMOVE);
    }

    public void saveAll() {
        this.discoConcurrentHashMap.forEach((s, disco) -> {
                    save(disco);
                }
        );
    }
}
