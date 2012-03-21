package com.easycapture.recorder;

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

public class AttendanceFrame extends JFrame implements ActionListener {

	JButton btnKaydet;
	JComboBox cbSinif, cbDers;
	JPanel panelust, panelogr, panelalt;
	JLabel label1;

	AttendanceFrame(String sinif) {

		label1 = new JLabel("Sýnýf: " + sinif);
		label1.setFont(new Font("Tahoma", Font.BOLD, 20));

		String[] ogrList = { "MÜNÝRE GAMZE EKÝZER", "YASÝN SARTIK",
				"YASÝN SARTIK", "HALÝME DALBOY", "HALÝME DALBOY",
				"MÜNÝRE GAMZE EKÝZER", "MÜNÝRE GAMZE EKÝZER",
				"MÜNÝRE GAMZE EKÝZER", "MÜNÝRE GAMZE EKÝZER",
				"MÜNÝRE GAMZE EKÝZER", "MÜNÝRE GAMZE EKÝZER",
				"MÜNÝRE GAMZE EKÝZER", "MÜNÝRE GAMZE EKÝZER",
				"MÜNÝRE GAMZE EKÝZER", "MÜNÝRE GAMZE EKÝZER",
				"MÜNÝRE GAMZE EKÝZER", "MÜNÝRE GAMZE EKÝZER",
				"MÜNÝRE GAMZE EKÝZER", "MÜNÝRE GAMZE EKÝZER",
				"MÜNÝRE GAMZE EKÝZER" };
		String[] noList = { "070401001", "070401004", "070401007", "070401001",
				"070401001", "070401001", "070401001", "070401015",
				"070401001", "070401001", "070401001", "070401065",
				"070401001", "070401001", "070401001", "070401541",
				"070401001", "070401001", "070433301", "070432001" };

		btnKaydet = new JButton("Kaydet");

		panelust = new JPanel(new GridLayout(0, 1));
		panelust.add(label1);

		panelogr = new JPanel(new GridLayout(10, 2));

		for (int i = 0; i < 20; i++) {
			JPanel paneltekogr = new JPanel(new GridLayout(0, 3));			
			
			JLabel num = new JLabel(noList[i]);
			num.setIcon(new ImageIcon("child.jpg"));
			JLabel name = new JLabel(ogrList[i]);
			JCheckBox jc = new JCheckBox("Mevcut", true);
//			JCheckBox jc2 = new JCheckBox("Yok", false);
//			JCheckBox jc3 = new JCheckBox("Ýzinli", false);

			paneltekogr.add(num);
			paneltekogr.add(name);
			paneltekogr.add(jc);
//			paneltekogr.add(jc2);
//			paneltekogr.add(jc3);

			panelogr.add(paneltekogr);

		}
		panelalt = new JPanel(new GridLayout(0, 1));
		panelalt.add(btnKaydet);

		add(panelust, BorderLayout.NORTH);
		add(panelogr, BorderLayout.CENTER);
		add(panelalt, BorderLayout.SOUTH);

		btnKaydet.addActionListener(this);

		setTitle("Yoklama");

		setLocation(200, 50);
		setSize(800, 600);
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand().equals("Kaydet")) {
			this.setVisible(false);
		}

	}

	public static void main(String arg[]) {
		AttendanceFrame frame = new AttendanceFrame("demo");
		frame.setVisible(true);

	}
}
