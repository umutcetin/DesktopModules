package com.argeloji.client;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class Stopwatch extends JFrame implements ActionListener, Runnable {
	private long startTime;
	private final static java.text.SimpleDateFormat timerFormat = new java.text.SimpleDateFormat("hh: mm : ss : SSS");
	private final JButton startStopButton = new JButton("Start/stop");
	private Thread updater;
	private boolean isRunning = false;
	private final Runnable displayUpdater = new Runnable() {
		public void run() {
			displayElapsedTime(Stopwatch.this.startTime
					- System.currentTimeMillis());
		}
	};

	public void actionPerformed(ActionEvent ae) {
		if (isRunning) {
			long elapsed = startTime - System.currentTimeMillis();
			isRunning = false;
			try {
				updater.join();
				// Wait for updater to finish
			} catch (InterruptedException ie) {
			}
			displayElapsedTime(elapsed);
			// Display the end-result
		} else {
			//bu satýr burada olursa pause olduktan sonra tekrar calisinca 2 dk ya uzatýyor
			//startTime = 2 * 60 * 1000 + System.currentTimeMillis();
			startTime +=10000;
			isRunning = true;
			updater = new Thread(this);
			updater.start();
		}
	}

	private void displayElapsedTime(long elapsedTime) {
		startStopButton.setText(timerFormat.format(new java.util.Date(
				elapsedTime)));
	}

	public void run() {
		try {
			while (isRunning) {
				SwingUtilities.invokeAndWait(displayUpdater);
				Thread.sleep(50);
			}
		} catch (java.lang.reflect.InvocationTargetException ite) {
			ite.printStackTrace(System.err);
			// Should never happen!
		} catch (InterruptedException ie) {
		}
		// Ignore and return!
	}

	public Stopwatch() {
		startTime = 2 * 60 * 1000 + System.currentTimeMillis();//bu satýr burada olursa pause ediyor
		startStopButton.addActionListener(this);
		getContentPane().add(startStopButton);
		setSize(500, 500);
		setVisible(true);
	}

	public static void main(String[] arg) {
		new Stopwatch().addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}
