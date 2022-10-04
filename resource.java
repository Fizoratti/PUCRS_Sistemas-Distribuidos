import java.net.InetAddress;

public class Resource {
    String name;
    String hash;
    InetAddress address;
    Integer port;

    Resource(String name, String hash, InetAddress address, Integer port) {
        this.name = name;
        this.hash=hash;
        this.address=address;
        this.port=port;
    }
    
    public String toString() {
        return name+"  (hash: "+hash+") | address: "+address+":"+port+" |";
    }
}
