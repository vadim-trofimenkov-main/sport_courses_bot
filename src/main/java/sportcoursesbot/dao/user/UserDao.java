package sportcoursesbot.dao.user;


import sportcoursesbot.shared.entity.User;

import java.util.List;

public interface UserDao {
    User getUSer(Long chatId);

    void createUser(User user);

    List<User> getAllUsers();
}
