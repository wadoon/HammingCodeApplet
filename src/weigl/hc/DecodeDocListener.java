package weigl.hc;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DecodeDocListener implements DocumentListener, UpdateListener {
	static final Border OK_BORDER = BorderFactory.createLineBorder(Color.GREEN,
			1);
	static final Border ERR_BORDER = BorderFactory.createLineBorder(Color.RED,
			1);

	private JLabel lblCode;
	private JLabel lblError;
	private JTextField txt;
	private HammingApplet hApplet;
	private JLabel lblVal;

	public DecodeDocListener(HammingApplet ha, JTextField txt,
			JLabel lblCode, JLabel lblVal, JLabel lblError) {
		this.hApplet = ha;
		this.lblCode = lblCode;
		this.lblError = lblError;
		this.lblVal = lblVal;
		this.txt = txt;
		ha.registerForUpdate(this);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		update();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		update();

	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		update();
	}

	@Override
	public void update() {
		try {
			String text = txt.getText();
			int i = Integer.parseInt(text,2);
			try {
				int c = hApplet.getHammingCode().fromHammingCode(i);

				lblCode.setText(Integer.toBinaryString(c));
				lblVal.setText(String.valueOf(c));
				lblError.setText("");
			} catch (HammingCode.HammingCorrectionException e) {
				lblError.setText("error at: " + e.getPosition());
				lblVal.setText(String.valueOf(e.getCorrectedValue()));
				lblCode.setText(Integer.toBinaryString(e.getCorrectedValue()));
			}
			txt.setBorder(OK_BORDER);
		} catch (Exception e) {
			txt.setBorder(ERR_BORDER);
			e.printStackTrace();
		}
	}
}
