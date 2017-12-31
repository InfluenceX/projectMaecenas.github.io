package myWork;

import java.io.IOException;
import java.net.*;
import javax.net.ssl.SSLServerSocketFactory;

public class serverSocketOpen implements Runnable {
    /// variabile che verifica la status del server: acceso o spento
    private Boolean isStopped = false;

    /// uso il protocollo https
    private SSLServerSocketFactory sock;
    private ServerSocket serverSocket;

    private int port;

    private Boolean executed = false;

    public serverSocketOpen(int port) {
        this.port = port;
        sock = new SSLServerSocketFactory() {
            @Override
            public String[] getDefaultCipherSuites() {
                return new String[0];
            }

            @Override
            public String[] getSupportedCipherSuites() {
                return new String[0];
            }

            @Override
            public ServerSocket createServerSocket(int i) throws IOException {
                return new ServerSocket(i);
            }

            @Override
            public ServerSocket createServerSocket(int i, int i1) throws IOException {
                return null;
            }

            @Override
            public ServerSocket createServerSocket(int i, int i1, InetAddress inetAddress) throws IOException {
                return null;
            }
        };
    }


    @Override
    public void run() {
        if (executed) return;
        executed = true;
        ///creo il socket su cui il server ascolterà
        try {
            serverSocket = sock.createServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't create a server Socket on port: " + port);
        }

        /// Accetto connessioni dai client. Il server è multithread quindi lavorerà con i diversi client conteporeamente.
        /// La classe serverSocketWork si occuperà della comunicazione del server con il client

        while (!isStopped) {
            Socket client;
            try {
                client = serverSocket.accept();
            } catch (IOException e) {
                if (isStopped) {
                    System.err.println("Server has stopped");
                    return;
                }
                throw new RuntimeException("Accept failed");
            }
            Thread t = new Thread(new serverSocketWork(client));
            t.start();
        }
        System.err.println("Server has stopped");
    }

    public synchronized Boolean getIsStopped() {
        return this.isStopped;
    }

    public synchronized void stop() {
        isStopped = true;
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing the server");
        }
    }
}

