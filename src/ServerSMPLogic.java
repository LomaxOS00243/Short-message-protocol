import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLContext;
import javax.net.ssl.KeyManagerFactory;
import java.io.IOException;
import java.io.FileInputStream;
import java.security.KeyStore;
import javax.net.ssl.SSLServerSocketFactory;


public class ServerSMPLogic {

    private StreamSocketServices mySocket;
    private SSLServerSocket mySSLConnectionSocket;
    public ServerSMPLogic() {}

    //Create a SSL server socket
    private SSLContext configSecureSocket() throws Exception {
        String ksName = "smpkeystore.jks";
        char[] ksPass = "mtu123".toCharArray();
        char[] ctPass = "mtu123".toCharArray();
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream(ksName), ksPass);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, ctPass);
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(kmf.getKeyManagers(), null, null);
        return sc;

    }
    //listen for the connection on port 17
    public void setupConnection() throws Exception {

        SSLServerSocketFactory ssf = configSecureSocket().getServerSocketFactory();
        mySSLConnectionSocket = (SSLServerSocket) ssf.createServerSocket(17);
        printServerSocketInfo(mySSLConnectionSocket);

    }

    //accept the connection
    public void acceptConnection() {
        try {
            mySocket = new StreamSocketServices((SSLSocket) mySSLConnectionSocket.accept());
            mySocket.sendConnectedMessage();

        } catch (IOException e) {
            System.out.println("Error during handshake or connection acceptance"); //Handle Handshake or connection acceptance error
        }

    }
    /*public void sendCloseConnectionMessage() {
        mySocket.sendCloseConnectionMessage();
    }*/
    //close the connection socket
    public void closeConnection()  {

        try {
            if (mySocket != null) {
                mySocket.close();
                mySocket.closeConnection();
            }
            if (mySSLConnectionSocket != null && !mySSLConnectionSocket.isClosed()) {
                mySSLConnectionSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //return the socket
    public StreamSocketServices getSocket() {
        return mySocket;
    }

    private static void printServerSocketInfo(SSLServerSocket s) {
        System.out.println("Server socket class: "+s.getClass());
        System.out.println("   Socket address = "
                +s.getInetAddress().toString());
        System.out.println("   Socket port = "
                +s.getLocalPort());
        System.out.println("   Need client authentication = "
                +s.getNeedClientAuth());
        System.out.println("   Want client authentication = "
                +s.getWantClientAuth());
        System.out.println("   Use client mode = "
                +s.getUseClientMode());
    }


}
