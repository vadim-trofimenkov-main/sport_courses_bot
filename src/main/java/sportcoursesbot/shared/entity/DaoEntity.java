package sportcoursesbot.shared.entity;

public class DaoEntity {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if (this.id != null) {
            throw new RuntimeException("id is already set");
        }
        this.id = id;
    }
}
