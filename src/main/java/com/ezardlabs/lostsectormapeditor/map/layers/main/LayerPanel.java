package com.ezardlabs.lostsectormapeditor.map.layers.main;

import com.ezardlabs.lostsectormapeditor.map.layers.AbstractLayerPanel;
import com.ezardlabs.lostsectormapeditor.map.layers.LayerList;
import com.ezardlabs.lostsectormapeditor.map.layers.LayerList.LayerListComponentCreator;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class LayerPanel extends AbstractLayerPanel<Layer> {
	@Override
	protected ListModel<Layer> getListModel() {
		return LayerManager.getLayers();
	}

	@Override
	protected LayerListComponentCreator<Layer> getComponentCreator() {
		return new LayerListComponentCreator<Layer>() {
			@Override
			public JComponent getLayerListComponent(LayerList<Layer> list, final Layer layer, int index, boolean isSelected) {
				if (isSelected) {
					SpringLayout layout = new SpringLayout();
					JPanel panel = new JPanel();
					panel.setLayout(layout);

					JLabel nameLabel = new JLabel("Name:");
					final JTextField nameField = new JTextField(layer.getName());
					nameField.getDocument().addDocumentListener(new DocumentListener() {
						@Override
						public void insertUpdate(DocumentEvent e) {
							updateText();
						}

						@Override
						public void removeUpdate(DocumentEvent e) {
							updateText();
						}

						@Override
						public void changedUpdate(DocumentEvent e) {
							updateText();
						}

						private void updateText() {
							layer.setName(nameField.getText());
						}
					});
					nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameField.getPreferredSize().height));

					panel.add(nameLabel);
					panel.add(nameField);

					layout.putConstraint(SpringLayout.NORTH, nameLabel, 5, SpringLayout.NORTH, panel);
					layout.putConstraint(SpringLayout.SOUTH, nameLabel, 0, SpringLayout.SOUTH, nameField);
					layout.putConstraint(SpringLayout.WEST, nameLabel, 10, SpringLayout.WEST, panel);

					layout.putConstraint(SpringLayout.NORTH, nameField, 5, SpringLayout.NORTH, panel);
					layout.putConstraint(SpringLayout.EAST, nameField, -10, SpringLayout.EAST, panel);
					layout.putConstraint(SpringLayout.WEST, nameField, 10, SpringLayout.EAST, nameLabel);

					layout.putConstraint(SpringLayout.SOUTH, panel, 0, SpringLayout.SOUTH, nameLabel);

					panel.setBackground(Color.PINK);
					panel.setOpaque(true);

					return panel;
				} else {
					SpringLayout layout = new SpringLayout();
					JPanel panel = new JPanel(layout);

					JLabel nameLabel = new JLabel(layer.getName());

					panel.add(nameLabel);

					layout.putConstraint(SpringLayout.NORTH, nameLabel, 5, SpringLayout.NORTH, panel);
					layout.putConstraint(SpringLayout.EAST, nameLabel, 10, SpringLayout.EAST, panel);
					layout.putConstraint(SpringLayout.WEST, nameLabel, 10, SpringLayout.WEST, panel);

					layout.putConstraint(SpringLayout.SOUTH, panel, 5, SpringLayout.SOUTH, nameLabel);

					return panel;
				}
			}
		};
	}

	@Override
	protected void onAddLayerClicked() {
		LayerManager.addLayer(new Layer(JOptionPane.showInputDialog("Layer name")));
	}

	@Override
	protected void onDeleteLayerClicked(int index) {
		LayerManager.removeLayer(index);
	}
}
