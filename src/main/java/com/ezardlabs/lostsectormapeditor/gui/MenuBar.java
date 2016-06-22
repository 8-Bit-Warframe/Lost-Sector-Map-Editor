package com.ezardlabs.lostsectormapeditor.gui;

import com.ezardlabs.lostsectormapeditor.PreferenceManager;
import com.ezardlabs.lostsectormapeditor.map.MapManager;
import com.ezardlabs.lostsectormapeditor.map.layers.parallax.ParallaxLayerManager;
import com.ezardlabs.lostsectormapeditor.project.ProjectManager;
import com.ezardlabs.lostsectormapeditor.sprites.SpriteManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

class MenuBar extends JMenuBar {

	MenuBar() {
		add(createFileMenu());
		add(createImportMenu());
		add(createTestMenu());
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
				GUI.getInstance().showNewMapDialog();
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
		JMenuItem saveMap = new JMenuItem("Save map");
		saveMap.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));
		saveMap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MapManager.saveMap();
			}
		});
		JMenuItem saveMapAs = new JMenuItem("Save map as...");
		saveMapAs.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		saveMapAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MapManager.saveMap(JOptionPane.showInputDialog(GUI.getInstance(), "File name:", "Save map as...", JOptionPane.QUESTION_MESSAGE));
			}
		});

		JMenuItem saveProject = new JMenuItem("Save project");
		saveProject.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ProjectManager.saveCurrentProject();
			}
		});

		file.add(newMenu);
		file.add(openMap);
		file.add(openRecentMap);
		file.addSeparator();
		file.add(saveMap);
		file.add(saveMapAs);
		file.addSeparator();
		file.add(saveProject);
		return file;
	}

	private JMenu createImportMenu() {
		JMenu menu = new JMenu("Import");
		JMenuItem spritesheet = new JMenuItem("Spritesheet");
		spritesheet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(PreferenceManager.getString(PreferenceManager.IMPORT_LAST_LOCATION, "C:\\")));
				fileChooser.setFileFilter(new FileFilter() {
					@Override
					public boolean accept(File f) {
						return f.isDirectory() || f.getName().endsWith(".png") || f.getName().endsWith(".jpg");
					}

					@Override
					public String getDescription() {
						return "Images";
					}
				});
				if (fileChooser.showDialog(null, "Open") == JFileChooser.APPROVE_OPTION) {
					PreferenceManager.putString(PreferenceManager.IMPORT_LAST_LOCATION, fileChooser.getCurrentDirectory().getAbsolutePath());
					SpriteManager.showSpriteCutterDialog(fileChooser.getSelectedFile());
				}
			}
		});
		menu.add(spritesheet);
		return menu;
	}

	private JMenu createTestMenu() {
		JMenu menu = new JMenu("Test");
		JMenuItem parallax = new JMenuItem("Parallax");
		parallax.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ParallaxLayerManager.showTestWindow();
			}
		});
		menu.add(parallax);
		return menu;
	}
}
