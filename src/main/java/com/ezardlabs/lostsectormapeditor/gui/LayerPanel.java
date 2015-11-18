package com.ezardlabs.lostsectormapeditor.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class LayerPanel extends JPanel {
	private DragDropList<String> dragDropList;

	LayerPanel() {
		setLayout(new BorderLayout());

		JLabel title = new JLabel("Layers");
		title.setFont(new Font(title.getName(), Font.BOLD, 20));
		add(title, BorderLayout.PAGE_START);

		add(dragDropList = new DragDropList<String>(), BorderLayout.CENTER);

		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

		JButton addLayer = new JButton("Add new layer");
		addLayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dragDropList.addDatum(JOptionPane.showInputDialog("Layer name"));
			}
		});
		JButton deleteLayer = new JButton("Delete layer");
		deleteLayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dragDropList.removeSelected();
			}
		});
		buttons.add(addLayer, BorderLayout.LINE_START);
		buttons.add(deleteLayer, BorderLayout.LINE_END);

		add(buttons, BorderLayout.PAGE_END);
	}
}
