package sportcoursesbot.service;


import sportcoursesbot.service.coach.CoachService;
import sportcoursesbot.service.coach.CoachServiceImpl;
import sportcoursesbot.service.course.CourseService;
import sportcoursesbot.service.course.CourseServiceImpl;
import sportcoursesbot.service.security.SecurityService;
import sportcoursesbot.service.security.SecurityServiceImpl;
import sportcoursesbot.service.user.UserService;
import sportcoursesbot.service.user.UserServiceImpl;

public class ServiceFactory {
    private static UserService userService = new UserServiceImpl();
    private static CourseService courseService = new CourseServiceImpl();
    private static SecurityService securityService = new SecurityServiceImpl();
    private static CoachService coachService = new CoachServiceImpl();

    public static CoachService getCoachService() {
        return coachService;
    }

    public static UserService getUserService() {
        return userService;
    }

    public static CourseService getCourseService() {
        return courseService;
    }

    public static SecurityService getSecurityService() {
        return securityService;
    }
}
