package Twitch;

import Utils.MessageToCommandsConverter;
import com.github.twitch4j.chat.events.channel.IRCMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class TwitchChatMessageEventHandler implements Consumer<IRCMessageEvent> {
    public static Logger log = LoggerFactory.getLogger(TwitchChatMessageEventHandler.class);
    private final MessageToCommandsConverter converter;

    public TwitchChatMessageEventHandler() {
        converter = new MessageToCommandsConverter();
    }


    @Override
    public void accept(IRCMessageEvent ircMessageEvent) {
        log.info("New message '" + ircMessageEvent.getMessage().get() + "' from " + ircMessageEvent.getUserName());

//        ircMessageEvent
//                .getMessage()
//                .filter(m -> m.startsWith("!"))
//                .map(converter)
//                .ifPresent(commands -> {});
    }
}
