import java.net.InetAddress;

public class resource {
    String name;
    String hash;
    InetAddress address;
    Integer port;
    
    public String toString() {
        return "| h: "+hash+" | a: "+address+" | p: "+port+" | r: ("+name+" )";
    }
}
