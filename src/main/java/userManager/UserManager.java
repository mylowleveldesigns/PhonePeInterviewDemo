package userManager;

import models.User;

public interface UserManager {
    void createUser(User user);
    User getUser(String userId);
}
