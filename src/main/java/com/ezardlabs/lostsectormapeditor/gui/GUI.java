package com.ezardlabs.lostsectormapeditor.gui;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

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
		JSplitPane main = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mapPanel, sideBar);
		add(main);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		sideBar.setDividerLocation((int) (0.5 * getHeight()));
		main.setDividerLocation((int) (0.8 * getWidth()));
	}

	public static MapPanel getMapPanel() {
		return mapPanel;
	}
}