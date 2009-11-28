package net.miginfocom.examples;

/**
 * @author Mikael Grev, MiG InfoCom AB
 *         Date: Jun 26, 2009
 *         Time: 11:57:30 AM
 */

import java.awt.*;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;



public class MigSliderTest {

   public MigSliderTest(){
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//      JPanel panel = getTestPanel1();
//      JPanel panel = getTestPanel2();
      JPanel panel = getTestPanel3();

      Container container = frame.getContentPane();
      container.setLayout(new BorderLayout());
      container.add(panel, BorderLayout.CENTER);

      frame.setSize(400, 200);
      frame.setVisible(true);
   }

   private JPanel getTestPanel1(){
      JPanel panel = new JPanel(new MigLayout("w 200","[][grow]",""));

      panel.add(new JLabel("Label:"));
      panel.add(new JSlider(), "span, grow, wrap");

      return panel;
   }

   private JPanel getTestPanel2(){
      JPanel panel = new JPanel(new MigLayout("w 200, debug","[][grow][]","[][][][][][][][]"));

      panel.add(new JSlider());
      panel.add(new JSlider(), "span, grow, wrap");

      return panel;
   }

   private JPanel getTestPanel3(){
      JPanel panel = new JPanel(new BorderLayout());

      JPanel subPanel = new JPanel(new BorderLayout());
      subPanel.add(new JLabel("Label:"), BorderLayout.WEST);
      subPanel.add(new JSlider(), BorderLayout.CENTER);

      panel.add(subPanel, BorderLayout.NORTH);

      return panel;
   }

   public static void main(String[] args)
   {

	   try {
		   UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
	   } catch (ClassNotFoundException e) {
		   e.printStackTrace();
	   } catch (InstantiationException e) {
		   e.printStackTrace();
	   } catch (IllegalAccessException e) {
		   e.printStackTrace();
	   } catch (UnsupportedLookAndFeelException e) {
		   e.printStackTrace();
	   }

	   // TODO Auto-generated method stub
      new MigSliderTest();
   }
}