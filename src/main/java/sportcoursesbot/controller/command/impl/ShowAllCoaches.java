package sportcoursesbot.controller.command.impl;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sportcoursesbot.controller.SportCoursesBot;
import sportcoursesbot.controller.command.Command;
import sportcoursesbot.controller.tool.UiEntityUtil;
import sportcoursesbot.controller.tool.chat.ChatUtil;
import sportcoursesbot.service.ServiceFactory;
import sportcoursesbot.service.coach.CoachService;
import sportcoursesbot.shared.entity.Coach;

import java.util.List;

public class ShowAllCoaches implements Command {
    private CoachService coachService = ServiceFactory.getCoachService();
    @Override
    public void execute(SportCoursesBot source, Update update) throws TelegramApiException {
        List<Coach> allCoaches = coachService.getAllCoaches();
        String coaches = UiEntityUtil.coachesToString(allCoaches);
        ChatUtil.sendMessage("All coaches:", update, source);
        ChatUtil.sendMessage(coaches,update,source);
    }
}
