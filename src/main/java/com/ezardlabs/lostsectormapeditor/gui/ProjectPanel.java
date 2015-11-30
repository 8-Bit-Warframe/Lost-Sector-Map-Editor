package com.ezardlabs.lostsectormapeditor.gui;

import com.ezardlabs.lostsectormapeditor.project.Project;

import java.awt.Component;
import java.awt.GridLayout;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;

public class ProjectPanel extends JPanel {
	private JTree tree = new JTree();

	public ProjectPanel() {
		setLayout(new GridLayout(1, 1));
		tree.setModel(new DefaultTreeModel(null));
		add(tree);
	}


	public void setProject(Project project) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(project.getDirectory(), true);
		addFilesToModel(project.getDirectory(), root);
		tree.setModel(new DefaultTreeModel(root));
		tree.setCellRenderer(new TreeCellRenderer() {
			@Override
			public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
				File file = (File) ((DefaultMutableTreeNode) value).getUserObject();
				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

				if (file.isDirectory()) {
					if (expanded) {
						panel.add(new JLabel(UIManager.getIcon("Tree.openIcon")));
					} else {
						panel.add(new JLabel(UIManager.getIcon("Tree.closedIcon")));
					}
				} else {
					panel.add(new JLabel(UIManager.getIcon("Tree.leafIcon")));
				}
				panel.add(Box.createHorizontalStrut(5));
				panel.add(new JLabel(file.getName()));
				return panel;
			}
		});
	}

	private void addFilesToModel(File directory, DefaultMutableTreeNode node) {
		File[] files = directory.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return !name.endsWith(".demp");
			}
		});
		if (files != null) {
			for (File f : files) {
				DefaultMutableTreeNode n = new DefaultMutableTreeNode(f);
				node.add(n);
				if (f.isDirectory()) {
					addFilesToModel(f, n);
				}
			}
		}
	}
}
