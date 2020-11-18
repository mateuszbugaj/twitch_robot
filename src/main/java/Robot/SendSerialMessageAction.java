package Robot;

import Utils.IEnvAction;
import exception.EmptyMessageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SendSerialMessageAction implements IEnvAction<String> {
    public static Logger log = LoggerFactory.getLogger(SendSerialMessageAction.class);
    private final SerialCom serialCom;

    public SendSerialMessageAction(SerialCom serialCom) {
        this.serialCom = serialCom;
    }

    @Override
    public void execute(String message) {
        try {
            serialCom.sendMessage(message);
        } catch (IOException | EmptyMessageException e) {
            log.error(e.getMessage());
        }
    }
}
