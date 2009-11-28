package weigl.hc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

public final class HammingApplet extends JApplet implements ChangeListener {
	private static final long serialVersionUID = -4673434716918779954L;
	private HammingCode hc;
	private List<UpdateListener> updateList = new LinkedList<UpdateListener>();
	private JPanel structurePanel = new JPanel();

	public HammingApplet() {
		
	}

	public void init() {
		setBackground(Color.WHITE);
		MigLayout layout = new MigLayout(new LC().fill());
		setLayout(layout);

		JSpinner spin = new JSpinner(new SpinnerNumberModel(4, 0, 100, 1));

		spin.addChangeListener(this);

		hc = new HammingCode(4);

		add(label("Data bits:", spin));
		add(spin, new CC().spanX().wrap().alignX("right"));
		addHeader("Structure");
		add(updateStructurePanel(), new CC().spanX().alignX("center").alignY(
				"top").wrap());
		addHeader("Encode");
		add(createEncodePanel(), new CC().spanX().grow().alignX("center")
				.alignY("top").spanX());
		addHeader("Decode");
		add(createDecodePanel(), new CC().spanX().grow().alignX("center")
				.alignY("top").spanX());
	}

	private Component createDecodePanel() {
		JPanel p = new JPanel(new MigLayout(new LC().fillX()));
		

		p.add(new JLabel("<html>HammingCode<sub>2</sub></html>"), new CC().growX());
		p.add(createArrowLabel());
		p.add(new JLabel("<html>Number<sub>2</sub></html>"));
		p.add(new JLabel("<html>Number<sub>10</sub></html>"));
		p.add(new JLabel("<html>Error <sub>&nbsp;</sub></html>"),new CC().wrap());
		
		createDecodeLine(p);
		createDecodeLine(p);
		createDecodeLine(p);
		return p;
	}

	private void createDecodeLine(JPanel p) {
		final JLabel lblCode = new JLabel("", JLabel.RIGHT);
		final JLabel lblError = new JLabel();
		final JLabel lblValue = new JLabel("", JLabel.RIGHT);
		final JTextField txt = new JTextField();
		txt.setAlignmentX(JTextField.RIGHT_ALIGNMENT);
		txt.getDocument().addDocumentListener(
				new DecodeDocListener(this, txt, lblCode, lblValue, lblError));

		p.add(txt, new CC().growX());
		p.add(createArrowLabel());
		p.add(lblCode);
		p.add(lblValue);
		p.add(lblError, new CC().wrap());
	}

	private Component createEncodePanel() {
		JPanel p = new JPanel(new MigLayout(new LC().fillX()));
		
		p.add(new JLabel("<html>Number<sub>10</sub></html>"), new CC().growX());
		p.add(new JLabel("<html>Number<sub>2</sub></html>"));
		p.add(createArrowLabel());
		p.add(new JLabel("<html>HammingCode<sub>2</sub></html>"), "wrap");
		
		createEncodeLine(p);
		createEncodeLine(p);
		createEncodeLine(p);
		return p;
	}

	private void createEncodeLine(JPanel p) {
		final JLabel lblHamming = new JLabel("", JLabel.RIGHT);
		final JLabel lblBin = new JLabel("", JLabel.RIGHT);
		final JTextField txt = new JTextField();
		txt.setAlignmentX(JTextField.RIGHT_ALIGNMENT);

		txt.getDocument().addDocumentListener(
				new EncodeDocListener(this, txt, lblBin, lblHamming));

		p.add(txt, new CC().growX());
		p.add(lblBin);
		p.add(createArrowLabel());
		p.add(lblHamming, new CC().wrap());
	}

	private JLabel createArrowLabel() {
		JLabel lblArrow = new JLabel("-->");
		lblArrow.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
		return lblArrow;
	}

	private Component updateStructurePanel() {
		structurePanel.removeAll();

		boolean[] structure = hc.getParityPosition();
		int i = hc.getDatabits(), py = hc.getParities();

		structurePanel.setLayout(new GridLayout(0, structure.length + 1, 5, 5));

		structurePanel.add(createLabelStructure("", false));
		for (int j = 0; j < structure.length; j++) {
			String s = structure[j] ? String.format(
					"<html>%c<sub>%d</sub></html>", 'p', py--) : String.format(
					"<html>%c<sub>%d</sub></html>", 'i', i--);
			structurePanel.add(createLabelStructure(s, true));
		}

		boolean parity[][] = hc.getParityTable();
		for (int j = 0; j < parity.length; j++) {
			structurePanel.add(createLabelStructure(String.format(
					"<html>p<sub>%d</sub></html>", j), true));
			for (int k = 0; k < parity[j].length; k++) {
				String s = parity[j][k] ? "x" : "-";
				structurePanel.add(createLabelStructure(s, false));
			}
		}
		return structurePanel;
	}

	private JLabel createLabelStructure(String s, boolean bold) {
		JLabel lbl = (JLabel) label(s, null);
		int i = bold ? Font.BOLD : Font.PLAIN;
		lbl.setFont(new Font(Font.MONOSPACED, i, 16));
		return lbl;
	}

	private void addHeader(String string) {
		Component l = label(string, null);
		add(l, "gapbottom 1, span, split 2, aligny center");
		add(new JSeparator(), "gapleft rel, growx");
	}

	private static Component label(String string, JComponent spin) {
		JLabel lbl = new JLabel(string);
		lbl.setLabelFor(spin);
		return lbl;
	}

	public HammingCode getHammingCode() {
		return hc;
	}

	public void fireUpdate() {
		for (UpdateListener l : updateList)
			l.update();
	}

	public void registerForUpdate(UpdateListener listener) {
		updateList.add(listener);
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		hc = new HammingCode((Integer) ((JSpinner) arg0.getSource()).getValue());
		updateStructurePanel();
		fireUpdate();
		invalidate();
		repaint();
	}
}
