package com.easycapture.recorder;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

class ImagePanel extends JPanel {

	  private Image img;
	  private Dimension size;

	  public ImagePanel(String img) {
	    this(new ImageIcon(img).getImage());
	  }

	  public ImagePanel(Image img) {
	    this.img = img;
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
	    g.drawImage(img, size.width, size.height, null);
	  }

	}
