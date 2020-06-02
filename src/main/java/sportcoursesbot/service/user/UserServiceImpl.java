package sportcoursesbot.service.user;



import sportcoursesbot.dao.DaoFactory;
import sportcoursesbot.dao.user.UserDao;
import sportcoursesbot.shared.entity.User;
import sportcoursesbot.shared.entity.security.Feature;
import sportcoursesbot.shared.exception.PermissionDeniedException;
import sportcoursesbot.shared.exception.UserFriendlyException;
import sportcoursesbot.shared.tool.SecurityUtil;
import sportcoursesbot.shared.tool.validate.Validator;
import sportcoursesbot.shared.tool.validate.ValidatorWrapper;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao = DaoFactory.getUserDao();

    @Override
    public User getUser(Long chatId) {
        User user = userDao.getUser(chatId);
        return user;
    }

    @Override
    public void createUser(User user) {
        ValidatorWrapper<User> validatorWrapper = new ValidatorWrapper<>((validateUser) -> {
            String username = validateUser.getUsername();
            return !username.isEmpty() && username.length() < 50 && !username.startsWith("/");
        }, "Incorrect username");
        validatorWrapper.validateWithThrow(user);


        Validator<User> userValidator = (validateUser) -> !validateUser.getUsername().isEmpty() && validateUser.getUsername().length() < 50;
        boolean validate = userValidator.validate(user);
        if (validate) {
            userDao.createUser(user);
        } else {
            throw new UserFriendlyException("Incorrect username");
        }
    }

    @Override
    public List<User> getAllUsers(Long curChatId) {
        User user = getUser(curChatId);
        if (!SecurityUtil.hasFeature(user, Feature.VIEW_USERS)) {
            throw new PermissionDeniedException();
        }
        List<User> allUsers = userDao.getAllUsers();
        Collections.sort(allUsers, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                Integer compare1 = o1.getRole().getCompare();
                Integer compare2 = o2.getRole().getCompare();
                return compare1.compareTo(compare2);
            }
        });
        return allUsers;
    }
}
