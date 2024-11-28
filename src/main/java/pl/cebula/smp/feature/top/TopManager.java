package pl.cebula.smp.feature.top;

import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TopManager {

    private final UserService userService;

    public TopManager(UserService userService) {
        this.userService = userService;
    }


    public List<User> get16UsersMoneyTop() {
        return userService.usersByNickName.values().stream()
                .sorted(Comparator.comparingDouble(User::getMoney).reversed())
                .limit(16)
                .collect(Collectors.toList());
    }

    public List<User> get16UsersSpendTime() {
        return userService.usersByNickName.values().stream()
                .sorted(Comparator.comparingInt(User::getSpentTime).reversed())
                .limit(16)
                .collect(Collectors.toList());
    }
}
