package com.ezardlabs.lostsectormapeditor.gui;

import com.ezardlabs.lostsectormapeditor.map.Layer;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class LayerPanel extends Panel {
	private DragDropList dragDropList;

	LayerPanel() {
		setLayout(new BorderLayout());

		JLabel title = new JLabel("Layers");
		title.setFont(new Font(title.getName(), Font.BOLD, 20));
		add(title, BorderLayout.PAGE_START);

		add(dragDropList = new DragDropList(), BorderLayout.CENTER);

		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

		JButton addLayer = new JButton("Add new layer");
		addLayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Layer.layers.addElement(new Layer(JOptionPane.showInputDialog("Layer name"), 0, 0));
			}
		});
		JButton deleteLayer = new JButton("Delete layer");
		deleteLayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Layer.layers.remove(dragDropList.getSelectedIndex());
			}
		});
		buttons.add(addLayer, BorderLayout.LINE_START);
		buttons.add(deleteLayer, BorderLayout.LINE_END);

		add(buttons, BorderLayout.PAGE_END);
	}
}
