package pl.cebula.smp.feature.disco;

import lombok.Getter;
import lombok.Setter;
import pl.cebula.smp.database.repository.Identifiable;

import java.io.Serializable;

@Setter
@Getter
public class Disco implements Serializable, Identifiable<String> {

    private String owner;
    private DiscoType discoType;

    public Disco(String owner, DiscoType discoType) {
        this.owner = owner;
        this.discoType = discoType;
    }

    @Override
    public String getId() {
        return owner;
    }


}
