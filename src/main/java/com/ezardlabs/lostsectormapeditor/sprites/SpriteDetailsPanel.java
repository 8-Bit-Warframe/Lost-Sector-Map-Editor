package com.ezardlabs.lostsectormapeditor.sprites;

import com.ezardlabs.lostsectormapeditor.gui.Panel;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class SpriteDetailsPanel extends Panel {

	public SpriteDetailsPanel() {
		SpringLayout layout = new SpringLayout();
		setLayout(layout);

		JLabel nameLabel = new JLabel("Name:");
		add(nameLabel);
		JTextField nameField = new JTextField();
		add(nameField);

		JLabel indexLabel = new JLabel("Index:");
		add(indexLabel);
		JLabel indexField = new JLabel("0");
		add(indexField);

		JLabel colliderLabel = new JLabel("Collider:");
		add(colliderLabel);
		JCheckBox colliderField = new JCheckBox();
		add(colliderField);

		JButton cutSpriteButton = new JButton("Cut Sprite");
		add(cutSpriteButton);

		// Name label
		layout.putConstraint(SpringLayout.NORTH, nameLabel, 15, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, nameLabel, 15, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, nameLabel, 0, SpringLayout.SOUTH, nameField);

		// Name field
		layout.putConstraint(SpringLayout.NORTH, nameField, 15, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, nameField, 15, SpringLayout.EAST, nameLabel);
		layout.putConstraint(SpringLayout.EAST, nameField, -15, SpringLayout.EAST, this);

		// Index label
		layout.putConstraint(SpringLayout.NORTH, indexLabel, 15, SpringLayout.SOUTH, nameLabel);
		layout.putConstraint(SpringLayout.WEST, indexLabel, 15, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, indexLabel, 0, SpringLayout.SOUTH, indexField);

		// Index field
		layout.putConstraint(SpringLayout.NORTH, indexField, 15, SpringLayout.SOUTH, nameField);
		layout.putConstraint(SpringLayout.WEST, indexField, 15, SpringLayout.EAST, indexLabel);
		layout.putConstraint(SpringLayout.EAST, indexField, -15, SpringLayout.EAST, this);

		// Collider label
		layout.putConstraint(SpringLayout.NORTH, colliderLabel, 15, SpringLayout.SOUTH, indexLabel);
		layout.putConstraint(SpringLayout.WEST, colliderLabel, 15, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, colliderLabel, 0, SpringLayout.SOUTH, colliderField);

		// Collider field
		layout.putConstraint(SpringLayout.NORTH, colliderField, 15, SpringLayout.SOUTH, indexField);
		layout.putConstraint(SpringLayout.EAST, colliderField, -15, SpringLayout.EAST, this);

		// Cut sprite button
		layout.putConstraint(SpringLayout.NORTH, cutSpriteButton, 15, SpringLayout.SOUTH, colliderLabel);
		layout.putConstraint(SpringLayout.EAST, cutSpriteButton, -15, SpringLayout.EAST, this);
	}
}
