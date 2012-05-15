package com.argeloji.server;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.argeloji.entity.ServerDispatcher;

public class AttendanceFrame extends JFrame implements ActionListener {
//deneme comment
	JButton btnKaydet;
	JComboBox cbSinif, cbDers;
	JPanel panelust, panelogr, panelalt;
	JLabel label1;
	
	Hashtable<String , JPanel> students= new Hashtable<String, JPanel>();
	
	String[] ogrList = { "OGRENCI OGRENCI", "OGRENCI OGRENCI",
			"OGRENCI OGRENCI", "OGRENCI OGRENCI", "OGRENCI OGRENCI",
			"OGRENCI OGRENCI", "OGRENCI OGRENCI",
			"OGRENCI OGRENCI", "OGRENCI OGRENCI",
			"OGRENCI OGRENCI", "OGRENCI OGRENCI",
			"OGRENCI OGRENCI", "OGRENCI OGRENCI",
			"OGRENCI OGRENCI", "OGRENCI OGRENCI",
			"OGRENCI OGRENCI", "OGRENCI OGRENCI",
			"OGRENCI OGRENCI", "OGRENCI OGRENCI",
			"OGRENCI OGRENCI" };
	String[] noList = { "1", "2", "3", "4",
			"5", "6", "7", "8",
			"9", "10",
			"11", "12", "13", "14", "15", "16", "17", "18", "19", "0" };

	AttendanceFrame(String sinif) {
		
		ServerDispatcher sd= ServerDispatcher.getInstance();
		sd.register(this);

		label1 = new JLabel("Sýnýf: " + sinif);
		label1.setFont(new Font("Tahoma", Font.BOLD, 20));		

		btnKaydet = new JButton("Kaydet");

		panelust = new JPanel(new GridLayout(0, 1));
		panelust.add(label1);

		panelogr = new JPanel(new GridLayout(10, 2));

		for (int i = 0; i < 20; i++) {
			JPanel paneltekogr = new JPanel(new GridLayout(0, 3));			
			
			JLabel num = new JLabel(noList[i]);
			num.setIcon(new ImageIcon("child.jpg"));
			JLabel name = new JLabel(ogrList[i]);
	//		JCheckBox jc = new JCheckBox("Mevcut", true);
//			JCheckBox jc2 = new JCheckBox("Yok", false);
//			JCheckBox jc3 = new JCheckBox("Ýzinli", false);

			paneltekogr.add(num);
			paneltekogr.add(name);
//			paneltekogr.add(jc);
//			paneltekogr.add(jc2);
//			paneltekogr.add(jc3);
			
			students.put(noList[i], paneltekogr);

			panelogr.add(paneltekogr);

		}
		panelalt = new JPanel(new GridLayout(0, 1));
		panelalt.add(btnKaydet);

		add(panelust, BorderLayout.NORTH);
		add(panelogr, BorderLayout.CENTER);
		add(panelalt, BorderLayout.SOUTH);

		btnKaydet.addActionListener(this);

		setTitle("Yoklama");
		
		

//		isHere("1");
//		isHere("5");
//		isHere("9");
//		isHere("10");
//		isHere("11");
//		isHere("12");
//		isHere("13");
//		isHere("14");
//		
//		isAbsent("2");
//		isAbsent("3");
//		isAbsent("6");
		
		
		setLocation(200, 50);
		setSize(800, 600);
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand().equals("Kaydet")) {
			this.setVisible(false);
		}

	}
	
	public void isHere(String studentID)
	{
		JPanel stu= students.get(studentID);
		stu.setBackground(Color.GREEN);
		pack();
	}
	
	public void isAbsent(String studentID)
	{
		JPanel stu= students.get(studentID);
		stu.setBackground(Color.RED);
	}

	public static void main(String arg[]) {
		AttendanceFrame frame = new AttendanceFrame("demo");
		frame.setVisible(true);

	}
}
