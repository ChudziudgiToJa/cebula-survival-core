package pl.cebula.smp.feature.user;

import pl.cebula.smp.database.UpdateType;
import pl.cebula.smp.feature.user.repository.UserRepository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserService {
    private final UserRepository userRepository;

    public final Map<UUID, User> userConcurrentHashMap = new ConcurrentHashMap<>();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user) {
        this.userConcurrentHashMap.put(UUID.fromString(user.getUuid()), user);
    }

    public void createUser(User user){
        this.userConcurrentHashMap.put(UUID.fromString(user.getUuid()), user);
        userRepository.update(user, user.getId(), UpdateType.CREATE);
    }

    public void saveUser(User user){
        userRepository.update(user, user.getId(), UpdateType.UPDATE);
    }

    public void removeUser(User user) {
        this.userConcurrentHashMap.remove(user.getId());
    }

    public User findUserByNickName(String nickName){
        return this.userConcurrentHashMap.values()
                .stream()
                .filter(user -> user.getNickName().contains(nickName))
                .findFirst()
                .orElse(null);
    }

    public User findUserByUUID(UUID uuid){
        return this.userConcurrentHashMap.get(uuid);
    }

    public Map<UUID, User> getUsersByNickname() {
        return this.userConcurrentHashMap;
    }
}
