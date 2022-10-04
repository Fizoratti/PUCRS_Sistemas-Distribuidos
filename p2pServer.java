import java.io.*;
import java.net.*;
import java.util.*;

public class p2pServer {
	public static void main(String[] args) throws IOException {
		String content = null;
		InetAddress addr;
		int port;
		byte[] resource = new byte[1024];
		byte[] response = new byte[1024];
		DatagramSocket socket = new DatagramSocket(Integer.parseInt(args[0]));
		DatagramPacket packet;
		
		List<String> clientList = new ArrayList<>();
		List<InetAddress> clientAddr = new ArrayList<>();
		List<Integer> clientPort = new ArrayList<>();
		List<Integer> timeoutVal = new ArrayList<>();

		Map<String, String> resourceList = new HashMap<>();  // <client, hash>
		
		System.out.println("# Server application started.");

		while (true) {
			try {
				// recebe datagrama
				packet = new DatagramPacket(resource, resource.length);
				socket.setSoTimeout(1000);
				socket.receive(packet);
				System.out.print("\n[Package received] ");
								
				// processa o que foi recebido, adicionando a uma lista
				content = new String(packet.getData(), 0, packet.getLength());
				addr = packet.getAddress();
				port = packet.getPort();
				String vars[] = content.split("\\s");

				System.out.println("| c: "+content+" | a: "+addr+" | p: "+port);
				
				if (vars[0].equals("join") && vars.length > 1) {
					int j;
					
					// check if resource (currently is a nickname) exists in the pool
					for (j = 0; j < clientList.size(); j++) {
						if (clientList.get(j).equals(vars[1]))
							break;
					}

					// append new resource to the resource list
					if (j == clientList.size()) {
						clientList.add(vars[1]);
						clientAddr.add(addr);
						clientPort.add(port);
						timeoutVal.add(15);		/* 500ms * 15 = 7.5s (enough for 5s heartbeat) */
						
						response = "OK".getBytes();
						System.out.print(vars[1]+ " joined");
					} else {
						response = "NOT OK".getBytes();
					}
					
					packet = new DatagramPacket(response, response.length, addr, port);
					socket.send(packet);
				}
				
				if (vars[0].equals("list") && vars.length > 1) {
					System.out.print("list request from " + vars[1]);
					for (int j = 0; j <= clientList.size(); j++) {
						
						if (clientList.get(j).equals(vars[1])) {
							for (int i = 0; i < clientList.size(); i++) {
								String data = new String(clientList.get(i) + " " + clientAddr.get(i).toString() + " " + clientPort.get(i).toString());
								response = data.getBytes();
								
								packet = new DatagramPacket(response, response.length, addr, port);
								socket.send(packet);
							}
							break;
						}
					}
				}


					System.out.print("vars[0]: " + vars[0]);
				if (vars[0].equals("up")) {
					System.out.print("adding resourses from " + vars[1]);

					resourceList.put(vars[1], vars[2]); // vars[1]: client, vars[2]: hash

					String data = new String(" resource added from" + vars[1].toString());
					response = data.getBytes();
					
					packet = new DatagramPacket(response, response.length, addr, port);
					socket.send(packet);
					
					break;	
				}

				if (vars[0].equals("search")) {
					System.out.print("search request from " + vars[1]);

					for (int i = 0; i < resourceList.size(); i++) {
						String data = new String("resource: "+resourceList.get(i));
						response = data.getBytes();
						
						packet = new DatagramPacket(response, response.length, addr, port);
						socket.send(packet);
					}
					break;	
				}
				
				if (vars[0].equals("heartbeat") && vars.length > 1) {
					System.out.print("heartbeat from " + vars[1]);
					for (int i = 0; i < clientList.size(); i++) {
						if (clientList.get(i).equals(vars[1]))
							timeoutVal.set(i, 15);
					}
				}
			} catch (IOException e) {
				// decrementa os contadores de timeout a cada 500ms (em função do receive com timeout)
				for (int i = 0; i < timeoutVal.size(); i++) {
					timeoutVal.set(i, timeoutVal.get(i) - 1);
					if (timeoutVal.get(i) == 0) {
						System.out.println("\n# User " + clientList.get(i) + " is dead.");
						clientList.remove(i);
						clientAddr.remove(i);
						clientPort.remove(i);
						timeoutVal.remove(i);
					}
				}
				System.out.print(".");
			}
		}
	}
}
