package weigl.hc;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class EncodeDocListener implements DocumentListener, UpdateListener {

	private JLabel lblHamming;
	private JLabel lblBin;
	private JTextField txt;
	private HammingApplet hApplet;

	public EncodeDocListener(HammingApplet ha, JTextField txt, JLabel lblBin,
			JLabel lblHamming) {
		this.hApplet = ha;
		this.lblHamming = lblHamming;
		this.lblBin = lblBin;
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
			int i = Integer.parseInt(text);
			lblBin.setText(Integer.toBinaryString(i));
			lblHamming.setText(hApplet.getHammingCode().format(
					hApplet.getHammingCode().toHammingCode(i)));
			txt.setBorder(DecodeDocListener.OK_BORDER);
		} catch (Exception e) {
			e.printStackTrace();
			txt.setBorder(DecodeDocListener.ERR_BORDER);
		}
	}
}
