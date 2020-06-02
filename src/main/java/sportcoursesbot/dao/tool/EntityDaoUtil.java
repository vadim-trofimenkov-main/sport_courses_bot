package sportcoursesbot.dao.tool;


import sportcoursesbot.shared.entity.Course;
import sportcoursesbot.shared.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class EntityDaoUtil {
    private static final String STATUS = "status";
    private static final String USERNAME = "username";
    private static final String CHAT_ID = "chat_id";
    private static final String ID = "id";

    public static User initUser(ResultSet rs) throws SQLException {
        String status = rs.getString(STATUS);
        String username = rs.getString(USERNAME);
        Long chatId = rs.getLong(CHAT_ID);
        int id = rs.getInt(ID);
        User user = new User(chatId, username, status);
        user.setId(id);
        return user;

    }

    public static List<User> initUsers(ResultSet rs) throws SQLException {
        List<User> users = new ArrayList<>();
        rs.beforeFirst();
        while (rs.next()) {
            User user = initUser(rs);
            users.add(user);
        }
        return users;
    }

    public static Course initCourseShort(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        Timestamp startDate = resultSet.getTimestamp("start_date");
        Course course = new Course();
        course.setId(id);
        course.setTitle(title);
        course.setStartDate(startDate);
        return course;
    }

    public static List<Course> initCoursesShort(ResultSet resultSet) throws SQLException {
        List<Course> courses = new ArrayList<>();
        resultSet.beforeFirst();
        while (resultSet.next()) {
            Course course = initCourseShort(resultSet);
            courses.add(course);
        }
        return courses;
    }


    public static Course initCourse(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        Timestamp startDate = resultSet.getTimestamp("start_date");
        String description = resultSet.getString("description");
        Integer coachesId = resultSet.getInt("coaches_id");
        Course course = new Course();
        course.setId(id);
        course.setTitle(title);
        course.setStartDate(startDate);
        course.setDescription(description);
        course.setCoachesId(coachesId);
        return course;
    }
}