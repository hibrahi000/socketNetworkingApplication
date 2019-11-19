package messagingApplication;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.DatagramPacket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.net.InetAddress;

public class BeginChat extends JFrame {
	
	private static Socket socket = new Socket(64000);
	private int port;
	private JLabel ipLabel;
	private JLabel portLabel;
	private JTextField ipTxt;
	private JTextField portTxt;
	private JButton connect;
	private JTextField ipName;
	
	private static Map<String, ChatApplication> newChat = new HashMap<String, ChatApplication>();
	private static HashMap <String, String> cacheTable = new HashMap<>();
	
	public BeginChat(String BroadCast) {
//		this.port = port;
//		this.socket = new Socket(this.port);
		
	if(BroadCast == "NonBroadCast")	{
		socket.setBroadcast(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 200);
		setTitle("Lets Get Started");
		setResizable(true);
		setLayout(new GridBagLayout());
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		ipLabel = new JLabel("IP Address:");
		constraints.insets = new Insets(0, 20, 0, 0);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(ipLabel, constraints);
		
			
		ipTxt = new JTextField();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(0,30,0,20);
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		add(ipTxt, constraints);
				
		
		portLabel = new JLabel("Port number:");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(20, 20, 0, 0);
		constraints.weightx = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(portLabel,  constraints);
		
		portTxt = new JTextField();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(20, 0, 0, 20);
		constraints.weightx = 2;
		constraints.gridx = 1;
	    constraints.gridy = 1;
	    constraints.gridwidth = 2;
		add(portTxt, constraints);
	    
		connect = new JButton("Connect");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(20, 40, 0, 80);
		constraints.weightx = .2;
		constraints.gridx = 1;
	    constraints.gridy = 2;
	    constraints.gridwidth = 1;
	    add(connect, constraints);
		connect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!ipTxt.getText().isEmpty()) {
					String sIp =  ipTxt.getText();
					int portNum = Integer.parseInt(portTxt.getText());
					ChatApplication createChat = new ChatApplication(socket, sIp, portNum);
					newChat.put(sIp, createChat);
					
				}
				
			}
			
		});
		//panelS.add(connect);
		setVisible(true);
		do {
		recieve();
		}while (true);
		
		
	}
	else {
		socket.setBroadcast(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 200);
		setTitle("Lets Get Started");
		setResizable(true);
		setLayout(new GridBagLayout());
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		ipLabel = new JLabel("Name To BroadCast:");
		constraints.insets = new Insets(0, 20, 0, 0);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(ipLabel, constraints);
		
			
		ipName = new JTextField();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(0,30,0,20);
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		add(ipName, constraints);
		
		
		connect = new JButton("Connect");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(20, 40, 0, 80);
		constraints.weightx = .2;
		constraints.gridx = 1;
	    constraints.gridy = 2;
	    constraints.gridwidth = 1;
	    add(connect, constraints);
		connect.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(!ipName.getText().isEmpty()) {
					
						String name = ipName.getText();
						
						if (!name.isEmpty()) {
							String message = null;
							message = "????? " + name + " ##### hashmat";
							
							try {
								socket.send(message,InetAddress.getByName("255.255.255.255"), 64000);
							} catch (UnknownHostException e1) {
								e1.printStackTrace();
							}
							
							ipName.setText("");
						}
						
					}
			}

		});
		
		setVisible(true);
		do {
			broadCastRecieve();
			}while (true);
			
		
	}
	}
	
	
	
	public static void broadCastChat(Boolean broadcast) {
		
		if(!broadcast) {
			BeginChat chatter = new BeginChat("NonBroadCast");
		}
		else {
			BeginChat chatter =  new BeginChat("BroadCast");
		}
		
	}
	
	

	
	public static void recieve() {
			DatagramPacket packet= null;
		
		do {
			packet = socket.receive();
			
			if(packet != null) {
				String ip = packet.getAddress().getHostAddress();
				
				int port = packet.getPort();
						
				String msg = new String(packet.getData());
				if(!newChat.containsKey(ip)) {
					ChatApplication newchat = new ChatApplication(socket, ip, port);
					newChat.put(ip, newchat);
					newchat.getText().append("Them: " + msg + "\n");
					newchat.setVisible(true);
				} else {
					ChatApplication currentChat = newChat.get(ip);
					currentChat.getText().append("Them: " + msg + "\n");
					currentChat.setVisible(true);
					System.out.println(msg);
				}
			}
		} while (true);
		
	}
	

	public static void broadCastRecieve() {
			DatagramPacket inPacket = null;
			
			do {
				try {
					inPacket = socket.receive();

					if (inPacket != null) {
						System.out.print(inPacket);
						String message = new String(inPacket.getData());
						System.out.println(message);
						if (isBroadcastMessage(message)) {
							String sender = "", receiver="";
							StringTokenizer st = new StringTokenizer(message);
							int tokens = st.countTokens();
							
							for (int i = 0; i < tokens; i++) {
								String s = st.nextToken();
								
								if (i == 1) {
									receiver = s;
								}
								
								if (i == 3) {
									sender = s;
								}
							}
							
							String myName = "hashmat"  ;
							System.out.println(receiver);
							System.out.println(receiver.equals(myName));
							if (receiver.equals(myName)) {
								InetAddress senderAddress = inPacket.getAddress();
								String reply = "##### " + myName + " ##### " + senderAddress.getHostAddress();
								
								socket.send(reply, senderAddress, 64000);
								cacheTable.put(senderAddress.getHostAddress(), sender);
								
								ChatApplication chat = new ChatApplication(socket, 64000, senderAddress.getHostAddress(), sender);
								
								newChat.put(sender, chat);
								chat.setVisible(true);
							}
						} else if (isReply(message)) {
							StringTokenizer st = new StringTokenizer(message);
							int tokens = st.countTokens();
							String sender = "", ip = "";
							
							for (int i = 0; i < tokens; i++) {
								String token = st.nextToken();
								
								if (i == 1) {
									sender = token; 
								}
								
								if (i == 3) {
									ip = token;
								}
								
							}
							
							String senderName = sender, senderIP = ip;
							
							ChatApplication chatter = new ChatApplication(socket, 64000, senderIP, senderName);
							newChat.put(senderName, chatter);
							cacheTable.put(senderIP, senderName);
							chatter.setVisible(true);
							
							chatter.addCloseEventListener(new WindowListener () {
								
								
								public void windowOpened(WindowEvent e) {
									// TODO Auto-generated method stub
									
								}

								
								public void windowClosing(WindowEvent e) {
									 newChat.remove(senderName);
									 cacheTable.remove(senderIP);
								}

								
								public void windowClosed(WindowEvent e) {
									// TODO Auto-generated method stub
									
								}

								public void windowIconified(WindowEvent e) {
									// TODO Auto-generated method stub
									
								}

								public void windowDeiconified(WindowEvent e) {
									// TODO Auto-generated method stub
									
								}

								
								public void windowActivated(WindowEvent e) {
									// TODO Auto-generated method stub
									
								}

								
								public void windowDeactivated(WindowEvent e) {
									// TODO Auto-generated method stub
									
								}
								
							});
						} else {
							InetAddress senderAddress = inPacket.getAddress();
							String senderIP = senderAddress.getHostAddress();
							String senderName = cacheTable.get(senderIP);
							
							ChatApplication chat = newChat.get(senderName);
							chat.getText().append(senderName + ": " + message + "\n");
							chat.setVisible(true);
						}
					}
				} catch (NullPointerException npe) {
					// do nothing
				}
			} while (true);
		}
		
	
	private static boolean isBroadcastMessage(String message) {
		return message.substring(0, 5).equals("?????");
	}
	
	private static boolean isReply(String message) {
		return message.substring(0, 5).matches("#####");
	}

	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		broadCastChat(true);

	}
	
	
	//// receivemethod


	
	
	
	
	
	
}
