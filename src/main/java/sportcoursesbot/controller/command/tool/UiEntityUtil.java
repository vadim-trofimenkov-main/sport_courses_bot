package sportcoursesbot.controller.command.tool;


import sportcoursesbot.shared.entity.Course;
import sportcoursesbot.shared.entity.User;

import java.util.Collection;

public class UiEntityUtil {
    public static String coursesToShortString(Collection<Course> courses) {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (Course course : courses) {
            builder.append("/")
                    .append(++i)
                    .append(" ")
                    .append(course.getTitle())
                    .append("    Start date: ")
                    .append(course.getStartDate())
                    .append("\n");

        }
        return builder.toString();
    }

    public static String coursesToString(Course course) {
        StringBuilder builder = new StringBuilder();
        builder.append(course.getTitle())
                .append("\n")
                .append("Start date: ")
                .append(course.getStartDate())
                .append("\n")
                .append("Description: ")
                .append(course.getDescription());
        return builder.toString();
    }

    public static String userToString(Collection<User> users){
        StringBuilder builder = new StringBuilder();
        for (User user: users) {
            builder.append(user.getUsername())
                    .append(" - ")
                    .append(user.getStatus())
                    .append("\n");
        }
        return builder.toString();
    }
}
