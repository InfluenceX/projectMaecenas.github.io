package myWork;

public class Main {
    public static void main(String argvs[]) {
        int PORT=4444;
        Thread server=new Thread(new serverSocketOpen(PORT));
        server.start();
    }
}
