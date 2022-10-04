import java.io.*;
import java.net.*;
import java.util.*;

public class p2pPeer {

	public static void main(String[] args) throws IOException {
		if (args.length != 3) {
			System.out.println("Uso: java p2pPeer <server> \"<message>\" <localport>"); // localport é a porta que ele vai usar pra ele mesmo se comunicar com outros peers. quando roda o comando java p2pPeer 10.132.249.122 "create john" 9001  essa 9001 é a porta que o processo do novo peer vai ter.
			System.out.println("<message> is:");
			System.out.println("join <nickname>");
			System.out.println("list <nickname>");
			System.out.println("wait");
			return;
		} else {
			new p2pPeerThread(args).start();
			new p2pPeerHeartbeat(args).start();
			new p2pPeerClient(args).start();
		}
	}
}
