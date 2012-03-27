package com.easycapture.recorder;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JWindow;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.IRational;

public class ResizableJWindow extends JWindow implements ActionListener,
		MouseMotionListener {

	// for mouse events
	private String windowKonum;

	// ses
	private TargetDataLine m_line;
	private AudioFileFormat.Type m_targetType;
	private AudioInputStream m_audioInputStream;
	private File m_outputFile;

	// video
	private static IRational FRAME_RATE = IRational.make(3, 1);

	final String outFile = "output44.mp4";

	private AtomicBoolean paused, stopped, pausedSes;

	private Rectangle bounds;
	private Thread threadObject, threadSes;
	JWindow jwust, jwsag, jwsol;
	JButton btnStart, btnStop, btnPause, btnClose, btnDrag, btnCapture;

	final IMediaWriter writer = ToolFactory.makeWriter(outFile);

	/*
	 * Now, we are creating an SimpleAudioRecorder object. It contains the logic
	 * of starting and stopping the recording, reading audio data from the
	 * TargetDataLine and writing the data to a file.
	 */
	SimpleAudioRecorder recorder = null;

	// CONSTRUCTOR
	// sýnýfýn kendisi pencerenin alt kýsmý, diðer kenarlarý yaratýyor
	ResizableJWindow(final Rectangle bounds, boolean Snapshot) {

		// ses kaydý init
		File outputFile = new File("voice.wav");

		/*
		 * For simplicity, the audio data format used for recording is hardcoded
		 * here. We use PCM 44.1 kHz, 16 bit signed, stereo.
		 */
		AudioFormat audioFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED, 44100.0F, 16, 2, 4, 44100.0F,
				false);

		/*
		 * Now, we are trying to get a TargetDataLine. The TargetDataLine is
		 * used later to read audio data from it. If requesting the line was
		 * successful, we are opening it (important!).
		 */
		DataLine.Info info = new DataLine.Info(TargetDataLine.class,
				audioFormat);
		TargetDataLine targetDataLine = null;
		try {
			targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
			targetDataLine.open(audioFormat);
		} catch (LineUnavailableException e) {

			e.printStackTrace();
			System.exit(1);
		}

		/*
		 * Again for simplicity, we've hardcoded the audio file type, too.
		 */
		AudioFileFormat.Type targetType = AudioFileFormat.Type.WAVE;

		m_line = targetDataLine;
		m_audioInputStream = new AudioInputStream(targetDataLine);
		m_targetType = targetType;
		m_outputFile = outputFile;

		// recorder = new SimpleAudioRecorder(targetDataLine, targetType,
		// outputFile);//

		// ses kaydý init sonu

		paused = new AtomicBoolean(false);
		stopped = new AtomicBoolean(false);
		pausedSes = new AtomicBoolean(false);
		this.bounds = bounds;

		jwust = new JWindow();// ust border bir jwindow
		jwsol = new JWindow();// sol border bir jwindow
		jwsag = new JWindow();// sag border bir jwindow

		draw();// bounds a gore pencereler çizilir

		// renklendirme
		//max pencere boyutu 3000x2000
		ImagePanel colorpanel = new ImagePanel(new ImageIcon("b.jpg").getImage());
		//colorpanel.setBackground(Color.RED);
		JPanel cp2 = new JPanel();
		cp2.setBackground(Color.LIGHT_GRAY);
		ImagePanel cp3 = new ImagePanel(new ImageIcon("b.jpg").getImage());
		//cp3.setBackground(Color.RED);
		ImagePanel cp4 = new ImagePanel(new ImageIcon("b.jpg").getImage());
		//cp4.setBackground(Color.RED);

		// butonlar ve listener
		btnCapture = new JButton("Yakala");
		cp2.add(btnCapture);
		btnCapture.addActionListener(this);

		btnStart = new JButton("Baþla");
		cp2.add(btnStart);
		btnStart.addActionListener(this);

		btnPause = new JButton("Durakla");
		cp2.add(btnPause);
		btnPause.addActionListener(this);

		btnStop = new JButton("Bitir");
		cp2.add(btnStop);
		btnStop.addActionListener(this);

		btnClose = new JButton("Kapat");
		cp2.add(btnClose);
		btnClose.addActionListener(this);

		btnDrag = new JButton("Taþý");
		cp2.add(btnDrag);

		// cursorlar
		btnCapture.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		btnStart.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		btnPause.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		btnStop.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		btnClose.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		btnDrag.setCursor(new Cursor(Cursor.MOVE_CURSOR));

		if (Snapshot)// resim yakalama penceresi açýlacaksa
		{
			btnStart.setVisible(false);
			btnPause.setVisible(false);
			btnStop.setVisible(false);
		} else
			btnCapture.setVisible(false);

		// renkli panellerin eklenmesi
		jwust.add(colorpanel);
		this.add(cp2);
		jwsol.add(cp3);
		jwsag.add(cp4);

		// drag iþlemi mouse listener
		btnDrag.addMouseMotionListener(this);
		colorpanel.addMouseMotionListener(this);
		cp3.addMouseMotionListener(this);
		cp4.addMouseMotionListener(this);
		cp2.addMouseMotionListener(this);

		// her zaman üstte
		jwust.setAlwaysOnTop(true);
		this.setAlwaysOnTop(true);
		jwsol.setAlwaysOnTop(true);
		jwsag.setAlwaysOnTop(true);

		// görünürlük
		jwust.setVisible(true);
		this.setVisible(true);
		jwsol.setVisible(true);
		jwsag.setVisible(true);

		// video yakalama runnable
		Runnable runnable = new Runnable() {
			public void run() {
				try {

					/*
					 * We are waiting for the user to press ENTER to start the
					 * recording. (You might find it inconvenient if recording
					 * starts immediately.)
					 */

					// recorder.start();
					// System.out.println("Recording...");

					// This is the robot for taking a snapshot of the
					// screen. It's part of Java AWT
					final Robot robot = new Robot();
					final Toolkit toolkit = Toolkit.getDefaultToolkit();
					Rectangle screenBounds = bounds;

					// bunu yukarýda public yaptýk
					// First, let's make a IMediaWriter to write the file.
					// final IMediaWriter writer =
					// ToolFactory.makeWriter(outFile);

					// We tell it we're going to add one video stream, with id
					// 0,
					// at position 0, and that it will have a fixed frame rate
					// of
					// FRAME_RATE.
					writer.addVideoStream(0, 0, FRAME_RATE, screenBounds.width,
							screenBounds.height);

					System.out.println("görüntü kaydý baþladý.");
					// Now, we're going to loop
					int index = 0;
					long startTime = System.nanoTime();
					while (!stopped.get()) {

						if (paused.get()) {
							synchronized (threadObject) {
								// Pause
								try {
									System.out
											.println("video kaydý duraklamalý");
									// m_audioInputStream.close();
									threadObject.wait();
									// threadSes.wait();
								} catch (InterruptedException e) {
								}
							}
						}
						// take the screen shot
						BufferedImage screen = robot
								.createScreenCapture(screenBounds);

						// convert to the right image type
						BufferedImage bgrScreen = convertToType(screen,
								BufferedImage.TYPE_3BYTE_BGR);

						// encode the image
						writer.encodeVideo(0, bgrScreen, System.nanoTime()
								- startTime, TimeUnit.NANOSECONDS);

						System.out.println("encoded image: " + index);
						index++;

						// sleep for framerate milliseconds
						Thread.sleep((long) (1000 / FRAME_RATE.getDouble()));
					}
					// Finally we tell the writer to close and write the trailer
					// if
					// needed

					writer.close();
					// recorder.stopRecording();
					System.out.println("Recording stopped.");

					// ses ve görüntü yakalandý. synch et yeni video yap.

					ConcatenateAudioAndVideo c = new ConcatenateAudioAndVideo();
					c.concatenate("output44.mp4", "voice.wav",
							System.currentTimeMillis() + ".mp4", bounds);

				}

				catch (Throwable e) {
					System.err.println("an error occurred: " + e.getMessage());
				}
			}

		};
		threadObject = new Thread(runnable);

		Runnable runnableSes = new Runnable() {
			public void run() {
				System.out.println("ses kaydý baþladý");
				try {
					if (pausedSes.get()) {
						synchronized (threadSes) {
							// Pause
							try {
								System.out.println("ses kaydý duraklamalý");
								threadSes.wait();
							} catch (InterruptedException e) {
							}
						}
					}
					AudioSystem.write(m_audioInputStream, m_targetType,
							m_outputFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		};
		threadSes = new Thread(runnableSes);
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand().equals("Kapat")) {
			jwust.setVisible(false);
			this.setVisible(false);
			jwsol.setVisible(false);
			jwsag.setVisible(false);
			m_line.close();
		}
		if (evt.getActionCommand().equals("Yakala")) {
			// Resim yakala
			// resim yakalama iþlemleri
			// resim yakala butonuna basýldýðýnda resim capture et
			ScreenCapturer sc = new ScreenCapturer();
			try {
				// koordinatlarý bu þekilde manuel vermek sakat. bilgisayara
				// göre pencere kalýnlýklarý deðiþebilir.
				sc.draw((int) bounds.getX() + 10, (int) bounds.getY() + 30,
						(int) bounds.getWidth() - 17,
						(int) bounds.getHeight() - 72);
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (evt.getActionCommand().equals("Baþla")) {
			threadObject.start();
			m_line.start();
			threadSes.start();

		}
		if (evt.getActionCommand().equals("Durakla")) {
			if (!paused.get()) {
				btnPause.setText("Devam");
				paused.set(true);
				pausedSes.set(true);
				m_line.stop();
			}
		}
		if (evt.getActionCommand().equals("Bitir")) {
			m_line.stop();
			m_line.close();
			stopped.set(true);
		}
		if (evt.getActionCommand().equals("Devam")) {
			btnPause.setText("Durakla");
			paused.set(false);
			pausedSes.set(false);
			m_line.start();
			System.out.println("Devam!");
			// Resume
			synchronized (threadSes) {
				System.out.println("ses kaydý devam etmeli");
				threadSes.notify();
			}
			synchronized (threadObject) {
				System.out.println("görüntü kaydý devam etmeli");
				threadObject.notify();
			}
		}

	}

	public static BufferedImage convertToType(BufferedImage sourceImage,
			int targetType) {
		BufferedImage image;

		// if the source image is already the target type, return the source
		// image

		if (sourceImage.getType() == targetType)
			image = sourceImage;

		// otherwise create a new image of the target type and draw the new
		// image

		else {
			image = new BufferedImage(sourceImage.getWidth(),
					sourceImage.getHeight(), targetType);
			image.getGraphics().drawImage(sourceImage, 0, 0, null);
		}

		return image;
	}

	public static void main(String[] args) {
		new ResizableJWindow(new Rectangle(100, 100, 800, 400), true);
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void draw() {// bounds adlý rectangle a göre çizim yapar
		jwust.setSize(bounds.width, 15);
		jwust.setLocation(bounds.x, bounds.y);
		jwust.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));

		this.setSize(bounds.width + 15, 35);
		this.setLocation(bounds.x, bounds.y + bounds.height);
		setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));

		jwsol.setSize(15, bounds.height);
		jwsol.setLocation(bounds.x, bounds.y);
		jwsol.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));

		jwsag.setSize(15, bounds.height);
		jwsag.setLocation(bounds.x + bounds.width, bounds.y);
		jwsag.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
	}

	public void mouseDragged(MouseEvent arg0) {
		if (arg0.getSource().equals(btnDrag))// taþý
		{
			System.out.println("taþý");
			this.bounds.x += arg0.getX();// baþlangýç koordinatlarýný delta
											// cursor kadar taþý
			this.bounds.y += arg0.getY();
		} 
		else 
		{// hangi kenarýn tutulduðunu bul. köþelerin tutulmasýný da bul
				// (to do)
			if (arg0.getXOnScreen() < this.bounds.getMaxX() + 25
					&& arg0.getXOnScreen() > this.bounds.getMaxX() - 25
					&& arg0.getYOnScreen() < this.bounds.getMaxY() + 35
					&& arg0.getYOnScreen() > this.bounds.getMaxY() - 15) {
				System.out.println("sag alt");
				windowKonum = "sag alt";
			}else
			if (arg0.getXOnScreen() < this.bounds.getMaxX() + 15
					&& arg0.getXOnScreen() > this.bounds.getMaxX() - 15
					&& arg0.getYOnScreen() < this.bounds.y + 15
					&& arg0.getYOnScreen() > this.bounds.y - 15) {
				System.out.println("sag ust");
				windowKonum = "sag ust";
			}else
			if (arg0.getXOnScreen() < this.bounds.x + 15
					&& arg0.getXOnScreen() > this.bounds.x - 15
					&& arg0.getYOnScreen() < this.bounds.y + 15
					&& arg0.getYOnScreen() > this.bounds.y - 15) {
				System.out.println("sol ust");
				windowKonum = "sol ust";
			}else
			if (arg0.getXOnScreen() < this.bounds.x + 15
					&& arg0.getXOnScreen() > this.bounds.x - 15
					&& arg0.getYOnScreen() < this.bounds.getMaxY() + 35
					&& arg0.getYOnScreen() > this.bounds.getMaxY() - 15) {
				System.out.println("sol alt");
				windowKonum = "sol alt";
			}else
			if (arg0.getXOnScreen() < this.bounds.x + 15
					&& arg0.getXOnScreen() > this.bounds.x - 15)// sol
			{
				System.out.println("sol");
				windowKonum = "sol";

			}else
			if (arg0.getXOnScreen() < this.bounds.getMaxX() + 15
					&& arg0.getXOnScreen() > this.bounds.getMaxX() - 15) {
				System.out.println("sag");
				windowKonum = "sag";

			}else
			if (arg0.getYOnScreen() < this.bounds.getMaxY() + 35
					&& arg0.getYOnScreen() > this.bounds.getMaxY() - 15) {
				System.out.println("alt");
				windowKonum = "alt";

			}else
			if (arg0.getYOnScreen() < this.bounds.y + 15
					&& arg0.getYOnScreen() > this.bounds.y - 15) {
				System.out.println("ust");
				windowKonum = "ust";
			}

			// buna göre bounds adlý dikdörtgeni güncelle
			if (windowKonum.equals("sag alt"))// sol
			{	
				System.out.println(this.bounds.width);
				System.out.println(arg0.getX()+"  -  "+ arg0.getY());
				this.bounds.width += arg0.getX()-this.bounds.width;
				this.bounds.height += arg0.getY();
			}
			if (windowKonum.equals("sag ust"))// sol
			{							
				this.bounds.width += arg0.getX();
				this.bounds.y += arg0.getY();
				this.bounds.height -= arg0.getY();
			}
			if (windowKonum.equals("sol ust"))// sol
			{							
				this.bounds.x += arg0.getX();
				this.bounds.width -= arg0.getX();
				this.bounds.y += arg0.getY();
				this.bounds.height -= arg0.getY();
			}			
			if (windowKonum.equals("sol alt"))// sol
			{					
				this.bounds.x += arg0.getX();
				this.bounds.width -= arg0.getX();
				this.bounds.height += arg0.getY();
			}
			if (windowKonum.equals("sol"))// sol
			{
				System.out.println("sol");
				this.bounds.x += arg0.getX();
				this.bounds.width -= arg0.getX();
			}
			if (windowKonum.equals("sag")) {
				System.out.println("sag");
				this.bounds.width += arg0.getX();

			}
			if (windowKonum.equals("alt")) {
				System.out.println("alt");
				this.bounds.height += arg0.getY();

			}
			if (windowKonum.equals("ust")) {
				System.out.println("ust");
				this.bounds.y += arg0.getY();
				this.bounds.height -= arg0.getY();

			}
		}
		// bounds adlý dikdörtgene göre çizim yap
		draw();

	}

	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
