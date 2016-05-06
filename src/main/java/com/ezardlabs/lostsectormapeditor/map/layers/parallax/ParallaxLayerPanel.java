package com.ezardlabs.lostsectormapeditor.map.layers.parallax;

import com.ezardlabs.lostsectormapeditor.gui.FloatField;
import com.ezardlabs.lostsectormapeditor.gui.GUI;
import com.ezardlabs.lostsectormapeditor.gui.IntegerField;
import com.ezardlabs.lostsectormapeditor.map.layers.AbstractLayerPanel;
import com.ezardlabs.lostsectormapeditor.map.layers.LayerList;
import com.ezardlabs.lostsectormapeditor.map.layers.LayerList.LayerListComponentCreator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

class ParallaxLayerPanel extends AbstractLayerPanel<ParallaxLayer> {


	@Override
	protected ListModel<ParallaxLayer> getListModel() {
		return ParallaxLayerManager.getLayers();
	}

	@Override
	protected LayerListComponentCreator<ParallaxLayer> getComponentCreator() {
		return new LayerListComponentCreator<ParallaxLayer>() {
			@Override
			public JComponent getLayerListComponent(LayerList<ParallaxLayer> list, final ParallaxLayer layer, int index, boolean isSelected) {
				if (isSelected) {
					SpringLayout layout = new SpringLayout();
					final JPanel panel = new JPanel(layout);

					JLabel nameLabel = new JLabel("Name:");
					final JTextField nameField = new JTextField(layer.getName(), 25);
					JLabel imageLabel = new JLabel("Image:");
					final JTextField imageLocationField = new JTextField(layer.getImageLocation());
					imageLocationField.setEnabled(false);
					JButton imageChooser = new JButton("...");
					JLabel yLabel = new JLabel("y position (px):");
					final IntegerField yField = new IntegerField(layer.getY());
					JLabel speedLabel = new JLabel("Scroll speed:");
					final FloatField speedField = new FloatField(layer.getSpeed());

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
					imageChooser.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							JFileChooser imageChooser = new JFileChooser(imageLocationField.getText());
							imageChooser.removeChoosableFileFilter(imageChooser.getFileFilter());
							imageChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png"));
							if (imageChooser.showDialog(panel, null) == JFileChooser.APPROVE_OPTION) {
								imageLocationField.setText(imageChooser.getSelectedFile().getAbsolutePath());
							}
						}
					});
					yField.getDocument().addDocumentListener(new DocumentListener() {
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
							layer.setY(yField.getInteger());
						}
					});
					speedField.getDocument().addDocumentListener(new DocumentListener() {
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
							layer.setSpeed(speedField.getFloat());
						}
					});

					nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameField.getPreferredSize().height));
					imageLocationField.setMaximumSize(new Dimension(Integer.MAX_VALUE, imageLocationField.getPreferredSize().height));
					yField.setMaximumSize(new Dimension(Integer.MAX_VALUE, yField.getPreferredSize().height));
					speedField.setMaximumSize(new Dimension(Integer.MAX_VALUE, speedField.getPreferredSize().height));

					panel.add(nameLabel);
					panel.add(nameField);
					panel.add(imageLabel);
					panel.add(imageLocationField);
					panel.add(imageChooser);
					panel.add(yLabel);
					panel.add(yField);
					panel.add(speedLabel);
					panel.add(speedField);

					layout.putConstraint(SpringLayout.NORTH, nameLabel, 10, SpringLayout.NORTH, panel);
					layout.putConstraint(SpringLayout.SOUTH, nameLabel, 0, SpringLayout.SOUTH, nameField);
					layout.putConstraint(SpringLayout.WEST, nameLabel, 10, SpringLayout.WEST, panel);

					layout.putConstraint(SpringLayout.NORTH, nameField, 10, SpringLayout.NORTH, panel);
					layout.putConstraint(SpringLayout.WEST, nameField, 0, SpringLayout.WEST, yField);

					layout.putConstraint(SpringLayout.NORTH, imageLabel, 0, SpringLayout.NORTH, imageLocationField);
					layout.putConstraint(SpringLayout.SOUTH, imageLabel, 0, SpringLayout.SOUTH, imageLocationField);
					layout.putConstraint(SpringLayout.WEST, imageLabel, 10, SpringLayout.WEST, panel);

					layout.putConstraint(SpringLayout.NORTH, imageLocationField, 10, SpringLayout.SOUTH, nameField);
					layout.putConstraint(SpringLayout.EAST, imageLocationField, 0, SpringLayout.WEST, imageChooser);
					layout.putConstraint(SpringLayout.WEST, imageLocationField, 0, SpringLayout.WEST, yField);

					layout.putConstraint(SpringLayout.NORTH, imageChooser, 0, SpringLayout.NORTH, imageLocationField);
					layout.putConstraint(SpringLayout.EAST, imageChooser, -10, SpringLayout.EAST, panel);

					layout.putConstraint(SpringLayout.NORTH, yLabel, 0, SpringLayout.NORTH, yField);
					layout.putConstraint(SpringLayout.SOUTH, yLabel, 0, SpringLayout.SOUTH, yField);
					layout.putConstraint(SpringLayout.WEST, yLabel, 10, SpringLayout.WEST, panel);

					layout.putConstraint(SpringLayout.NORTH, yField, 10, SpringLayout.SOUTH, imageLocationField);
					layout.putConstraint(SpringLayout.EAST, yField, -10, SpringLayout.EAST, panel);
					layout.putConstraint(SpringLayout.WEST, yField, 10, SpringLayout.EAST, yLabel);

					layout.putConstraint(SpringLayout.NORTH, speedLabel, 0, SpringLayout.NORTH, speedField);
					layout.putConstraint(SpringLayout.SOUTH, speedLabel, 0, SpringLayout.SOUTH, speedField);
					layout.putConstraint(SpringLayout.WEST, speedLabel, 10, SpringLayout.WEST, panel);

					layout.putConstraint(SpringLayout.NORTH, speedField, 10, SpringLayout.SOUTH, yField);
					layout.putConstraint(SpringLayout.EAST, speedField, -10, SpringLayout.EAST, panel);
					layout.putConstraint(SpringLayout.WEST, speedField, 0, SpringLayout.WEST, yField);

					layout.putConstraint(SpringLayout.EAST, panel, 10, SpringLayout.EAST, nameField);
					layout.putConstraint(SpringLayout.SOUTH, panel, 5, SpringLayout.SOUTH, speedField);

					panel.setBackground(Color.PINK);
					panel.setOpaque(true);

					return panel;
				} else {
					SpringLayout layout = new SpringLayout();
					JPanel panel = new JPanel(layout);

					JLabel nameLabel = new JLabel(layer.getName());

					panel.add(nameLabel);

					layout.putConstraint(SpringLayout.NORTH, nameLabel, 10, SpringLayout.NORTH, panel);
					layout.putConstraint(SpringLayout.EAST, nameLabel, 10, SpringLayout.EAST, panel);
					layout.putConstraint(SpringLayout.WEST, nameLabel, 10, SpringLayout.WEST, panel);

					layout.putConstraint(SpringLayout.SOUTH, panel, 10, SpringLayout.SOUTH, nameLabel);

					return panel;
				}
			}
		};
	}

	@Override
	protected void onAddLayerClicked() {
		final JDialog dialog = new JDialog(GUI.getInstance(), "Add parallax layer", true);

		SpringLayout layout = new SpringLayout();
		final JPanel panel = new JPanel(layout);

		JLabel nameLabel = new JLabel("Name:");
		final JTextField nameField = new JTextField(25);
		JLabel imageLabel = new JLabel("Image:");
		final JTextField imageLocationField = new JTextField();
		JButton imageChooser = new JButton("...");
		imageChooser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser imageChooser = new JFileChooser(imageLocationField.getText());
				imageChooser.removeChoosableFileFilter(imageChooser.getFileFilter());
				imageChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png"));
				if (imageChooser.showDialog(panel, null) == JFileChooser.APPROVE_OPTION) {
					imageLocationField.setText(imageChooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		JLabel yLabel = new JLabel("Distance from top of map (px):");
		final IntegerField yField = new IntegerField(0);
		JLabel speedLabel = new JLabel("Scroll speed:");
		final FloatField speedField = new FloatField(0);
		JButton positiveButton = new JButton("Add");
		positiveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (nameField.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(dialog, "You must provide a name", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!new File(imageLocationField.getText()).exists()) {
					JOptionPane.showMessageDialog(dialog, "Image file does not exist", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (yField.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(dialog, "You must provide a y-value", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (speedField.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(dialog, "You must provide a scroll speed", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				ParallaxLayerManager.addLayer(new ParallaxLayer(nameField.getText(), imageLocationField.getText(), yField.getInteger(), speedField.getFloat()));
				dialog.dispose();
			}
		});
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});

		panel.add(nameLabel);
		panel.add(nameField);
		panel.add(imageLabel);
		panel.add(imageLocationField);
		panel.add(imageChooser);
		panel.add(yLabel);
		panel.add(yField);
		panel.add(speedLabel);
		panel.add(speedField);
		panel.add(positiveButton);
		panel.add(cancel);

		layout.putConstraint(SpringLayout.NORTH, nameLabel, 15, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.SOUTH, nameLabel, 0, SpringLayout.SOUTH, nameField);
		layout.putConstraint(SpringLayout.WEST, nameLabel, 15, SpringLayout.WEST, panel);

		layout.putConstraint(SpringLayout.NORTH, nameField, 10, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, nameField, 0, SpringLayout.WEST, yField);

		layout.putConstraint(SpringLayout.NORTH, imageLabel, 0, SpringLayout.NORTH, imageLocationField);
		layout.putConstraint(SpringLayout.SOUTH, imageLabel, 0, SpringLayout.SOUTH, imageLocationField);
		layout.putConstraint(SpringLayout.WEST, imageLabel, 15, SpringLayout.WEST, panel);

		layout.putConstraint(SpringLayout.NORTH, imageLocationField, 10, SpringLayout.SOUTH, nameField);
		layout.putConstraint(SpringLayout.EAST, imageLocationField, 0, SpringLayout.WEST, imageChooser);
		layout.putConstraint(SpringLayout.WEST, imageLocationField, 0, SpringLayout.WEST, yField);

		layout.putConstraint(SpringLayout.NORTH, imageChooser, 0, SpringLayout.NORTH, imageLocationField);
		layout.putConstraint(SpringLayout.EAST, imageChooser, -15, SpringLayout.EAST, panel);

		layout.putConstraint(SpringLayout.NORTH, yLabel, 0, SpringLayout.NORTH, yField);
		layout.putConstraint(SpringLayout.SOUTH, yLabel, 0, SpringLayout.SOUTH, yField);
		layout.putConstraint(SpringLayout.WEST, yLabel, 15, SpringLayout.WEST, panel);

		layout.putConstraint(SpringLayout.NORTH, yField, 10, SpringLayout.SOUTH, imageLocationField);
		layout.putConstraint(SpringLayout.EAST, yField, -15, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.WEST, yField, 10, SpringLayout.EAST, yLabel);

		layout.putConstraint(SpringLayout.NORTH, speedLabel, 0, SpringLayout.NORTH, speedField);
		layout.putConstraint(SpringLayout.SOUTH, speedLabel, 0, SpringLayout.SOUTH, speedField);
		layout.putConstraint(SpringLayout.WEST, speedLabel, 15, SpringLayout.WEST, panel);

		layout.putConstraint(SpringLayout.NORTH, speedField, 10, SpringLayout.SOUTH, yField);
		layout.putConstraint(SpringLayout.EAST, speedField, -15, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.WEST, speedField, 0, SpringLayout.WEST, yField);

		layout.putConstraint(SpringLayout.NORTH, positiveButton, 10, SpringLayout.SOUTH, speedField);
		layout.putConstraint(SpringLayout.EAST, positiveButton, -15, SpringLayout.EAST, panel);

		layout.putConstraint(SpringLayout.NORTH, cancel, 0, SpringLayout.NORTH, positiveButton);
		layout.putConstraint(SpringLayout.EAST, cancel, -10, SpringLayout.WEST, positiveButton);

		layout.putConstraint(SpringLayout.EAST, panel, 15, SpringLayout.EAST, nameField);
		layout.putConstraint(SpringLayout.SOUTH, panel, 15, SpringLayout.SOUTH, positiveButton);

		dialog.setContentPane(panel);
		dialog.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((screenSize.width - dialog.getWidth()) / 2, (screenSize.height - dialog.getHeight()) / 2);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}

	@Override
	protected void onDeleteLayerClicked(int index) {
		ParallaxLayerManager.removeLayer(index);
	}
}
