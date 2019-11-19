package messagingApplication;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ChatApplication extends JFrame{

	
	private JTextArea textArea;
	private JTextField textInput;
	private	JButton send;
	private JButton close;
	private InetAddress destIP;
	private int port;
	private Socket socket;
	private InetAddress address;

	
	public ChatApplication(Socket connect, String ip, int port){
//		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 600);
		setTitle("IP address: " + ip + " Port: " + port);
		setResizable(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		getContentPane().add(panel);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		panel.add(textArea);
		
		 
		JPanel bottom = new JPanel(new BorderLayout());
		panel.add(bottom, BorderLayout.SOUTH);
		textInput = new JTextField();
		
		
		bottom.add(textInput);
		JPanel button = new JPanel(new BorderLayout());
		bottom.add(button, BorderLayout.EAST);
	
		 send = new JButton("Send Message");
		 send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(!textInput.getText().isEmpty()) {
					String msg = textInput.getText().toString().trim();
					InetAddress dest = null;
					System.out.print(msg);
					textArea.append("Hashmat Ibrahimi: "+ msg + "\n");
				
				
					try {
						dest = InetAddress.getByName(ip);
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					connect.send(msg, dest, port);
				
				}
			}
			 
			 
		 });
		 
		 button.add(send, BorderLayout.WEST);
		 
		 //close button
		 close = new JButton("close");
		 close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				
			}
			 
		 });
		
		 button.add(close);
		setVisible(true);
	}
	
	public ChatApplication(Socket connect, int port, String ip, String Name){
		this.port = port;
		try {
			this.address = InetAddress.getByName(ip);
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
			System.exit(-1);
		}
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 600);
		setTitle("Host Name: " + Name + ", Port number: " + port);
		setResizable(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		getContentPane().add(panel);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		panel.add(textArea);
		
		 
		JPanel bottom = new JPanel(new BorderLayout());
		panel.add(bottom, BorderLayout.SOUTH);
		textInput = new JTextField();
		
		
		bottom.add(textInput);
		JPanel button = new JPanel(new BorderLayout());
		bottom.add(button, BorderLayout.EAST);
	
		 send = new JButton("Send Message");
		 send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(!textInput.getText().isEmpty()) {
					String msg = textInput.getText().toString().trim();
					InetAddress dest = null;
					System.out.print(msg);
					textArea.append("Hashmat Ibrahimi: "+ msg + "\n");
				
				
					try {
						dest = InetAddress.getByName(ip);
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					connect.send(msg, dest, port);
				
				}
			}
			 
			 
		 });
		 
		 button.add(send, BorderLayout.WEST);
		 
		 //close button
		 close = new JButton("close");
		 close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				
			}
			 
		 });
		
		 button.add(close);
		setVisible(true);
		
	}
	
	
	public JTextArea getText() {
		return this.textArea;
	}
	
	public void addCloseEventListener (WindowListener windowListener) {
		addWindowListener(windowListener);
	}
	
	public JTextArea getTextArea () {
		return this.textArea;
	}
	
	public InetAddress getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
