package sportcoursesbot.controller.base;

import lombok.Getter;
import lombok.Setter;
import sportcoursesbot.controller.command.Command;
import sportcoursesbot.shared.entity.Course;
import sportcoursesbot.shared.entity.User;

import java.util.List;


@Getter
@Setter

public class UserSession {
    private User user;
    private Command nextCommand;
    private List<Course> allCourses;
    private List<User> allUsers;

    public UserSession(User user) {
        this.user = user;
    }

    public void clearNextCommandIfExists() {
        if (nextCommand != null) {
            nextCommand = null;
        }
    }
}
