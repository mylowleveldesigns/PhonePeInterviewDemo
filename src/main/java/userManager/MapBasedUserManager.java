package userManager;

import models.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapBasedUserManager implements UserManager {
    private Map<String, User> users = new ConcurrentHashMap<>();

    @Override
    public void createUser(User user) {
        users.put(user.getUserId(), user);
    }

    @Override
    public User getUser(String userId) {
        return users.get(userId);
    }
}
