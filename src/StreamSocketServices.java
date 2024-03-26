
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class StreamSocketServices {

    private final SSLSocket sslSocket;
    private BufferedReader input;
    private PrintWriter output;
    private Users user;
    public StreamSocketServices(String acceptorHost, int acceptorPort) throws IOException {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        sslSocket = (SSLSocket) factory.createSocket(acceptorHost, acceptorPort);
        sslSocket.startHandshake();
        setStreams();
    }
    public StreamSocketServices(SSLSocket _sslSocket)  throws IOException {
        sslSocket = _sslSocket;
        setStreams();
    }

    //set the input and output streams
    private void setStreams() throws IOException {
        // write to the socket
        InputStream inStream = sslSocket.getInputStream();
        input = new BufferedReader(new InputStreamReader(inStream));

        // read from the socket
        OutputStream outStream = sslSocket.getOutputStream();
        output = new PrintWriter(new OutputStreamWriter(outStream));
    }


    public String receiveMessage() throws IOException {
        //sslSocket.setSoTimeout(1000);
        return input.readLine();
    }

    //sent by the server and received by the client
    public void sendConnectedMessage() {
        String connection = "Connection is established";
        output.print("connected" + connection+"\n");
        output.flush();
    }

    //sent by the client and received by the server
    public void sendLoginMessage(String username, String password) {
        user = new Users(username, password);
        String auth = user.getUsername() + " " + user.getPassword();
        output.print("login"+auth + "\n");

        output.flush();

    }

    //sent by the client and received by the server
    public void sendUploadMessage(String message) {
        output.print("upload"+ message+"\n");
        user.addMessage(message);
        output.flush();
    }

    // sent by the client and received by the server
    public void sendDownloadOneMessage(String message) {
        output.print("downloadOne" + user.getAMessage(message)+"\n");
        output.flush();
    }

    //sent by the client and received by the server
    public String sendDownloadAllMessages() {
        StringBuilder mBuilder = new StringBuilder();
        List<String> allMessages = user.getAllMessages();

        for (String mess : allMessages) {
            mBuilder.append(mess).append("\n");
        }
        String toSend = "downloadAll" + mBuilder;
        output.println(toSend);
        output.flush();

        return toSend.substring(11);
    }
    //sent by the client and received by the server
    public void sendLogoutMessage() {
        // send logout message to server
        output.print("logout\n");
        output.flush();
    }

    //sent by the server and received by the client
    public void sendLoggingOutMessage() {
        user = null;
        String lmsg = "logging out from the server";
        output.print("lout"+ lmsg + "\n");
        output.flush();
    }
    public void sendSuccessMessage() {
        String success = "Success";
        output.print("success"+ success + "\n");
        output.flush();
    }

    //sent by the server and received by the client
    public void sendMessageNoFound() {
        String notFound = "Message not found";
        output.print("noFound"+ notFound + "\n");
        output.flush();
    }
    //acknowledgement message from server when closing the connection
    /*public void sendCloseConnectionMessage() {
        String closeMsg = "Server connection is closed";
        output.print("close"+ closeMsg + "\n");
        output.flush();
    }*/

    //used by the server to close the connection
    public void closeConnection() throws IOException {
        this.sslSocket.close();
    }

}
