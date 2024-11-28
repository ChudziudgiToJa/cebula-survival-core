package pl.cebula.smp.feature.user.repository;

import pl.cebula.smp.database.DatabaseRepository;
import pl.cebula.smp.feature.user.User;

import java.util.UUID;

public class UserRepository extends DatabaseRepository<String, User> {

    public UserRepository() {
        super(User.class, "uuid", "users");
    }
}
