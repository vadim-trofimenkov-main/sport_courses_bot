package sportcoursesbot.service.course;


import sportcoursesbot.shared.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> getAllActualCourses();
    Course getFullCourse(int id);
    void createCourse(Course course);
}
