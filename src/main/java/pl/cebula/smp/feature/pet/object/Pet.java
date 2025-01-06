package pl.cebula.smp.feature.pet.object;

import lombok.Getter;
import lombok.Setter;
import pl.cebula.smp.database.repository.Identifiable;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class Pet implements Serializable {
    private final PetData petData;
    private final UUID uuid;

    public Pet(PetData petData, UUID uuid) {
        this.petData = petData;
        this.uuid = uuid;
    }
}