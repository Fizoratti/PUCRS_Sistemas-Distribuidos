import java.io.IOException;
import java.lang.*;
import java.util.*;

public class p2p {
    public static void main(String[] args) throws IOException {
        String mode = "default";
        String port = "0";

        System.out.println("================= Application p2p =================");
        try {
            if(args.length == 0) {
                System.out.println("! Missing p2p mode.");
            }
            if(args.length == 1) {
                System.out.println("! Missing "+ args[0] +" port.");
                if(Objects.equals("server", new String(args[0])))
                    System.out.println("# Usage: > java p2p <mode> <port>");
                if(Objects.equals("peer", new String(args[0])))
                    System.out.println("# Usage: > java p2p <mode> <port> <nickname> join <server-ip>");
            }
            if(args.length >= 2) {
                mode = args[0]; // server mode or peer mode
                port = args[1]; // server port or peer port
            }
            if((args.length == 2 && Objects.equals("server", new String(mode))) || args.length == 4) {
                System.out.println("# Starting "+ mode +" on port "+ port +".");
            }
            if(args.length == 3 && Objects.equals("peer", new String(args[0]))) {
                System.out.println("! Missing server address.");
            }

            switch(mode) {
                case "server":
                    if(args.length == 2) {
                        String[] serverArgs = new String[1];
                        serverArgs[0] = port;

                        p2pServer.main(serverArgs);
                    } else {
                        System.out.println("! Missing parameters.");
                        System.out.println("# Usage: > java p2p <mode> <port> <nickname> join <server-ip>");
                    }
                    break;
                
                case "peer":
                    if(args.length == 4) {
                        String[] peerArgs = new String[5];
                        String nickname = args[2];
                        String serverAddress = args[3];

                        // peerArgs[0] = port; 
                        // peerArgs[1] = nickname;
                        // peerArgs[2] = serverAddress;
                        peerArgs[0] = serverAddress; 
                        peerArgs[1] = "join " + nickname;
                        peerArgs[2] = port;
                        peerArgs[3] = nickname;
                        
                        p2pPeer.main(peerArgs);
                    } else {
                        System.out.println("! Missing parameters.");
                        System.out.println("# Usage: > java p2p <mode> <port> <nickname> join <server-ip>");
                    }
                    break;
                
                default:
                    System.out.println("# End.");
                    break;
            }
        } catch (IOException e) {
            //
		}
    }
}
