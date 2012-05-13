package com.argeloji.server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.argeloji.cons.Enums.FileType;
import com.argeloji.cons.Enums.MessageType;
import com.argeloji.entity.FileContainer;
import com.argeloji.entity.ServerDispatcher;
import com.argeloji.util.LabelAccessory;
import com.sun.media.rtsp.Server;

public class FileChooser extends JFrame implements ActionListener {

	// JButton btnBrowse;
	JButton btnSend;
	JLabel flabel;
	JLabel flabelLM;
	JLabel flabelSize;
	
	File selFile;

	FileChooser() {

		// Create and set up the window.
		super("Dosya Gönder");

		flabel = new JLabel("");
		flabelLM = new JLabel("");
		flabelSize = new JLabel("");
		
		JPanel labelPanel = new JPanel(new GridLayout(0, 1));
		

		labelPanel.setBorder(new EmptyBorder(new Insets(40, 60, 40, 60)));

		labelPanel.add(flabel);
		labelPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		labelPanel.add(flabelLM);
		labelPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		labelPanel.add(flabelSize);

		getContentPane().add(labelPanel, BorderLayout.NORTH);


		setLocation(500, 300);

		// btnBrowse = new JButton("Gözat");
		// btnBrowse.addActionListener(this);

		btnSend = new JButton("Gönder");
		btnSend.addActionListener(this);

		JPanel buttonPanel = new JPanel();

		// buttonPanel.add(btnBrowse);
		buttonPanel.add(btnSend);

		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		// Display the window.
		setSize(400, 200);
		pack();
		setVisible(true);

		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setAccessory(new LabelAccessory(fileChooser));
		
		// C: test klasörünün default klasör yapmak için
	    File f=null;
		try {
			f = new File(new File("C:\\test\\").getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fileChooser.setCurrentDirectory(f);

		fileChooser.showOpenDialog(null);
		selFile = fileChooser.getSelectedFile();

		flabel.setText("Seçilen Dosya: " + selFile.getName());
		flabelSize.setText("Boyut: " + selFile.length() / 1024 + " Kb");
		flabelLM.setText("Degiþtirme Tarihi: "
				+ new Date(selFile.lastModified()));

	}

	public static void main(String[] args) {
		new FileChooser();
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand().equals("Gönder")) {
			this.setVisible(false);		
			
			String extension= selFile.getAbsolutePath().split("\\.")[1];
			
			FileContainer fc;
			
			if (extension.equals("jpg"))
				fc= new FileContainer(selFile, FileType.Image);
			else if (extension.equals("mp4"))
				fc= new FileContainer(selFile, FileType.Video);
			else 
				fc= new FileContainer(selFile, FileType.Other);
			
			ServerDispatcher sd= ServerDispatcher.getInstance();
			
			sd.send(MessageType.SendFile, fc);
			
			
		}
	}
}