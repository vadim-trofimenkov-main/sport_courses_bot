package sportcoursesbot.dao;


import sportcoursesbot.dao.course.CourseDao;
import sportcoursesbot.dao.course.CourseDaoImpl;
import sportcoursesbot.dao.security.SecurityDao;
import sportcoursesbot.dao.security.SecurityDaoImpl;
import sportcoursesbot.dao.user.UserDao;
import sportcoursesbot.dao.user.UserDaoImpl;

public class DaoFactory {
    private static UserDao userDao = new UserDaoImpl();
    private static CourseDao courseDao = new CourseDaoImpl();
    private static SecurityDao securityDao = new SecurityDaoImpl();

    public static UserDao getUserDao() {
        return userDao;
    }

    public static CourseDao getCourseDao() {
        return courseDao;
    }

    public static SecurityDao getSecurityDao() {
        return securityDao;
    }
}