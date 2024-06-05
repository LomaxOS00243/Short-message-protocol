**A GUI Java-based System for Inter-process Communication over TCP**

This project involves the design and implementation of a **Message Protocol system** to streamline secure and concurrent client-server message exchanges over TCP. It uses Java stream-mode sockets to support connection-oriented communication between client and server processes. Multiple clients can request setting SSL/TLS connections with the server.

The communication flow starts with the server listening for incoming connections on port 17. A client process can then establish a TLS connection on this port, initiating the TLS handshaking process before messages are exchanged. Once the secure connection is established, the server sends an acknowledgment message, starting a new session for this client using threading.

