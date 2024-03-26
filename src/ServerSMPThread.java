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
                if (message.startsWith("downloadOne")) {
                    //check if message is downloaded is empty
                    if (message.substring(11).isEmpty()) {
                        textArea.setText("");
                        textArea.append("No message found" + "\n");
                        myDataSocket.sendMessageNoFound();
                    }
                    else {
                        textArea.setText("");
                        textArea.append("Successfully downloaded: " + message.substring(11) + "\n");
                        myDataSocket.sendSuccessMessage();
                    }
                }
                if (message.startsWith("downloadAll")) {
                    textArea.setText("");
                    textArea.append("Successfully downloaded all messages: " + message.substring(11) + "\n");
                }
                //finish session if logout
                if (message.startsWith("logout")) {
                    myDataSocket.sendLoggingOutMessage();
                    textArea.append("Session over" + "\n");
                    myDataSocket.closeConnection( );
                    done = true;
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception caught in thread: " + ex);
        }
    }
}
