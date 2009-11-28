package net.miginfocom.examples;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mikael Grev, MiG InfoCom AB
 *         Date: Dec 15, 2008
 *         Time: 7:04:50 PM
 */
public class BugTestApp
{
	private static JPanel createPanel()
	{
		JFrame frame = new JFrame();

		JPanel panel =new JPanel(new MigLayout("gap 15, ins 10 0 0 0, flowy, debug",
		"[][fill, sg][fill, sg][grow]", "[fill, sg 2][fill, sg 2][fill, sg 2][fill, sg 2][grow][fill]"));

		JPanel infoPanel = new JPanel(new MigLayout("gap 15, flowy, debug"));
		infoPanel.add(new JLabel("lable 1"));
		JPanel picturePanel = new JPanel();
		picturePanel.setMinimumSize(new Dimension(100,200));
		infoPanel.add(picturePanel);
		infoPanel.add(new JLabel("lable 2"));

		panel.add(infoPanel, "spany 4");
		panel.add(new JButton("button 1"), "newline");
		panel.add(new JButton("button 2"));
		panel.add(new JButton("button 3"));
		panel.add(new JButton("button 4"), "wrap");
		panel.add(new JButton("button 5"));
		panel.add(new JButton("button 6"));
		panel.add(new JButton("button 7"));
		panel.add(new JButton("button 8"));

		frame.setContentPane(panel);

		frame.setSize(600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		return null;
	}

	private static JPanel createPanel2()
	{
		JFrame tst = new JFrame();
		tst.setLayout(new MigLayout("debug, fillx","",""));

		tst.add(new JTextField(),"span 2, grow, wrap");
		tst.add(new JTextField(10));
		tst.add(new JLabel("End"));

		tst.setSize(600, 400);
		tst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tst.setVisible(true);
		return null;
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				createPanel();
//				JFrame frame = new JFrame("Bug Test App");
//				frame.getContentPane().add(createPanel2());
//				frame.pack();
//				frame.setLocationRelativeTo(null);
//				frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//				frame.setVisible(true);
			}
		});
	}
}
