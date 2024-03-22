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
    private void setStreams() throws IOException {
        // write to the socket
        InputStream inStream = sslSocket.getInputStream();
        input = new BufferedReader(new InputStreamReader(inStream));

        // read from the socket
        OutputStream outStream = sslSocket.getOutputStream();
        output = new PrintWriter(new OutputStreamWriter(outStream));
    }
    public String receiveMessage() throws IOException {
        return input.readLine();
    }
    //connect to the server
    public void connect() {
        output.print("Connection is established \n");
        output.flush();
    }

    public void login(String username, String password) {
        user = new Users(username, password);
        String auth = user.getUsername() + " " + user.getPassword();
        output.print("login"+auth + "\n");

        output.flush();

    }

    //logout method
    public void logout() {
        // send logout message to server
        output.print("logout\n");
        output.flush();
    }
    public void loggingOut() {
        user = null;
        String lmsg = "logging out from the server";
        output.print("lout"+ lmsg + "\n");
        output.flush();
    }

    public void uploadMessage(String message) {
        output.print("upload"+ message+"\n");
        user.addMessage(message);
        output.flush();
    }

    // send a download message to server
    public void downloadAMessage(String message) {
        output.print("downloadOne" + user.getAMessage(message)+"\n");
        output.flush();
    }
    public String downloadAllMessages() {
        StringBuilder mBuilder = new StringBuilder();
        List<String> allMessages = user.getAllMessages();

        for (String mess : allMessages) {
            mBuilder.append(mess).append("\n");
        }
        output.println("downloadAll"+ mBuilder.toString());
        output.flush();
        return mBuilder.toString();
    }

    public void close() throws IOException {
        this.sslSocket.close();
    }
}
