import java.util.ArrayList;
import java.util.List;

public class Users {
    private final String username;
    private final String password;
    private final List<String> message;

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
        this.message = new ArrayList<>();
    }
    //getter and setter methods
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public void addMessage(String message) {
        this.message.add(message);
    }
    public String getAMessage(String _msg) {
        String messageToReturn = "";
        for (String aMessage : this.message) {
            if (aMessage.equals(_msg)){
                messageToReturn = aMessage;
            }
        }
        return messageToReturn;
    }
    public List<String> getAllMessages() {
        return this.message;
    }
}
