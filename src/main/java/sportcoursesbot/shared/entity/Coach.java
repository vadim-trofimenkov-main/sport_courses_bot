package sportcoursesbot.shared.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class Coach extends DaoEntity {
    private String name;
    private String specialization;
    private Integer experience;
}
