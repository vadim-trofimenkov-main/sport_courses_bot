package sportcoursesbot.service.course;


import sportcoursesbot.shared.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> getAllActualCourses();

    List<Course> getAllCourses();

    Course getFullCourse(int id);

    void createCourse(Course course);

    void deleteCourse(int id);

    void editCourse(Course course);
}
