package sportcoursesbot.service.course;


import sportcoursesbot.dao.DaoFactory;
import sportcoursesbot.dao.course.CourseDao;
import sportcoursesbot.shared.entity.Course;

import java.util.*;
import java.util.stream.Collectors;

public class CourseServiceImpl implements CourseService {
    private CourseDao courseDao = DaoFactory.getCourseDao();

    @Override
    public List<Course> getAllActualCourses() {
        List<Course> allCourses = courseDao.getAllCoursesShort();
        final Date curDate = new Date();

        List<Course> actualCourses = allCourses.stream()
                .filter(n -> n.getStartDate().after(curDate))
                .collect(Collectors.toList());

        Comparator<Course> comparator = Comparator.comparing(Course::getStartDate);

        comparator.thenComparing(Course::getTitle);

        Collections.sort(actualCourses, comparator);
        return actualCourses;
    }


    @Override
    public Course getFullCourse(int id) {
        return courseDao.getFullCourse(id);
    }

    @Override
    public void createCourse(Course course) {
        courseDao.createCourse(course);
    }

}
