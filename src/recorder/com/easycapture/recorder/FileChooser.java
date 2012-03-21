package com.easycapture.recorder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class FileChooser extends JFrame implements ActionListener {

	// JButton btnBrowse;
	JButton btnSend;
	JLabel flabel;
	JLabel flabelLM;
	JLabel flabelSize;

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

		fileChooser.showOpenDialog(null);
		File selFile = fileChooser.getSelectedFile();

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
		}
	}
}