package sportcoursesbot.service.security;


import sportcoursesbot.dao.DaoFactory;
import sportcoursesbot.dao.security.SecurityDao;
import sportcoursesbot.shared.entity.security.Role;


public class SecurityServiceImpl implements SecurityService {
    private SecurityDao securityDao = DaoFactory.getSecurityDao();

    @Override
    public Role getUserRole(Integer id) {
        String userRole = securityDao.getUserRole(id);
        Role role = Role.getByName(userRole);
        return role;
    }
}

