package com.ezardlabs.lostsectormapeditor.gui;

import com.ezardlabs.lostsectormapeditor.map.MapManager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

class MenuBar extends JMenuBar {

	MenuBar() {
		add(createFileMenu());
	}

	private JMenu createFileMenu() {
		JMenu file = new JMenu("File");
		file.setMnemonic('f');

		JMenu newMenu = new JMenu("New");
		JMenuItem newProject = new JMenuItem("Project...");
		newProject.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK));
		newProject.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GUI.getInstance().showNewProjectDialog();
			}
		});
		JMenuItem newMap = new JMenuItem("Map");
		newMap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel p = new JPanel();
				p.setLayout(new GridBagLayout());
				GridBagConstraints c = new GridBagConstraints();
				c.gridx = 0;
				c.gridy = 0;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.weightx = 0.2;
				p.add(new JLabel("Width:"), c);
				c.gridy = 1;
				p.add(new JLabel("Height:"), c);
				c.weightx = 0.8;
				c.gridx = 1;
				c.gridy = 0;
				final IntegerField if1 = new IntegerField();
				p.add(if1, c);
				c.gridy = 1;
				IntegerField if2 = new IntegerField();
				p.add(if2, c);
				if1.addAncestorListener(new AncestorListener() {
					@Override
					public void ancestorAdded(AncestorEvent event) {
						if1.requestFocusInWindow();
					}

					@Override
					public void ancestorRemoved(AncestorEvent event) {
					}

					@Override
					public void ancestorMoved(AncestorEvent event) {
					}
				});
				if (JOptionPane.showConfirmDialog(null, p, "Enter map size:", JOptionPane.OK_CANCEL_OPTION) == 0) {
					MapManager.createNewMap(Integer.parseInt(if1.getText()), Integer.parseInt(if2.getText()));
				}
			}
		});
		newMenu.add(newProject);
		newMenu.addSeparator();
		newMenu.add(newMap);

		JMenuItem openMap = new JMenuItem("Open...");
		openMap.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));
		openMap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GUI.getInstance().showOpenProjectDialog();
			}
		});
		JMenu openRecentMap = new JMenu("Open recent");
		JMenuItem saveMap = new JMenuItem("Save");
		saveMap.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK));
		saveMap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		JMenuItem saveMapAs = new JMenuItem("Save as...");
		saveMapAs.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		saveMapAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

		file.add(newMenu);
		file.add(openMap);
		file.add(openRecentMap);
		file.addSeparator();
		file.add(saveMap);
		file.add(saveMapAs);
		return file;
	}
}
