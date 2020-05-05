package sportcoursesbot.dao.config;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@EqualsAndHashCode
@ToString(exclude = "password")

abstract class Environment {
    protected String url;
    protected String username;
    protected String password;
    protected String driver;
    protected Integer poolSize;

    Environment(boolean initImmediate) {
        if (initImmediate) {
            init();
        }
    }

    Environment() {
    }

    abstract void init();
}

