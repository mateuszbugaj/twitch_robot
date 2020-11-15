package Twitch;

import Managment.SaveCommandAction;
import Managment.UserCommand;
import Utils.MessageToCommandsConverter;
import com.github.twitch4j.chat.events.channel.IRCMessageEvent;
import exception.InvalidUserCommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TwitchChatMessageEventHandler implements Consumer<IRCMessageEvent> {
    public static Logger log = LoggerFactory.getLogger(TwitchChatMessageEventHandler.class);
    private final MessageToCommandsConverter converter;
    private final SaveCommandAction saveCommandAction;
    private final SendChatMessageAction sendChatMessageAction;

    public TwitchChatMessageEventHandler(SaveCommandAction saveCommandAction,
                                         SendChatMessageAction sendChatMessageAction) {

        this.saveCommandAction = saveCommandAction;
        this.sendChatMessageAction = sendChatMessageAction;
        converter = new MessageToCommandsConverter();
    }

    @Override
    public void accept(IRCMessageEvent ircMessageEvent) {
        log.info("New message '" + ircMessageEvent.getMessage().get() + "' from " + ircMessageEvent.getUserName());

        String message = ircMessageEvent.getMessage().get(); // todo: there might be better to use .getRawMassage()
        if(message.startsWith("!")){
            try {
                List<UserCommand> userCommands = converter.apply(message)
                        .stream()
                        .map(command -> new UserCommand(command, ircMessageEvent.getUserName()))
                        .collect(Collectors.toList());

                userCommands.forEach(saveCommandAction::execute);
            } catch (InvalidUserCommandException e) {
                log.error(e.getMessage());
                sendChatMessageAction.execute(e.getMessage());
            }
        }
    }
}
