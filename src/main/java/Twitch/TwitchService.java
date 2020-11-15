package Twitch;

import com.github.twitch4j.TwitchClient;
import exception.EmptyChatMessageException;

public class TwitchService {
    private final TwitchClient twitchClient;
    private final String CHANNEL_NAME;

    public TwitchService(TwitchClient twitchClient, String channel_name){
        this.twitchClient = twitchClient;
        CHANNEL_NAME = channel_name;
    }

    public void sendChatMessage(String content) throws EmptyChatMessageException {
        if(content == null || content.isEmpty()){
            throw new EmptyChatMessageException("Cannot send empty message!");
        }

        twitchClient.getChat().sendMessage(CHANNEL_NAME, content);
    }
}
