package com.ezardlabs.lostsectormapeditor.gui;

import com.ezardlabs.lostsectormapeditor.project.Project;
import com.ezardlabs.lostsectormapeditor.project.ProjectManager;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

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
		tree.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					int selRow = tree.getRowForLocation(e.getX(), e.getY());
					TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
					if (selRow != -1) {
						File f;
						if (e.getClickCount() == 1 && selPath != null && (f = new File(selPath.toString().replaceAll("\\[|\\]", ""))).isDirectory()) {
							JPopupMenu popup = new JPopupMenu();
							JMenuItem newMapFile = new JMenuItem("Create new map file...");
							newMapFile.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									final JPanel panel = new JPanel();
									panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

									JLabel label = new JLabel("Enter a new file name:");
									panel.add(label);

									panel.add(Box.createVerticalStrut(5));

									JTextField textField = new JTextField();
									panel.add(textField);

									if (JOptionPane
											.showOptionDialog(null, panel, "New map file", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"OK", "Cancel"}, null) == 0) {
										try {
											new File(ProjectManager.getCurrentProject().getDirectory() + File.separator + textField.getText() + ".lsmap").createNewFile();
										} catch (IOException ignored) {
										}
									}
								}
							});
							popup.add(newMapFile);
							popup.show(tree, e.getX(), e.getY());
						}
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

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
