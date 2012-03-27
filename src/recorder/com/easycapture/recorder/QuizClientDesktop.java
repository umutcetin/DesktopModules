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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class QuizClientDesktop extends JFrame implements ActionListener {	
	
	JPanel panelust, panelsoru, panelalt;
	JLabel labelTime;
	JRadioButton ja,jb,jc,jd,je;
	JButton jbGonder;
	ImagePanel img;
	int saat, dakika, saniye;
	

	QuizClientDesktop(int saat, int dakika, int saniye, String soruUrl) {
		
		this.saat=saat;
		this.dakika=dakika;
		this.saniye=saniye;

		panelust = new JPanel(new GridLayout(1, 1));
		labelTime= new JLabel();
		panelust.add(labelTime);

		panelsoru = new JPanel(new GridLayout(1, 1));
		img= new ImagePanel(soruUrl);
		panelsoru.add(img);
		
		panelalt = new JPanel(new GridLayout(1, 6));
		ja= new JRadioButton("a");
		jb= new JRadioButton("b");
		jc= new JRadioButton("c");
		jd= new JRadioButton("d");
		je= new JRadioButton("e");
		jbGonder= new JButton("Gönder");
		jbGonder.addActionListener(this);
		
		panelalt.add(ja);
		panelalt.add(jb);
		panelalt.add(jc);
		panelalt.add(jd);
		panelalt.add(je);
		panelalt.add(jbGonder);

		add(panelust, BorderLayout.NORTH);	
		add(panelsoru, BorderLayout.CENTER);
		add(panelalt, BorderLayout.SOUTH);

		setTitle("Quiz : Öðrenci");

		setLocation(200, 50);
		setSize(500, 400);
		
		TimerThread();
	}
	
	public void TimerThread() {
		try {
			Runnable r = new Runnable() {
				public void run() {
					startTimer();
				}
			};
			Thread Timer = new Thread(r, "Timer Thread");
			Timer.start();
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(new JFrame(), exc);
		}
	}

	public void startTimer() {
		try {
			long time= ((saat*60*60)+(dakika*60)+saniye);
			//long time= ((saat*60+dakika)*60 +saniye)*1000;			
			//system.current time millis kullanýrsak sistem saatiyle oynayarak cheat yapýlabilir
			for (long t=time; t >= 0; t=t-1) {
				//dakika ve saat bulmak için iyi bir yöntem deðil
				labelTime.setText("Kalan süre: "+ t / (60*60) +" saat "+ (t / 60) % 60 + " dakika "+ t%(60) +" saniye");
				Thread.sleep(1000);
			}
			JOptionPane.showMessageDialog(this, "Süre doldu.");
			this.setVisible(false);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(new JFrame(), exc);
		}
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand().equals("Gönder")) {
			this.setVisible(false); //Buraya ve close iþlemlerine bir düzenleme yapýlmasý gerekiyor. 
			//sayacýn pencere kapandýktan sonra devam edip uyarý vermemesi için
		}

	}

	public static void main(String arg[]) {
		QuizClientDesktop frame = new QuizClientDesktop(0,0,3,"soru.jpg");
		frame.setVisible(true);

	}
}
