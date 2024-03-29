package com.argeloji.server;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
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

import org.jfree.ui.RefineryUtilities;

import com.argeloji.cons.Enums.AnswerType;
import com.argeloji.cons.Enums.MessageType;
import com.argeloji.entity.Question;
import com.argeloji.entity.ServerDispatcher;
import com.argeloji.util.ImageFileFilter;
import com.argeloji.util.ImagePanel;
import com.argeloji.util.LabelAccessory;
import com.argeloji.util.PicturePanel;

public class QuizTeacherDesktop extends JFrame implements ActionListener {

	private final int MAX_WIDTH = 800;
	private final int MAX_HEIGHT = 600;

	JPanel panelSure, panelsoru, panelalt;

	JRadioButton ja, jb, jc, jd, je;
	JButton jbGonder, jbSoruSec;
	ImagePanel img;
	int saat, dakika, saniye;

	JComboBox cbSaat, cbDakika, cbSaniye, cbSecenekSayisi;

	File selFile;

	QuizTeacherDesktop() {

		panelsoru = new JPanel(new GridLayout(1, 1));

		panelSure = new JPanel(new GridLayout(1, 9));

		cbSaniye = new JComboBox();
		cbDakika = new JComboBox();
		cbSaat = new JComboBox();
		cbSaat.addItem("0");
		cbSaat.addItem("1");
		cbSaat.addItem("2");

		cbSecenekSayisi = new JComboBox();
		cbSecenekSayisi.addItem("2");
		cbSecenekSayisi.addItem("3");
		cbSecenekSayisi.addItem("4");
		cbSecenekSayisi.addItem("5");

		for (int i = 0; i < 60; i++) {
			cbDakika.addItem(i);
			if (i % 5 == 0)
				cbSaniye.addItem(i);
		}
		panelSure.add(new JLabel("S�re Se�iniz:"));
		panelSure.add(cbSaat);
		panelSure.add(new JLabel("saat"));
		panelSure.add(cbDakika);
		panelSure.add(new JLabel("dakika"));
		panelSure.add(cbSaniye);
		panelSure.add(new JLabel("saniye"));
		panelSure.add(new JLabel("Se�enek Say�s�:"));
		panelSure.add(cbSecenekSayisi);

		panelalt = new JPanel(new GridLayout(3, 1));
		jbSoruSec = new JButton("Soru Se�");
		jbSoruSec.addActionListener(this);
		panelalt.add(jbSoruSec);

		jbGonder = new JButton("G�nder");
		jbGonder.addActionListener(this);

		panelalt.add(jbGonder);
		add(panelSure, BorderLayout.NORTH);
		add(panelsoru, BorderLayout.CENTER);
		add(panelalt, BorderLayout.SOUTH);

		setTitle("Quiz : ��retmen");

		setLocation(200, 50);
		setSize(850, 600);

	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand().equals("G�nder")) {

			this.setVisible(false);

			// duration
			Calendar duration = new GregorianCalendar();
			duration.set(Calendar.HOUR,
					Integer.parseInt(cbSaat.getSelectedItem().toString()));
			duration.set(Calendar.MINUTE,
					Integer.parseInt(cbDakika.getSelectedItem().toString()));
			duration.set(Calendar.SECOND,
					Integer.parseInt(cbDakika.getSelectedItem().toString()));

			// number of choices
			byte numOfChoices = Byte.parseByte(cbSecenekSayisi
					.getSelectedItem().toString());

			Question q1 = new Question(AnswerType.MultipleChoice, numOfChoices,
					duration, selFile);

			ServerDispatcher sd= ServerDispatcher.getInstance();
			sd.send(MessageType.SendQuestion, q1);
			
			BarChartDemo4 demo = new BarChartDemo4("Cevap Da��l�m�");
	        demo.pack();
	        RefineryUtilities.centerFrameOnScreen(demo);
	        demo.setVisible(true);
			
			
			// QuizClientDesktop qc = new QuizClientDesktop(
			// Integer.parseInt(cbSaat.getSelectedItem().toString()),
			// Integer.parseInt(cbDakika.getSelectedItem().toString()),
			// Integer.parseInt(cbDakika.getSelectedItem().toString()),
			// selFile,
			// Integer.parseInt(cbSecenekSayisi.getSelectedItem().toString()));
			// qc.setVisible(true);
		}
		if (evt.getActionCommand().equals("Soru Se�")) {
			JFileChooser fileChooser = new JFileChooser();
			
			// C: test klas�r�n�n default klas�r yapmak i�in
		    File f=null;
			try {
				f = new File(new File("C:\\test\\").getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			fileChooser.setCurrentDirectory(f);

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
			// tekrar soru se�ti�i zaman resimler yan yana geliyor. son se�ilen
			// g�nderiliyor oras� ok.
			PicturePanel pp = new PicturePanel(selFile);
			panelsoru.add(pp);

			pack();

		}
	}

	public static void main(String arg[]) {
		QuizTeacherDesktop frame = new QuizTeacherDesktop();
		frame.setVisible(true);

	}
}
