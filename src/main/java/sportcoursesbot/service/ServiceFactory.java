package sportcoursesbot.service;


import sportcoursesbot.service.course.CourseService;
import sportcoursesbot.service.course.CourseServiceImpl;
import sportcoursesbot.service.user.UserService;
import sportcoursesbot.service.user.UserServiceImpl;

public class ServiceFactory {
    private static UserService userService = new UserServiceImpl();
    private static CourseService courseService = new CourseServiceImpl();

    public static UserService getUserService() { return userService; }

    public static CourseService getCourseService() { return courseService; }
}
