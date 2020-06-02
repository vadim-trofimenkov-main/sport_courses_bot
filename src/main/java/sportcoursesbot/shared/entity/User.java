package sportcoursesbot.shared.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sportcoursesbot.shared.entity.security.Role;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class User extends DaoEntity {
    private Long chatId;
    private String username;
    private String status;
    private Role role;

    public User(Long chatId, String username, String status) {
        this.chatId = chatId;
        this.username = username;
        this.status = status;
        role = Role.getByName(status);
    }

    public Role getRole() {
        if (role == null) {
            role = Role.getByName(status);
        }
        return role;
    }
}
