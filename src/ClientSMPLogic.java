
import java.io.IOException;


public class ClientSMPLogic {
    private final StreamSocketServices mySocket;
    public ClientSMPLogic(String hostName, int portNum) throws ClientProcessExceptions {
        try {
            this.mySocket = new StreamSocketServices(hostName, portNum);

        } catch (IOException e) {
            throw new ClientProcessExceptions("Connection failed - No server found at the specified host name and port number");
        }
    }
    public String getConnected() throws IOException {
        return mySocket.receiveMessage();
    }
    public void login(String username, String password) {
        mySocket.login(username, password);
    }
    public void uploadMessage(String message) {
        mySocket.uploadMessage(message);
    }
    public void downloadAMessage(String message) {
        mySocket.downloadAMessage(message);
    }

    public String downloadAll() {
        return mySocket.downloadAllMessages();
    }


    public String logout() throws IOException {
        mySocket.logout();
        String logoutMsg = mySocket.receiveMessage();
        if (logoutMsg.startsWith("lout")) {
            return logoutMsg.substring(4);
        }
        return null;
    }

}