package com.easycapture.recorder;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.imgscalr.Scalr;

class ImagePanel extends JPanel {

	private final int MAX_WIDTH = 800;
	private final int MAX_HEIGHT = 600;

	private Image img, img2;
	private Dimension size;

	public ImagePanel(String img) {
		this(new ImageIcon(img).getImage());
	}

	public ImagePanel(Image img) {
		
		this.img = img;
		
		int width = img.getWidth(null);
		int height= img.getHeight(null);
		System.out.println(width);
		
//		double factor = 1;
//		if (width > MAX_WIDTH) {
//			factor = (double) MAX_WIDTH / width;
//			int newheight= (int) (height*factor);//!
//			System.out.println(factor);
//			System.out.println(newheight);
//			img2= new ImageIcon(img).getImage();
//			this.img = img2.getScaledInstance(MAX_WIDTH, newheight, Image.SCALE_DEFAULT);
//		}	
//		else
//			this.img = img;
		
		size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);

	}

	public ImagePanel() {
		// TODO Auto-generated constructor stub
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

}
