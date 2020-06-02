package sportcoursesbot.shared.tool;


import sportcoursesbot.shared.entity.User;
import sportcoursesbot.shared.entity.security.Feature;
import sportcoursesbot.shared.entity.security.Role;

import java.util.List;

public class SecurityUtil {
    public static boolean hasFeature(User user, Feature feature) {
        Role role = user.getRole();
        if (role.equals(Role.SUPER_ADMIN)) {
            return true;
        } else if (role.equals(Role.BLOCKED)) {
            return false;
        } else {
            List<Feature> features = role.getFeatures();
            return features.contains(feature);
        }
    }
}
