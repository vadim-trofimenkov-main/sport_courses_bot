package sportcoursesbot.service.security;

import sportcoursesbot.shared.entity.security.Role;

public interface SecurityService {
    Role getUserRole(Integer id);
}
