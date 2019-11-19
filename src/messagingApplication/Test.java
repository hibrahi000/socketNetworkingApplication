package messagingApplication;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class Test {

	private static Socket mySocket;
	
	
	
	public static void main(String[] args) {

		mySocket = new Socket(64000);
		
		mySocket.send("Hello Communication World!!!", 
					  mySocket.getMyAddress(), 
					  mySocket.getMyPortNumber());

		DatagramPacket inPacket = null;
		do {
			inPacket = mySocket.receive();
		} while (inPacket == null);
		
		byte[] inBuffer = inPacket.getData();
		String inMessage = new String(inBuffer);
		InetAddress senderAddress = inPacket.getAddress();
		int senderPort = inPacket.getPort();
		
		System.out.println();
		System.out.println("Received Message = " + inMessage);
		System.out.println("Sender Address   = " + senderAddress.getHostAddress());
		System.out.println("Sender Port      = " + senderPort);
		
		mySocket.close();
		
	}

}
