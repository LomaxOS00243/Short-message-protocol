
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientSMPForm {
    private JPanel clientPanel1;
    private JPanel requestConnectionPanel;
    private JTextField hostAdd;
    private JButton connectBtn;
    private JTextField portNumber;
    private JTextField username;
    private JTextField password;
    private JButton loginBtn;
    private JPanel clientAuthPanel;
    private JTextField uploadMessage;
    private JButton uploadBtn;
    private JTextField downloadMessage;
    private JButton downloadBtn;
    private JPanel clientMessagePanel;
    private JButton logout;
    private JTextArea allMessage;
    private JButton downloadAll;

    private ClientSMPLogic clientSTP;


    public ClientSMPForm() {
        $$$setupUI$$$();
        //connect to the server
        connectBtn.addActionListener(e -> {
            System.setProperty("jdk.tls.server.protocols", "TLSv1.2");
            System.setProperty("javax.net.ssl.trustStore", "smptruststore.jks");
            System.setProperty("javax.net.ssl.trustStorePassword", "mtu123");

            //check if host address and port number are empty
            if (hostAdd.getText().isEmpty() || portNumber.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter host address and port number", "Connection", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                clientSTP = new ClientSMPLogic(hostAdd.getText(), Integer.parseInt(portNumber.getText()));

                JOptionPane.showMessageDialog(null, clientSTP.getConnected() + " in " + hostAdd.getText() + " on port " + portNumber.getText(), "Connection", JOptionPane.INFORMATION_MESSAGE);
                clientAuthPanel.setVisible(true);

            } catch (ClientProcessExceptions ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Connection", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        //login to the server
        loginBtn.addActionListener(e -> {

            //check if username and password are empty
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter username and password", "Login", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                clientSTP.login(username.getText(), password.getText());

                JOptionPane.showMessageDialog(null, "Login successful", "Login", JOptionPane.INFORMATION_MESSAGE);
                clientMessagePanel.setVisible(true);

            } catch (Exception ex) {
                ex.printStackTrace();
            }//
        });
        //logout from the server
        logout.addActionListener(e -> {

            try {
                String discon = clientSTP.logout();

                if (discon != null) {
                    JOptionPane.showMessageDialog(null, discon, "Logout", JOptionPane.INFORMATION_MESSAGE);
                }
                clientMessagePanel.setVisible(false);
                clientAuthPanel.setVisible(false);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        //upload message to the server
        uploadBtn.addActionListener(e -> {

            //check if message is empty
            if (uploadMessage.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter message", "Upload", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                clientSTP.uploadMessage(uploadMessage.getText());
                JOptionPane.showMessageDialog(null, "Upload message: " + uploadMessage.getText(), "Upload", JOptionPane.INFORMATION_MESSAGE);
                uploadMessage.setText("");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        //download message from the server
        downloadBtn.addActionListener(e -> {
            //check if message is empty
            if (downloadMessage.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter message", "Download", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                clientSTP.downloadAMessage(downloadMessage.getText());
                JOptionPane.showMessageDialog(null, "Download message: " + downloadMessage.getText(), "Download", JOptionPane.INFORMATION_MESSAGE);
                //append message to allMessage
                allMessage.append(downloadMessage.getText() + "\n");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        downloadAll.addActionListener(e -> {
            allMessage.setText("");
            try {
                allMessage.append(clientSTP.downloadAll());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        clientPanel1 = new JPanel();
        clientPanel1.setLayout(new BorderLayout(0, 0));
        requestConnectionPanel = new JPanel();
        requestConnectionPanel.setLayout(new GridBagLayout());
        requestConnectionPanel.setBackground(new Color(-1910826));
        clientPanel1.add(requestConnectionPanel, BorderLayout.NORTH);
        final JLabel label1 = new JLabel();
        label1.setText("Host Address:");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(7, 0, 0, 0);
        requestConnectionPanel.add(label1, gbc);
        hostAdd = new JTextField();
        hostAdd.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.ipadx = 80;
        gbc.insets = new Insets(10, 0, 2, 0);
        requestConnectionPanel.add(hostAdd, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Port No:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 5, 0);
        requestConnectionPanel.add(label2, gbc);
        portNumber = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 10;
        requestConnectionPanel.add(portNumber, gbc);
        connectBtn = new JButton();
        connectBtn.setEnabled(true);
        connectBtn.setHideActionText(false);
        connectBtn.setText("Connect");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = -20.0;
        gbc.insets = new Insets(5, 0, 0, 0);
        requestConnectionPanel.add(connectBtn, gbc);
        clientAuthPanel = new JPanel();
        clientAuthPanel.setLayout(new GridBagLayout());
        clientAuthPanel.setEnabled(true);
        clientPanel1.add(clientAuthPanel, BorderLayout.CENTER);
        final JLabel label3 = new JLabel();
        label3.setText("Username:");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        clientAuthPanel.add(label3, gbc);
        final JLabel label4 = new JLabel();
        label4.setText("Password:");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        clientAuthPanel.add(label4, gbc);
        username = new JTextField();
        username.setEnabled(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 80;
        clientAuthPanel.add(username, gbc);
        password = new JTextField();
        password.setEnabled(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        clientAuthPanel.add(password, gbc);
        loginBtn = new JButton();
        loginBtn.setEnabled(true);
        loginBtn.setHideActionText(false);
        loginBtn.setText("login");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = -20.0;
        gbc.insets = new Insets(5, 0, 0, 0);
        clientAuthPanel.add(loginBtn, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        clientAuthPanel.add(spacer1, gbc);
        clientMessagePanel = new JPanel();
        clientMessagePanel.setLayout(new GridBagLayout());
        clientMessagePanel.setEnabled(true);
        clientPanel1.add(clientMessagePanel, BorderLayout.SOUTH);
        allMessage = new JTextArea();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipadx = 200;
        clientMessagePanel.add(allMessage, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridheight = 4;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(150, 0, 0, 0);
        clientMessagePanel.add(spacer2, gbc);
        uploadMessage = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 90;
        clientMessagePanel.add(uploadMessage, gbc);
        logout = new JButton();
        logout.setText("logout");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        clientMessagePanel.add(logout, gbc);
        downloadBtn = new JButton();
        downloadBtn.setText("download");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 2;
        clientMessagePanel.add(downloadBtn, gbc);
        uploadBtn = new JButton();
        uploadBtn.setText("upload");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        clientMessagePanel.add(uploadBtn, gbc);
        downloadMessage = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        clientMessagePanel.add(downloadMessage, gbc);
        downloadAll = new JButton();
        downloadAll.setText("downloadAll");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        clientMessagePanel.add(downloadAll, gbc);
        final JLabel label5 = new JLabel();
        label5.setText("Messages");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        clientMessagePanel.add(label5, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return clientPanel1;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public JPanel getClientPanel1() {
        return clientPanel1;
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Client Host GUI");

        ClientSMPForm gui = new ClientSMPForm();

        //first panel - request connection
        gui.getClientPanel1().setBackground(Color.lightGray);
        gui.getClientPanel1().setMaximumSize(new Dimension(200, 300));
        gui.clientAuthPanel.setVisible(false);
        gui.clientMessagePanel.setVisible(false);
        frame.add(gui.getClientPanel1());

        //sec panel - get connection response from the server

        frame.setSize(400, 500);
        frame.setVisible(true);

    }
}
