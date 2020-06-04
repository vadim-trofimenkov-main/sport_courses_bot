package sportcoursesbot.service.coach;

import sportcoursesbot.dao.DaoFactory;
import sportcoursesbot.dao.coach.CoachDao;
import sportcoursesbot.shared.entity.Coach;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CoachServiceImpl implements CoachService {
    private CoachDao coachDao = DaoFactory.getCoachDao();

    @Override
    public List<Coach> getAllCoaches() {
        List<Coach> allCoaches = coachDao.getAllCoaches();
        Collections.sort(allCoaches, new Comparator<Coach>() {
            @Override
            public int compare(Coach o1, Coach o2) {
                return o1.getExperience().compareTo(o2.getExperience());
            }
        });
        return allCoaches;
    }
}
