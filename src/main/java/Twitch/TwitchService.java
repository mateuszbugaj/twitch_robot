package Twitch;

import com.github.twitch4j.TwitchClient;
import exception.EmptyMessageException;

public class TwitchService {
    private final TwitchClient twitchClient;
    private final String CHANNEL_NAME;

    public TwitchService(TwitchClient twitchClient, String channel_name){
        this.twitchClient = twitchClient;
        CHANNEL_NAME = channel_name;
    }

    public void sendChatMessage(String message) throws EmptyMessageException {
        if(message == null || message.isEmpty()){
            throw new EmptyMessageException("Cannot send empty chat message!");
        }

        twitchClient.getChat().sendMessage(CHANNEL_NAME, message);
    }
}
