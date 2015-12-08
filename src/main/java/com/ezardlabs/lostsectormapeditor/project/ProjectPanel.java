package com.ezardlabs.lostsectormapeditor.project;

import com.ezardlabs.lostsectormapeditor.Main;
import com.ezardlabs.lostsectormapeditor.gui.Panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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

class ProjectPanel extends Panel {
	private final JTree tree = new JTree();

	public ProjectPanel() {
		setLayout(new GridLayout(1, 1));
		tree.setModel(new DefaultTreeModel(null));
		add(tree);
		tree.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (tree.hasFocus() && e.getKeyCode() == KeyEvent.VK_DELETE) {
					if (JOptionPane.showOptionDialog(null, "Are you sure you want to delete " + tree.getLastSelectedPathComponent() + "?", "Delete item?", JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE, null, null, null) == 0) {
						if (!new File(tree.getLastSelectedPathComponent().toString()).delete()) {
							JOptionPane.showConfirmDialog(null, "Could not delete file", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
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
				panel.setOpaque(false);
				panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

				if (file.isDirectory()) {
					if (expanded) {
						panel.add(new JLabel(UIManager.getIcon("Tree.openIcon")));
					} else {
						panel.add(new JLabel(UIManager.getIcon("Tree.closedIcon")));
					}
				} else {
					String name = file.getName();
					if (name.length() > 6 && name.substring(name.lastIndexOf('.')).equals(".lsmap")) {
						//noinspection ConstantConditions
						panel.add(new JLabel(new ImageIcon(Main.class.getClassLoader().getResource("map_icon.png"))));
					} else {
						panel.add(new JLabel(UIManager.getIcon("Tree.leafIcon")));
					}
				}
				panel.add(Box.createHorizontalStrut(5));
				String text = file.getName();
				if (text.contains(".")) {
					text = text.substring(0, text.lastIndexOf('.'));
				}
				JLabel label = new JLabel(text);
				if (selected) {
					label.setForeground(Color.WHITE);
				}
				panel.add(label);
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
						if (e.getClickCount() == 1 && selPath != null && new File(selPath.toString().replaceAll("\\[|\\]", "")).isDirectory()) {
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
											if (!new File(ProjectManager.getCurrentProject().getDirectory() + File.separator + textField.getText() + ".lsmap").createNewFile()) {
												JOptionPane.showConfirmDialog(null, "Failed to create file", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null);
											}
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
