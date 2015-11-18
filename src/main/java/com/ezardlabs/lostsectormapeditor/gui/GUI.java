package com.ezardlabs.lostsectormapeditor.gui;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class GUI extends JFrame {
	private MapPanel mapPanel;
	private LayerPanel layerPanel;
	private JTabbedPane tabPanel;

	public GUI() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		setJMenuBar(new MenuBar());

		JSplitPane sideBar = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabPanel = new JTabbedPane(), layerPanel = new LayerPanel());
		JSplitPane main = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mapPanel = new MapPanel(), sideBar);
		add(main);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		sideBar.setDividerLocation((int) (0.5 * getHeight()));
		main.setDividerLocation((int) (0.8 * getWidth()));
	}
}