import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

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
			System.out.println("# Example: peer message \"Message body.\" <remote_peer_ip> <remote_peer_port>");
			System.out.print("> ");
			try {
				str = obj.readLine();
				String vars[] = str.split("\\s");
				addr = InetAddress.getByName(serverAddress);

				switch(vars[0]) {
					case "list":
						String str2 = vars[0] + " " + nickname;  // vars[0]: list ~vars[1]: nickname~
						resource = str2.getBytes();
						peer_port = 9000; // in this case, its not peer, its the server port
						break;

					case "up":
						String hash = "8f76g4";
						String str5 = vars[0] + " " + nickname + " " + hash;  // vars[0]: up
						System.out.println("resource body: "+str5);
						resource = str5.getBytes();
						peer_port = 9000; // in this case, its not peer, its the server port
						break;

					case "search":
						String str4 = vars[0];  // vars[0]: search
						resource = str4.getBytes();
						peer_port = 9000; // in this case, its not peer, its the server port
						break;

					case "peer":
						if(Objects.equals("message", new String(vars[1]))) {
							String str3 = "message from "+ nickname +": "+ vars[2];   
							resource = str3.getBytes();
							System.out.println(vars.length);
							if (vars.length == 5) { // if the input is peer "msg" 172.168.1.5 4000
								System.out.println("Sending message to peer on port " + vars[4]);
								peer_port = Integer.parseInt(vars[4]);
							} 
						}
						break;

					default:
						System.out.println("! Weird input: " + vars[0]);
						break;
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
