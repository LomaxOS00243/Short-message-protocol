import javax.swing.JTextArea;


public class ServerSMPThread implements Runnable {
    private final StreamSocketServices myDataSocket;

    private final JTextArea textArea;

    public ServerSMPThread(StreamSocketServices myDataSocket, JTextArea textArea) {
        this.myDataSocket = myDataSocket;
        this.textArea = textArea;
    }
    public void run( ) {
        boolean done = false;
        String message;

        try {
            while (!done) {
                message = myDataSocket.receiveMessage();
                //append username and password if login
                if (message.startsWith("login")) {
                    textArea.setText("");
                    textArea.append("Successfully login: " + message.substring(5) + "\n");

                }
                //append message if upload
                if (message.startsWith("upload")) {

                     textArea.setText("");
                     textArea.append("Successfully uploaded: " + message.substring(6) + "\n");

                }
                if (message.startsWith("download")) {
                    textArea.append("Successfully downloaded: " + message.substring(8) + "\n");
                }
                if (message.startsWith("getAll")) {
                    textArea.setText("");
                    textArea.append("All messages \n" + message.substring(5) + "\n");
                }
                //finish session if logout
                if ((message.trim()).equals ("logout")) {
                    myDataSocket.loggingOut();
                    textArea.append("Session over" + "\n");
                    myDataSocket.close( );
                    done = true;
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception caught in thread: " + ex);
        }
    }
}
