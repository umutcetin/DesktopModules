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

public class ShowImageWindow extends JFrame implements ActionListener {

	JPanel panelsoru;
	
	ImagePanel img;	

	File selFile;

	ShowImageWindow() {

		panelsoru = new JPanel(new GridLayout(1, 1));

		add(panelsoru, BorderLayout.CENTER);
		
		img = new ImagePanel("u.jpg");
		panelsoru.add(img);

		setTitle("Resim Görüntüleme");

		setLocation(200, 50);
		setSize(550, 400);

	}

	

	public static void main(String arg[]) {
		ShowImageWindow frame = new ShowImageWindow();
		frame.setVisible(true);

	}



	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
