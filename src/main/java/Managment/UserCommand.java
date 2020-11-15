package Managment;

public class UserCommand {
    private final String content;
    private final String userName;

    public UserCommand(String content, String userName) {
        this.content = content;
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "UserCommand{" +
                "content='" + content + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
