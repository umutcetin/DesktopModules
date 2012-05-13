
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

//import com.argeloji.gui.StatusBar;
import javax.swing.*;

public class ServerGUI1 extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGUI1 frame = new ServerGUI1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ServerGUI1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//StatusBar sb = new StatusBar();
		//getContentPane().add(sb, java.awt.BorderLayout.SOUTH);
		
		JButton fancyButton;
		
		Icon bug1 = new ImageIcon( "D:\\1-Movies-icon.png" );
	    Icon bug2 = new ImageIcon( "D:\\2-Movies-icon.png" );
	    fancyButton = new JButton( bug1 );
	    fancyButton.setRolloverIcon( bug2 );
	    
	    Container c = getContentPane();
	    c.setLayout( new FlowLayout() );
	      
	    c.add( fancyButton );
	    
	}

}
