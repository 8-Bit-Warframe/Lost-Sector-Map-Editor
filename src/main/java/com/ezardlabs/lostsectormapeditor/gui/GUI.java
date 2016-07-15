package com.ezardlabs.lostsectormapeditor.gui;

import com.ezardlabs.lostsectormapeditor.Main;
import com.ezardlabs.lostsectormapeditor.PreferenceManager;
import com.ezardlabs.lostsectormapeditor.map.MapManager;
import com.ezardlabs.lostsectormapeditor.map.layers.main.LayerManager;
import com.ezardlabs.lostsectormapeditor.map.layers.parallax.ParallaxLayerManager;
import com.ezardlabs.lostsectormapeditor.project.ProjectManager;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
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
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;

public class GUI extends JFrame {
	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static GUI gui;
	private static final JTabbedPane tabPanel = new JTabbedPane();
	private JDialog dialog;

	private GUI() {
		setTitle("Lost Sector Map Editor");

		setJMenuBar(new MenuBar());

		JTabbedPane layersParallaxTabs = new JTabbedPane();
		layersParallaxTabs.addTab("Layers", LayerManager.getLayerPanel());
		layersParallaxTabs.addTab("Parallax", ParallaxLayerManager.getLayerPanel());

		JSplitPane sideBar = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabPanel, layersParallaxTabs);
		JSplitPane projectMap = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, ProjectManager.getProjectPanel(), MapManager.getMapPanel());
		JSplitPane main = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, projectMap, sideBar);
		add(main);

		try {
			setIconImage(ImageIO.read(getClass().getResourceAsStream("/logo.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		sideBar.setDividerLocation((int) (0.5 * getHeight()));
		projectMap.setDividerLocation((int) (0.15 * getWidth()));
		main.setDividerLocation((int) (0.8 * getWidth()));

		pack();
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		projectMap.setOneTouchExpandable(true);
		main.setOneTouchExpandable(true);
		sideBar.setDividerLocation((int) (0.5 * getHeight()));
		projectMap.setDividerLocation((int) (0.15 * getWidth()));
		main.setDividerLocation((int) (0.8 * getWidth()));
	}

	public static GUI getInstance() {
		if (gui == null) gui = new GUI();
		return gui;
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
		tabs.add("Open project", createOpenProjectPanel());

		dialog = new JDialog(this, "Create new project");
		dialog.setContentPane(tabs);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((screenSize.width - dialog.getWidth()) / 2, (screenSize.height - dialog.getHeight()) / 2);
		dialog.setVisible(true);
	}

	private void closeDialog() {
		dialog.dispose();
		setComponentsEnabled(getContentPane(), true);
	}

	public void showNewProjectDialog() {
		setComponentsEnabled(getContentPane(), false);
		dialog = new JDialog(this, "Create new project");
		dialog.setContentPane(createNewProjectPanel());
		dialog.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((screenSize.width - dialog.getWidth()) / 2, (screenSize.height - dialog.getHeight()) / 2);
		dialog.setVisible(true);
	}

	public void showOpenProjectDialog() {
		setComponentsEnabled(getContentPane(), false);
		dialog = new JDialog(this, "Open project");
		dialog.setContentPane(createOpenProjectPanel());
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
		final JTextField locationField = new JTextField(PreferenceManager.getString(PreferenceManager.PROJECT_LAST_LOCATION, System.getProperty("user.home")));
		panel.add(locationField);
		final JButton locationChooser = new JButton("...");
		panel.add(locationChooser);
		locationChooser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(locationField.getText());
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
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
				File f = new File(locationField.getText() + File.separator + nameField.getText());
				if (!f.mkdirs()) {
					JOptionPane.showMessageDialog(panel, "Invalid project location", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					ProjectManager.createNewProject(f, nameField.getText());
					closeDialog();
				} catch (IOException ignored) {
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

	private JPanel createOpenProjectPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		final JFileChooser fileChooser = new JFileChooser();
		final FileFilter fileFilter = new FileFilter() {
			@Override
			public boolean accept(File f) {
				return f.isDirectory() && new File(f.getAbsolutePath() + File.separator + ".demp").exists();
			}

			@Override
			public String getDescription() {
				return "Dethsquare Engine Map Project";
			}
		};
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setFileView(new FileView() {

			@Override
			public String getTypeDescription(File f) {
				if (fileFilter.accept(f)) {
					return "Dethsquare Engine Map Project";
				} else {
					return super.getTypeDescription(f);
				}
			}

			@Override
			public Icon getIcon(File f) {
				if (fileFilter.accept(f)) {
					return new ImageIcon(Main.class.getResource("/logo_icon.png"));
				} else {
					return super.getIcon(f);
				}
			}
		});
		fileChooser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("ApproveSelection")) {
					if (fileFilter.accept(fileChooser.getSelectedFile())) {
						ProjectManager.openExistingProject(fileChooser.getSelectedFile());
						closeDialog();
					} else {
						fileChooser.setCurrentDirectory(fileChooser.getSelectedFile());
					}
				}
			}
		});
		panel.add(fileChooser, BorderLayout.CENTER);
		panel.invalidate();
		return panel;
	}

	public void showNewMapDialog() {
		JPanel p = new JPanel();
		p.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.2;
		p.add(new JLabel("Name:"), c);
		c.gridy = 1;
		p.add(new JLabel("Width:"), c);
		c.gridy = 2;
		p.add(new JLabel("Height:"), c);
		c.weightx = 0.8;
		c.gridx = 1;
		c.gridy = 0;
		final JTextField tf = new JTextField();
		p.add(tf, c);
		c.gridy = 1;
		IntegerField if1 = new IntegerField();
		p.add(if1, c);
		c.gridy = 2;
		IntegerField if2 = new IntegerField();
		p.add(if2, c);
		if1.addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorAdded(AncestorEvent event) {
				tf.requestFocusInWindow();
			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
			}
		});
		if (JOptionPane.showConfirmDialog(null, p, "Enter map size:", JOptionPane.OK_CANCEL_OPTION) == 0) {
			ProjectManager.refresh();
			MapManager.createNewMap(tf.getText(), if1.getInteger(), if2.getInteger());
		}
	}
}