package sportcoursesbot.shared.entity.security;

import java.util.*;

public enum Role {
    ADMIN(2, Arrays.asList(Feature.BLOCK_SIMPLE_USER, Feature.EDIT_SIMPLE_USER, Feature.VIEW_USERS)),
    SIMPLE_USER(1, new ArrayList<>()),
    SUPER_ADMIN(999, new ArrayList<>()),
    BLOCKED(-1, new ArrayList<>());

    private Integer compare;
    private List<Feature> features;

    Role(Integer compare, List<Feature> features) {
        this.compare = compare;
        this.features = features;
    }

    public Integer getCompare() {
        return compare;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    private static Map<String, Role> ROLE_NAMES = new HashMap<>();

    public static Role getByName(String name) {
        return ROLE_NAMES.get(name);
    }

    static {
        ROLE_NAMES.put("admin", ADMIN);
        ROLE_NAMES.put("simple", SIMPLE_USER);
        ROLE_NAMES.put("super_admin", SUPER_ADMIN);
        ROLE_NAMES.put("blocked", BLOCKED);
    }
}
