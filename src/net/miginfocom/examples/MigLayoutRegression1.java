package net.miginfocom.examples;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mikael Grev, MiG InfoCom AB
 *         Date: Aug 3, 2009
 *         Time: 6:15:50 PM
 */
public class MigLayoutRegression1 extends JFrame
{

  public MigLayoutRegression1() throws HeadlessException
  {
      Container contentPane = getContentPane();
      contentPane.setLayout(new MigLayout("", "[grow]", "[][]"));
      contentPane.add(new JLabel("This is a label that determines the width of the dialog"), "wrap");
      JTextArea textArea = new JTextArea("This is a text area with several lines of text. This is a text area with several lines of text. This is a text area with several lines of text.");
      textArea.setLineWrap(true);
      textArea.setWrapStyleWord(true);

      contentPane.add(textArea, "grow, push");

      pack();
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
  }

  public static void main(String[] args) {
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
	  new MigLayoutRegression1().setVisible(true);
  }
}