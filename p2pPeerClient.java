import java.io.*;
import java.net.*;
import java.util.*;

public class p2pPeerClient extends Thread {
	protected DatagramSocket socket = null;
	protected DatagramPacket packet = null;
	protected InetAddress addr = null;
	protected byte[] resource = new byte[1024];
	protected byte[] response = new byte[1024];
	protected int port, peer_port;
	protected String serverAddress;
	protected String nickname;

	public p2pPeerClient(String[] args) throws IOException {
		port = Integer.parseInt(args[2]) + 101;
		// cria um socket datagrama
		socket = new DatagramSocket(port);
		serverAddress = args[0];
		nickname = args[3];
	}

	public void run() {
		BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
		String str = "";	

		while (true) {

			//System.out.println("\n<list/peer> <message> <ip>");
			System.out.println("\n# COMMANDS: ");
			System.out.println("# Example: list");
			System.out.println("# Example: peer \"A message to the peer.\" <peer_ip> <peer_port>");
			System.out.print("> ");
			try {
				str = obj.readLine();
				String vars[] = str.split("\\s");
				addr = InetAddress.getByName(serverAddress);
				String str2 = vars[0] + " " + nickname;  // vars[0]: list ~vars[1]: nickname~
				resource = str2.getBytes();
				if (vars.length == 4) { // if the input is peer "msg" 172.168.1.5 4000
					System.out.println("Sending message to peer on port " + vars[3]);
					peer_port = Integer.parseInt(vars[3]);
				} else {
					peer_port = 9000; // in this case, its not peer, its the server port
				}
			} catch (IOException e) {
			}
			
			try {
				packet = new DatagramPacket(resource, resource.length, addr, peer_port); // peer port is 9000 sending to server ip
				socket.send(packet);
				
				while (true) {
					try {
						// obtem a resposta
						packet = new DatagramPacket(response, response.length);
						socket.setSoTimeout(500);
						socket.receive(packet);
						
						// mostra a resposta
						String resposta = new String(packet.getData(), 0, packet.getLength());
						System.out.println("[Package received] " + resposta);
					} catch (IOException e) {
						break;
					}
				}
			} catch (IOException e) {
			}
		}
	}
}
