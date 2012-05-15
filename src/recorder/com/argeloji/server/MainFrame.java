package com.argeloji.server;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.HeadlessException;
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
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.argeloji.client.ClientGUI;
import com.argeloji.entity.ServerDispatcher;

public class MainFrame extends JFrame implements ActionListener {

	JButton btnYoklama, btnCapture, btnDosyaGonder, btnSnapshot, btnQuiz;
	JComboBox cbSinif, cbDers;
	JPanel panel;
	JLabel labeluser, labelip;

	MainFrame(String user) {

		ServerDispatcher sd = ServerDispatcher.getInstance();

		InetAddress thisIp = null;
		try {
			thisIp = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		labeluser = new JLabel("Kullanýcý: " + user);
		labeluser.setFont(new Font("Tahoma", Font.BOLD, 20));

		labelip = new JLabel(thisIp.toString());

		String[] sinifList = { "Sýnýf Seçiniz", "9/A", "9/B", "9/C", "9/D " };
		String[] dersList = { "Ders Seçiniz", "Türkçe", "Matematik",
				"Bilgisayar Bilimleri" };

		cbSinif = new JComboBox(sinifList);
		cbDers = new JComboBox(dersList);

		btnYoklama = new JButton("Yoklama");
		btnSnapshot = new JButton("Resim Yakala");
		btnCapture = new JButton("Video Yakala");
		btnDosyaGonder = new JButton("Sýnýfa Dosya Gönder");
		btnQuiz = new JButton("Sýnýfa Soru Gönder");

		panel = new JPanel(new GridLayout(0, 1));
		panel.add(labeluser);
		panel.add(labelip);
		panel.add(cbSinif);
		panel.add(cbDers);
		panel.add(btnYoklama);
		panel.add(btnCapture);
		panel.add(btnSnapshot);
		panel.add(btnDosyaGonder);
		panel.add(btnQuiz);

		add(panel, BorderLayout.CENTER);

		btnYoklama.addActionListener(this);
		btnCapture.addActionListener(this);
		btnSnapshot.addActionListener(this);
		btnDosyaGonder.addActionListener(this);
		btnQuiz.addActionListener(this);
		setTitle("Ana Menü");

		setLocation(200, 200);
		setSize(300, 300);
	}

	public void actionPerformed(ActionEvent evt) {
		String valueSinif = cbSinif.getSelectedItem().toString();
		String valueDers = cbDers.getSelectedItem().toString();

		if (evt.getActionCommand().equals("Yoklama")) {
			AttendanceFrame tf = new AttendanceFrame(valueSinif);
			tf.setVisible(true);
		}
		if (evt.getActionCommand().equals("Resim Yakala")) {
			ResizableJWindow tf = new ResizableJWindow(new Rectangle(100, 100,
					600, 400), true);
			tf.setVisible(true);
		}
		if (evt.getActionCommand().equals("Video Yakala")) {
			ResizableJWindow tf = new ResizableJWindow(new Rectangle(100, 100,
					600, 400), false);
			tf.setVisible(true);
		}
		if (evt.getActionCommand().equals("Sýnýfa Soru Gönder")) {
			QuizTeacherDesktop qt = new QuizTeacherDesktop();
			qt.setVisible(true);
		}
		if (evt.getActionCommand().equals("Sýnýfa Dosya Gönder")) {
			FileChooser fc = new FileChooser();
			fc.setSize(400, 220);
			// fc.setVisible(true);
		}
		
	}

	public static void main(String arg[]) {
		try {
			MainFrame frame = new MainFrame("demo");
			frame.setVisible(true);
//			ClientGUI cg= new ClientGUI();
//			cg.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
