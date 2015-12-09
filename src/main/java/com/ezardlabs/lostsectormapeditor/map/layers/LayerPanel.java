package com.ezardlabs.lostsectormapeditor.map.layers;

import com.ezardlabs.lostsectormapeditor.gui.Panel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

public class LayerPanel extends Panel {
	private DragDropList dragDropList;

	LayerPanel() {
		setLayout(new BorderLayout());

		JLabel title = new JLabel("Layers");
		title.setFont(new Font(title.getName(), Font.BOLD, 20));
		add(title, BorderLayout.PAGE_START);

		add(dragDropList = new DragDropList(), BorderLayout.CENTER);

		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

		JButton addLayer = new JButton("Add new layer");
		addLayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Layer.layers.addElement(new Layer(JOptionPane.showInputDialog("Layer name"), 0, 0));
			}
		});
		JButton deleteLayer = new JButton("Delete layer");
		deleteLayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Layer.layers.remove(dragDropList.getSelectedIndex());
			}
		});
		buttons.add(addLayer, BorderLayout.LINE_START);
		buttons.add(deleteLayer, BorderLayout.LINE_END);

		add(buttons, BorderLayout.PAGE_END);
	}

	public static class DragDropList extends JList<Layer> {

		public DragDropList() {
			super(new DefaultListModel<Layer>());
			setModel(Layer.layers);
			setDragEnabled(true);
			setDropMode(DropMode.INSERT);

			setTransferHandler(new ListDropHandler());

			setFont(new Font(getFont().getName(), Font.PLAIN, 15));
			new DragListener(this);
		}

		private class DragListener {
			private DragDropList list;
			private DragSource ds = new DragSource();

			public DragListener(DragDropList list) {
				this.list = list;
				ds.createDefaultDragGestureRecognizer(list, DnDConstants.ACTION_MOVE, new DragGestureListener() {
					@Override
					public void dragGestureRecognized(DragGestureEvent dge) {
						StringSelection transferable = new StringSelection(Integer.toString(DragListener.this.list.getSelectedIndex()));
						ds.startDrag(dge, DragSource.DefaultCopyDrop, transferable, null);
					}
				});

			}
		}

		private class ListDropHandler extends TransferHandler {

			public boolean canImport(TransferSupport support) {
				if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
					return false;
				}
				JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
				return dl.getIndex() != -1;
			}

			public boolean importData(TransferSupport support) {
				if (!canImport(support)) {
					return false;
				}

				Transferable transferable = support.getTransferable();
				String indexString;
				try {
					indexString = (String) transferable.getTransferData(DataFlavor.stringFlavor);
				} catch (Exception e) {
					return false;
				}

				int index = Integer.parseInt(indexString);
				JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
				int dropTargetIndex = dl.getIndex();

				Layer layer = Layer.layers.remove(index);

				if (index == 0 && (dropTargetIndex == 0 || dropTargetIndex == 1)) {
					Layer.layers.add(index, layer);
					return false;
				}

				if (index < dropTargetIndex) {
					dropTargetIndex--;
				}

				Layer.layers.add(dropTargetIndex, layer);

				setModel(Layer.layers);

				setSelectedIndex(dropTargetIndex);
				return true;
			}
		}
	}
}
