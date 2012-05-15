package com.argeloji.client;

import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.argeloji.cons.Enums.MessageType;
import com.argeloji.entity.ClientDispatcher;
import com.argeloji.entity.Student;
import com.argeloji.messaging.MessengerClient;

public class ClientGUI extends JFrame {

	public ClientGUI() {

		JLabel welcome = new JLabel("Merhaba");
		this.add(welcome);

		// test icin
		Random r = new Random();
		int sID = r.nextInt(20);
		// test icin bitti
		ClientDispatcher cd= ClientDispatcher.getInstance(new Student(Integer.toString(sID)));

		
	}

	public static void main(String arg[]) {
		ClientGUI frame = new ClientGUI();
		frame.setSize(300, 200);
		frame.setLocation(300, 300);
		frame.setVisible(true);

	}
}
