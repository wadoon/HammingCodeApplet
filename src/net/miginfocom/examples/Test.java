package net.miginfocom.examples;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mikael Grev, MiG InfoCom AB
 *         Date: Jun 8, 2009
 *         Time: 10:54:56 PM
 */
public class Test
{
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				JFrame frame = new JFrame("Nested Panel Test");
				JPanel panel, rootPanel = createPanel(), newPanel = rootPanel;

				for (int i = 0; i < 15; i++) { // add nested panels
					panel = newPanel;
					newPanel = createPanel();
					panel.add(newPanel);
				}

				frame.getContentPane().add(rootPanel);
				long n = System.nanoTime();
				frame.pack();
				System.out.println("Timed: " + ((System.nanoTime() - n) / 1000000f));
				frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}

	private static JPanel createPanel()
	{
		JPanel panel = new JPanel(new MigLayout())
		{
			private Dimension prevDim;

			public Dimension getPreferredSize()
			{
				if (++ii % 10000 == 0)
					System.out.println(ii);
//				if( prevDim == null ) // uncomment for SPEEDUP!
				prevDim = super.getPreferredSize();
				return prevDim;
			}

			public void invalidate()
			{
				prevDim = null;
				super.invalidate();
			}
		};

		panel.add(new JLabel("Test"));
		panel.add(new JTextField(5));

		return panel;
	}

	static int ii = 0;

//	private static JPanel createPanel()
//	{
//		JPanel panel = new JPanel(new GridBagLayout())
//		{
//			private Dimension prevDim;
//
//			public Dimension getPreferredSize()
//			{
//				System.out.println(++ii);
////				if( prevDim == null ) // uncomment for SPEEDUP!
//				prevDim = super.getPreferredSize();
//				return prevDim;
//			}
//
//			public void invalidate()
//			{
//				prevDim = null;
//				super.invalidate();
//			}
//		};
//
//		panel.add(new JLabel("Test"), new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
//		panel.add(new JTextField(5), new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
//
//		return panel;
//	}
}
