package myWork;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.Executor;

public class httpsServer extends HttpsServer {

    private InetSocketAddress inetSocketAddress;
    private URL mainUrl;

    public httpsServer(InetSocketAddress inetSocketAddress, URL mainUrl) {
        this.inetSocketAddress = inetSocketAddress;
        this.mainUrl=mainUrl;
    }

    @Override
    public void setHttpsConfigurator(HttpsConfigurator httpsConfigurator) {

    }

    @Override
    public HttpsConfigurator getHttpsConfigurator() {
        return null;
    }

    @Override
    public void bind(InetSocketAddress inetSocketAddress, int i) throws IOException {
        bind(this.inetSocketAddress, 0);
    }

    @Override
    public void start() {

    }

    @Override
    public void setExecutor(Executor executor) {

    }

    @Override
    public Executor getExecutor() {
        return null;
    }

    @Override
    public void stop(int i) {

    }

    @Override
    public HttpContext createContext(String s, HttpHandler httpHandler) {
        return null;
    }

    @Override
    public HttpContext createContext(String s) {
        try {
            int l=mainUrl.toURI().toString().length();
            HttpContext context1 = (s.length() > l)?(HttpContext) s.substring(l):"/";
            HttpContext context = new HttpContext();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Couldn't create a context from the given String");
        }
    }

    @Override
    public void removeContext(String s) throws IllegalArgumentException {

    }

    @Override
    public void removeContext(HttpContext httpContext) {

    }

    @Override
    public InetSocketAddress getAddress() {
        return inetSocketAddress;
    }
}
