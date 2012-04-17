package com.easycapture.recorder;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.argeloji.cons.Enums.MessageType;
import com.argeloji.entity.Student;
import com.argeloji.messaging.MessengerClient;

public class ClientGUI extends JFrame{

	public ClientGUI() {		
		
		JLabel welcome= new JLabel("Merhaba");
		this.add(welcome);
		
		MessengerClient mc = new MessengerClient("127.0.0.1");
		Thread thread = new Thread(mc);
		thread.start();
		mc.connect();		
		
		mc.send(MessageType.StudentConnected, new Student("123"));
	}
	
	public static void main(String arg[]) {		
			ClientGUI frame = new ClientGUI();
			frame.setSize(300, 200);
			frame.setLocation(300,300);
			frame.setVisible(true);
		
	}
}
