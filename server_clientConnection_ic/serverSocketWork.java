package myWork;


import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.util.*;

public class serverSocketWork implements Runnable {
    private Socket client;
    private Boolean executed=false;

    public serverSocketWork(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        if (executed) return;
        executed=true;
        BufferedReader in;
        try {
            InputStream is=client.getInputStream();
            in = new BufferedReader(new InputStreamReader(is));
            String line=in.readLine();
            URL url = new URL();
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException("Error in creating a buffered reader");
        }

    }
}
