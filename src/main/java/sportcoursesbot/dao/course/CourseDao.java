package sportcoursesbot.dao.course;


import sportcoursesbot.shared.entity.Course;

import java.util.List;

public interface CourseDao {
    List<Course> getAllCoursesShort();

    Course getFullCourse(int id);

    void createCourse(Course course);

    void deleteCourse(int id);
}
