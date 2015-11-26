package com.ezardlabs.lostsectormapeditor.gui;

import com.ezardlabs.lostsectormapeditor.Main;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;

public class GUI extends JFrame {
	static {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final MapPanel mapPanel = new MapPanel();
	private static final LayerPanel layerPanel = new LayerPanel();
	private static final JTabbedPane tabPanel = new JTabbedPane();

	public GUI() {
		setJMenuBar(new MenuBar());

		JSplitPane sideBar = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabPanel, layerPanel);
		JSplitPane projectMap = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new ProjectPanel(), new MapPanel());
		JSplitPane main = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, projectMap, sideBar);
		add(main);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		projectMap.setOneTouchExpandable(true);
		main.setOneTouchExpandable(true);
		sideBar.setDividerLocation((int) (0.5 * getHeight()));
		projectMap.setDividerLocation((int) (0.15 * getWidth()));
		main.setDividerLocation((int) (0.8 * getWidth()));
	}

	public static MapPanel getMapPanel() {
		return mapPanel;
	}

	private void setComponentsEnabled(Container container, boolean enabled) {
		Component[] components = container.getComponents();
		for (Component component : components) {
			component.setEnabled(enabled);
			if (component instanceof Container) {
				setComponentsEnabled((Container) component, enabled);
			}
		}
	}

	public void showNewOrOpenDialog() {
		setComponentsEnabled(getContentPane(), false);

		JTabbedPane tabs = new JTabbedPane();

		tabs.add("Create new project", createNewProjectPanel());

		JDialog dialog = new JDialog(this, "Create new project");
		dialog.setContentPane(tabs);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((screenSize.width - dialog.getWidth()) / 2, (screenSize.height - dialog.getHeight()) / 2);
		dialog.setVisible(true);
	}

	private JPanel createNewProjectPanel() {
		JPanel container = new JPanel();
		SpringLayout layout;
		final JPanel panel = new JPanel(layout = new SpringLayout());
		JLabel nameLabel = new JLabel("Project name:");
		panel.add(nameLabel);

		final JTextField nameField = new JTextField(25);
		panel.add(nameField);

		JLabel locationLabel = new JLabel("Project location:");
		panel.add(locationLabel);
		final JTextField locationField = new JTextField(Preferences.userNodeForPackage(Main.class).get("project_last_location", System.getProperty("user.home")));
		panel.add(locationField);
		JButton locationChooser = new JButton("...");
		panel.add(locationChooser);
		locationChooser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
//				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setFileFilter(new FileFilter() {
					@Override
					public boolean accept(File f) {
						if (f.isDirectory()) {
							File[] files = f.listFiles();
							if (files != null) {
								for (File file : files) {
									if (file.getName().substring(file.getName().lastIndexOf('.')).equals("mep")) {
										return true;
									}
								}
								return false;
							}
						}
						return false;
					}

					@Override
					public String getDescription() {
						return null;
					}
				});
				if (fileChooser.showDialog(panel, null) == JFileChooser.APPROVE_OPTION) {
					locationField.setText(fileChooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		JButton create = new JButton("Create");
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (nameField.getText().length() == 0) {
					JOptionPane.showMessageDialog(panel, "Project name is required", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (locationField.getText().length() == 0) {
					JOptionPane.showMessageDialog(panel, "Project location is required", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!new File(locationField.getText()).mkdirs()) {
					JOptionPane.showMessageDialog(panel, "Invalid project location", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		panel.add(create);

		layout.putConstraint(SpringLayout.NORTH, nameLabel, 15, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.SOUTH, nameLabel, 0, SpringLayout.SOUTH, nameField);
		layout.putConstraint(SpringLayout.WEST, nameLabel, 15, SpringLayout.WEST, panel);

		layout.putConstraint(SpringLayout.NORTH, nameField, 10, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, nameField, 0, SpringLayout.WEST, locationField);

		layout.putConstraint(SpringLayout.NORTH, locationLabel, 0, SpringLayout.NORTH, locationField);
		layout.putConstraint(SpringLayout.SOUTH, locationLabel, 0, SpringLayout.SOUTH, locationField);
		layout.putConstraint(SpringLayout.WEST, locationLabel, 15, SpringLayout.WEST, panel);

		layout.putConstraint(SpringLayout.NORTH, locationField, 10, SpringLayout.SOUTH, nameField);
		layout.putConstraint(SpringLayout.EAST, locationField, 0, SpringLayout.WEST, locationChooser);
		layout.putConstraint(SpringLayout.WEST, locationField, 10, SpringLayout.EAST, locationLabel);

		layout.putConstraint(SpringLayout.NORTH, locationChooser, 0, SpringLayout.NORTH, locationField);
		layout.putConstraint(SpringLayout.EAST, locationChooser, -15, SpringLayout.EAST, panel);

		layout.putConstraint(SpringLayout.NORTH, create, 10, SpringLayout.SOUTH, locationChooser);
		layout.putConstraint(SpringLayout.EAST, create, -15, SpringLayout.EAST, panel);

		layout.putConstraint(SpringLayout.EAST, panel, 15, SpringLayout.EAST, nameField);
		layout.putConstraint(SpringLayout.SOUTH, panel, 15, SpringLayout.SOUTH, create);

		container.add(panel);
		return container;
	}
}