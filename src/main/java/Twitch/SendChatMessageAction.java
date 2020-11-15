package Twitch;

import Utils.IEnvAction;
import exception.EmptyChatMessageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendChatMessageAction implements IEnvAction<String > {
    public static Logger log = LoggerFactory.getLogger(SendChatMessageAction.class);

    private final TwitchService twitchService;

    public SendChatMessageAction(TwitchService twitchService) {
        this.twitchService = twitchService;
    }

    @Override
    public void execute(String message) {
        try {
            twitchService.sendChatMessage(message);
        } catch (EmptyChatMessageException e) {
            log.error(e.getMessage());
        }
    }
}
