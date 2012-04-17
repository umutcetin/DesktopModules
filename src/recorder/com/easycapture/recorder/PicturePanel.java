package com.easycapture.recorder;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PicturePanel extends JPanel {
	
	private final int MAX_WIDTH = 800;
	private final int MAX_HEIGHT = 600;
	
	PicturePanel(File file)
	{
BufferedImage myPicture = null;
		
		double factor = 1;
		try {
			myPicture = ImageIO.read(file);
			if (myPicture.getWidth() > MAX_WIDTH) {
				factor = (double) MAX_WIDTH / myPicture.getWidth();
				int newheight= (int) (myPicture.getHeight()*factor);//!
				
				// Create new (blank) image of required (scaled) size

				BufferedImage scaledImage = new BufferedImage(
						MAX_WIDTH, newheight, BufferedImage.TYPE_INT_ARGB);

				// Paint scaled version of image to new image

				Graphics2D graphics2D = scaledImage.createGraphics();
				graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				graphics2D.drawImage(myPicture, 0, 0, MAX_WIDTH, newheight, null);

				// clean up
				myPicture= scaledImage;
				graphics2D.dispose();
			}	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		this.add(picLabel);
	}
}
