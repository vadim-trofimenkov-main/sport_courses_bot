package sportcoursesbot.service.course;


import sportcoursesbot.dao.DaoFactory;
import sportcoursesbot.dao.course.CourseDao;
import sportcoursesbot.shared.entity.Course;

import java.util.*;

public class CourseServiceImpl implements CourseService {
    private CourseDao courseDao = DaoFactory.getCourseDao();

    @Override
    public List<Course> getAllActualCourses() {
        List<Course> allCourses = courseDao.getAllCoursesShort();
        List<Course> actualCourses = new ArrayList<>();
        Date date = new Date();
        for (Course course : allCourses) {
            if (course.getStartDate().after(date)) {
                actualCourses.add(course);
            }
        }
        Comparator<Course> comparator = new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
            return o1.getStartDate().compareTo(o2.getStartDate());
            }
        };
        comparator.thenComparing(new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });
        Collections.sort(actualCourses, comparator);
        return actualCourses;
    }

    @Override
    public Course getFullCourse(int id) {
        return courseDao.getFullCourse(id);
    }
}
