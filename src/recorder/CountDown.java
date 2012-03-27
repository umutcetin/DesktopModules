import javax.swing.*;
import java.awt.*;
import java.lang.Thread;

class CountDown {
	JTextField tf;
	JLabel l;
	JFrame fr;

	CountDown() {
		buildGUI();
	}

	public void buildGUI() {
		fr = new JFrame("CountDown Begins");
		JPanel p = new JPanel();
		l = new JLabel("");
		tf = new JTextField(5);
		tf.setEnabled(false);
		Font f = new Font("Algerian", 0, 18);
		tf.setFont(f);
		tf.setBackground(Color.red);
		p.setBackground(Color.black);
		fr.add(p);
		p.add(tf);
		p.add(l);
		fr.setVisible(true);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setSize(100, 100);
		fr.setResizable(false);
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
			for (int i = 10; i >= 0; i--) {
				tf.setText("" + i);
				Thread.sleep(1000);
			}
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(new JFrame(), exc);
		}
	}

	public static void main(String args[]) {
		CountDown cd = new CountDown();
		JOptionPane.showMessageDialog(new JFrame(), "Wait..");
		try {
			Thread.sleep(1001);
		} catch (Exception exc) {
			System.out.println(exc);
		}
		cd.TimerThread();
	}
}
