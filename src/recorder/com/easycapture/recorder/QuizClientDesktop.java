package com.easycapture.recorder;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class QuizClientDesktop extends JFrame implements ActionListener {	
	
	JPanel panelust, panelsoru, panelalt;
	JLabel labelTime;
	JRadioButton ja,jb,jc,jd,je;
	JButton jbGonder;
	ImagePanel img;
	int saat, dakika, saniye;
	
	ArrayList<JRadioButton> secenekler= new ArrayList<JRadioButton>();

	QuizClientDesktop(int saat, int dakika, int saniye, File soruFile, int secenekSayisi) {
		
		this.saat=saat;
		this.dakika=dakika;
		this.saniye=saniye;

		panelust = new JPanel(new GridLayout(1, 1));
		labelTime= new JLabel();
		panelust.add(labelTime);

		panelsoru = new JPanel(new GridLayout(1, 1));
		PicturePanel pp= new PicturePanel(soruFile);
		panelsoru.add( pp );	
		
		panelalt = new JPanel(new GridLayout(1, 6));
		
		
		for (int i=1; i<=secenekSayisi; i++)
		{
			String harf = null;
			if(i==1) harf="a";
			else if(i==2) harf="b";
			else if(i==3) harf="c";
			else if(i==4) harf="d";
			else if(i==5) harf="e";
			
			JRadioButton rbuton= new JRadioButton(harf);
			secenekler.add(rbuton);
			panelalt.add(rbuton);
		}
		
		jbGonder= new JButton("Gönder");
		jbGonder.addActionListener(this);
		panelalt.add(jbGonder);

		add(panelust, BorderLayout.NORTH);	
		add(panelsoru, BorderLayout.CENTER);
		add(panelalt, BorderLayout.SOUTH);

		setTitle("Quiz : Öðrenci");

		setLocation(200, 50);
		
		BufferedImage myPicture=null;
		try {
			myPicture= ImageIO.read(soruFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		setSize(850,600);
		
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
			long timeNow= System.currentTimeMillis();//load test için			
			
			long time= ((saat*60*60)+(dakika*60)+saniye);
			//long time= ((saat*60+dakika)*60 +saniye)*1000;			
			//system.current time millis kullanýrsak sistem saatiyle oynayarak cheat yapýlabilir
			for (long t=time; t >= 0; t=t-1) {
				//dakika ve saat bulmak için iyi bir yöntem deðil
				labelTime.setText("Kalan süre: "+ t / (60*60) +" saat "+ (t / 60) % 60 + " dakika "+ t%(60) +" saniye");
				Thread.sleep(1000);
				
				//load test. passed.
				System.out.println(System.currentTimeMillis()-timeNow);
				timeNow= System.currentTimeMillis();
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
			
			String result="";
			for(JRadioButton j: secenekler)
			{
				if (j.isSelected())
					result+= j.getText()+", ";
			}
			JOptionPane.showMessageDialog(new JFrame(), "seçilenler: "+result);
		}

	}

	public static void main(String arg[]) {
		QuizClientDesktop frame = new QuizClientDesktop(1,0,3,new File("u.jpg"),5);
		frame.setVisible(true);

	}
}
