package com.ezardlabs.lostsectormapeditor.gui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class LayerPanel extends JPanel {

	LayerPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(new DragDropList());
	}
}
