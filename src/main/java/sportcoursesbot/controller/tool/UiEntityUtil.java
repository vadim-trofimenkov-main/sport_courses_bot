package sportcoursesbot.controller.tool;


import sportcoursesbot.shared.entity.Course;
import sportcoursesbot.shared.entity.User;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class UiEntityUtil {

    public static String courseToFullString(Course course) {
        StringBuilder builder = new StringBuilder();
        builder.append(course.getTitle())
                .append("\n")
                .append("Start date: ")
                .append(course.getStartDate())
                .append("\n")
                .append(course.getDescription());
        return builder.toString();
    }

    public static String courseToShortString(Course course) {
        StringBuilder builder = new StringBuilder();
        Timestamp startDate = course.getStartDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        Date date = new Date(startDate.getTime());
        String formatDate = dateFormat.format(date);

        builder.append(course.getTitle())
                .append("\tStart: ")
                .append(formatDate);
        return builder.toString();
    }

    public static String usersToString(Collection<User> users) {
        StringBuilder builder = new StringBuilder();
        for (User user : users) {
            builder.append(user.getUsername())
                    .append(" - ")
                    .append(user.getStatus())
                    .append("\n");
        }
        return builder.toString();
    }
}

