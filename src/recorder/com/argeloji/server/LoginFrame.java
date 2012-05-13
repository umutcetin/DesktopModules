package com.argeloji.server;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.border.EmptyBorder;




public class LoginFrame extends JFrame implements ActionListener {

	JButton SUBMIT;
	JPanel panel;
	JLabel label1, label2;
	final JTextField text1, text2;

	LoginFrame() {
		label1 = new JLabel();
		label1.setText("Kullanýcý Adý:");
		text1 = new JTextField(15);

		label2 = new JLabel();
		label2.setText("Þifre:");
		text2 = new JPasswordField(15);

		SUBMIT = new JButton("Giriþ");

		panel = new JPanel(new GridLayout(3, 1));
		panel.setBorder(new EmptyBorder(new Insets(40, 60, 40, 60)));
		panel.add(label1);
		panel.add(text1);
		panel.add(label2);
		panel.add(text2);
		panel.add(SUBMIT);
		add(panel, BorderLayout.CENTER);
		SUBMIT.addActionListener(this);
		//setTitle("Kullanýcý Giriþi");
		
		setLocation(200, 200);
	}

	public void actionPerformed(ActionEvent ae) {
		String value1 = text1.getText();
		String value2 = text2.getText();
		if (value2.equals("demo")) {
			MainFrame mf=new MainFrame(value1);
			setVisible(false);
			mf.setVisible(true);
			
			//messaging
//			MessengerServer ms = new MessengerServer();
//			Thread thread = new Thread(ms);
//			thread.start();			
			
		} else {
			System.out.println("Geçersiz kullanýcý adý/þifre");
			JOptionPane.showMessageDialog(this, "Geçersiz kullanýcý adý/þifre",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void main(String arg[]) {
		try {
			LoginFrame frame = new LoginFrame();
			frame.setSize(300, 200);
			frame.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}

