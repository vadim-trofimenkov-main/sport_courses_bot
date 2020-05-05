package sportcoursesbot.shared.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course extends DaoEntity {
    private String title;
    private String description;
    private Timestamp startDate;
    private Integer lectureId;
}
