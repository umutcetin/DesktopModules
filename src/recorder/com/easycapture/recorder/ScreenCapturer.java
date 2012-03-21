package com.easycapture.recorder;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Toolkit;
import java.awt.Dimension;
import javax.imageio.ImageIO;

public class ScreenCapturer {

	/**
	 * @param x1 baslangic x
	 * @param y1 baslangic y
	 * @param width genislik
	 * @param height yukseklik
	 * @throws IOException 
	 */
	public void draw(int x1, int y1, int width, int height) throws IOException {
		
		//to find the screen dimension

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		//Rectangle rectangle = new Rectangle(0, 0, screenSize.width, screenSize.height);
		Rectangle rectangle = new Rectangle(x1, y1, width, height);
		
		//to get the BufferedImage with createScreenCapture method

		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferedImage image = robot.createScreenCapture(rectangle);
          

		//convert BufferedImage to JPG File

		File file = new File("screen.jpg");
		ImageIO.write(image, "jpg", file);
		

	}

}
