
import java.io.IOException;


public class ClientSMPLogic {
    private final StreamSocketServices mySocket;

    private String message = " ";
    public ClientSMPLogic(String hostName, int portNum) throws ClientProcessExceptions {
        try {
            this.mySocket = new StreamSocketServices(hostName, portNum);

        } catch (IOException e) {
            throw new ClientProcessExceptions("Connection failed - No server found at the specified host name and port number");
        }
    }
    public String connected() throws IOException {
        message = mySocket.receiveMessage();
        if (message.startsWith("connected")) {
            return message.substring(9);
        }
        return null;
    }

    /*
    * This code is commented out because the deadlock issue for the receiving a message needs to be solved.
    * The solution is to check if the server is still connected before login the client.
    */
    /*public String checkConnection() throws IOException {
        message = mySocket.receiveMessage();
        if (message.startsWith("close")) {
            return message.substring(5);
        }
        return null;
    }*/
    public void login(String username, String password) {
        mySocket.sendLoginMessage(username, password);
    }
    public void upload(String message) {
        mySocket.sendUploadMessage(message);

    }
    public String downloadOne(String message) throws IOException {
        String success = "";
        mySocket.sendDownloadOneMessage(message);
        message = mySocket.receiveMessage();
        if (message.startsWith("noFound")) {
            return message.substring(7);
        }
        if (message.startsWith("success")) {
            success = message.substring(7);
        }
        return success;

    }

    public String downloadAll() {
        return mySocket.sendDownloadAllMessages();
    }


    //send logout message to the server and receive the logout message
    public String logout() throws IOException {
        mySocket.sendLogoutMessage();
        message = mySocket.receiveMessage();
        if (message.startsWith("lout")) {
            return message.substring(4);
        }
        return null;
    }

}
