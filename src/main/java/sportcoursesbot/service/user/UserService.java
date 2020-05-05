package sportcoursesbot.service.user;


import sportcoursesbot.shared.entity.User;

import java.util.List;

public interface UserService {
    User getUser(Long chatId);

    void createUser(User user);

    List<User> getAllUsers(Long curChatId);
}
