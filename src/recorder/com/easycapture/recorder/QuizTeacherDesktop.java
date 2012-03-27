package com.easycapture.recorder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class QuizTeacherDesktop extends JFrame implements ActionListener {

	JPanel panelSure, panelsoru, panelalt;

	JRadioButton ja, jb, jc, jd, je;
	JButton jbGonder, jbSoruSec;
	ImagePanel img;
	int saat, dakika, saniye;

	JComboBox cbSaat, cbDakika, cbSaniye;

	File selFile;

	QuizTeacherDesktop() {

		panelsoru = new JPanel(new GridLayout(1, 1));

		panelSure = new JPanel(new GridLayout(1, 7));

		cbSaniye = new JComboBox();
		cbDakika = new JComboBox();
		cbSaat = new JComboBox();
		cbSaat.addItem("0");
		cbSaat.addItem("1");
		cbSaat.addItem("2");

		for (int i = 0; i < 60; i++) {
			cbDakika.addItem(i);
			if (i % 5 == 0)
				cbSaniye.addItem(i);
		}
		panelSure.add(new JLabel("Süre Seçiniz:"));
		panelSure.add(cbSaat);
		panelSure.add(new JLabel("saat"));
		panelSure.add(cbDakika);
		panelSure.add(new JLabel("dakika"));
		panelSure.add(cbSaniye);
		panelSure.add(new JLabel("saniye"));

		panelalt = new JPanel(new GridLayout(3, 1));
		jbSoruSec = new JButton("Soru Seç");
		jbSoruSec.addActionListener(this);
		panelalt.add(jbSoruSec);

		jbGonder = new JButton("Gönder");
		jbGonder.addActionListener(this);

		panelalt.add(jbGonder);
		add(panelSure, BorderLayout.NORTH);
		add(panelsoru, BorderLayout.CENTER);
		add(panelalt, BorderLayout.SOUTH);

		setTitle("Quiz : Öðretmen");

		setLocation(200, 50);
		setSize(550, 400);

	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand().equals("Gönder")) {
			this.setVisible(false);
			QuizClientDesktop qc = new QuizClientDesktop(
					Integer.parseInt(cbSaat.getSelectedItem().toString()),
					Integer.parseInt(cbDakika.getSelectedItem().toString()),
					Integer.parseInt(cbSaniye.getSelectedItem().toString()),
					selFile.getName());
			qc.setVisible(true);
		}
		if (evt.getActionCommand().equals("Soru Seç")) {
			JFileChooser fileChooser = new JFileChooser();

			fileChooser.setAccessory(new LabelAccessory(fileChooser));

			ImageFileFilter im = new ImageFileFilter();
			im.addExtension("jpg");
			im.addExtension("JPG");
			im.addExtension("gif");
			im.addExtension("BMP");
			im.addExtension("png");

			fileChooser.addChoosableFileFilter(im);

			fileChooser.showOpenDialog(null);
			selFile = fileChooser.getSelectedFile();
			// tekrar soru seçtiði zaman resimler yan yana geliyor. son seçilen
			// gönderiliyor orasý ok.
			img = new ImagePanel(selFile.getName());
			panelsoru.add(img);

			pack();

		}
	}

	public static void main(String arg[]) {
		QuizTeacherDesktop frame = new QuizTeacherDesktop();
		frame.setVisible(true);

	}
}
