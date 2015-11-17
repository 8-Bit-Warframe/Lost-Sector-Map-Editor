package com.ezardlabs.lostsectormapeditor.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

class MenuBar extends JMenuBar {

	MenuBar() {
		add(createFileMenu());
	}

	private JMenu createFileMenu() {
		JMenu file = new JMenu("File");
		file.setMnemonic('f');

		JMenuItem newMap = new JMenuItem("New");
		newMap.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK));
		newMap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		JMenuItem openMap = new JMenuItem("Open...");
		openMap.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));
		openMap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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

		file.add(newMap);
		file.add(openMap);
		file.add(openRecentMap);
		file.addSeparator();
		file.add(saveMap);
		file.add(saveMapAs);
		return file;
	}
}
