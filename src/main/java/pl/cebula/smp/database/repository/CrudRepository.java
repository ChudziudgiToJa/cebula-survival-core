package pl.cebula.smp.database.repository;

import pl.cebula.smp.database.UpdateType;

import java.util.Collection;

public interface CrudRepository<ID, T extends Identifiable<ID>> extends Repository<ID, T> {

    T find(ID id);

    void update(T object, ID field, UpdateType updateType);

    boolean delete(T object);

    Collection<T> saveAll(Collection<T> objects);

    Collection<T> findAll();

}
