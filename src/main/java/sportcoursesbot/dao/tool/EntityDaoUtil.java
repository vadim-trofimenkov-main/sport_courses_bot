package sportcoursesbot.dao.tool;


import sportcoursesbot.shared.entity.Course;
import sportcoursesbot.shared.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class EntityDaoUtil {
    public static User initUser(ResultSet rs) throws SQLException {
        String status = rs.getString("status");
        String username = rs.getString("username");
        Long chatId = rs.getLong("chat_id");
        return new User(chatId, username, status);
    }

    public static List<User> initUsers(ResultSet rs) throws SQLException {
        List<User> users = new ArrayList<>();
        rs.beforeFirst();
        while(rs.next()){
            User user = initUser(rs);
            users.add(user);
        }
        return users;
    }

    public static Course initCourseShort(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("Id");
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
        int id = resultSet.getInt("Id");
        String title = resultSet.getString("title");
        Timestamp startDate = resultSet.getTimestamp("start_date");
        String description = resultSet.getString("description");
        int lectureId = resultSet.getInt("lecture_id");
        Course course = new Course();
        course.setId(id);
        course.setTitle(title);
        course.setStartDate(startDate);
        course.setDescription(description);
        course.setLectureId(lectureId);
        return course;
    }
}