package myWork;



import javax.xml.ws.spi.http.HttpContext;
import javax.xml.ws.spi.http.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.security.Principal;
import java.util.*;

public class Http extends HttpExchange {
    private HashMap<String, List<String>> responseHeaders=new HashMap<>();

    private InputStream is;
    private Map<String, String> elements;

    public Http(InputStream is) {
        this.is =is;
        elements=getRequestElements();
    }

    @Override
    public Map<String, List<String>> getRequestHeaders() {
        return (HashMap<String, List<String>>) processRequest().get(2);
    }

    @Override
    public String getRequestHeader(String name) {
        HashMap<String, List<String>> requestHeaders=(HashMap<String, List<String>>) getRequestHeaders();
        if (requestHeaders.isEmpty() || name==null || !requestHeaders.containsKey(name)) return null;
        return ((LinkedList<String>)requestHeaders.get(name)).getFirst();
    }

    @Override
    public Map<String, List<String>> getResponseHeaders() {
        return (HashMap<String, List<String>>) responseHeaders.clone();
    }

    @Override
    public void addResponseHeader(String name, String value) {
        if (name==null || value==null) return;
        if (responseHeaders.containsKey(name)) {
            LinkedList<String> values=(LinkedList<String>) responseHeaders.get(name);
            if (!values.contains(value)) {
                values.add(value);
                responseHeaders.remove(name);
                responseHeaders.put(name, values);
            }
        } else {
            LinkedList<String> values=new LinkedList<>();
            values.add(value);
            responseHeaders.put(name, values);
        }
    }

    @Override
    public String getRequestURI() {
        return null;
    }

    @Override
    public String getContextPath() {
        return null;
    }

    @Override
    public String getRequestMethod() {
        return elements.get("method");
    }

    @Override
    public HttpContext getHttpContext() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public InputStream getRequestBody() throws IOException {
        return null;
    }

    @Override
    public OutputStream getResponseBody() throws IOException {
        return null;
    }

    @Override
    public void setStatus(int status) {

    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return null;
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return null;
    }

    @Override
    public String getProtocol() {
        return null;
    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public String getPathInfo() {
        return null;
    }

    @Override
    public String getQueryString() {
        return null;
    }

    @Override
    public Object getAttribute(String name) {
        return null;
    }

    @Override
    public Set<String> getAttributeNames() {
        return null;
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public boolean isUserInRole(String role) {
        return false;
    }


    private String getRequest() {
        return (String) processRequest().get(0);
    }

    private Map<String, String> getRequestElements() {
        return (HashMap<String, String>) processRequest().get(1);
    }

    private List<Object> processRequest() {
        HashMap<String, String> elements=new HashMap<>();
        try {
            BufferedReader in=new BufferedReader(new InputStreamReader(is));
            String request=in.readLine()+"\r\n";
            LinkedList<String> parts=new LinkedList<>();
            getParts(request, parts);
            elements.put("method", parts.getFirst());
            elements.put("url", parts.get(1));
            elements.put("version", parts.get(2));
            parts.clear();
            String line;
            HashMap<String, List<String>> requestHeaders=new HashMap<>();
            while ((line=in.readLine()).length()>0) {
                line+="\r\n";
                request+=line;
                getParts(line, parts);
                if (requestHeaders.containsKey(parts.getFirst())) {
                    List<String> values=requestHeaders.get(parts.getFirst());
                    values.add(parts.getLast());
                    requestHeaders.put(parts.getFirst(), values);
                } else {
                    List<String> values=new LinkedList<>();
                    values.add(parts.getLast());
                    requestHeaders.put(parts.getFirst(), values);
                }
            }
            line=in.readLine();
            while (line!=null) {
                line+=in.readLine();
            }
            request+=line+"\r\n"+"\r\n";
            elements.put("entity body", line);
            LinkedList<Object> l=new LinkedList<>();
            l.add(request);
            l.add(elements);
            l.add(requestHeaders);
            return l;
        } catch (IOException e) {
            throw new RuntimeException("Couldn't process the request");
        }
    }

    private void getParts(String s, List<String> l) {
        if (s.length()>=0) {
            int i=s.indexOf(" ");
            if (i<0) i=s.indexOf("\r");
            l.add(s.substring(0, i));
            getParts(s.substring(i+1), l);
        }
    }
}
