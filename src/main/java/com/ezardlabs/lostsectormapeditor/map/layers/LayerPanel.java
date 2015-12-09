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
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

class LayerPanel extends Panel {
	private final DragDropList dragDropList = new DragDropList();

	LayerPanel() {
		setLayout(new BorderLayout());

		JLabel title = new JLabel("Layers");
		title.setFont(new Font(title.getName(), Font.BOLD, 20));
		add(title, BorderLayout.PAGE_START);

		add(dragDropList, BorderLayout.CENTER);

		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

		JButton addLayer = new JButton("Add new layer");
		addLayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LayerManager.addLayer(new Layer(JOptionPane.showInputDialog("Layer name"), 0, 0));
			}
		});
		JButton deleteLayer = new JButton("Delete layer");
		deleteLayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LayerManager.removeLayer(dragDropList.getSelectedIndex());
			}
		});
		buttons.add(addLayer, BorderLayout.LINE_START);
		buttons.add(deleteLayer, BorderLayout.LINE_END);

		add(buttons, BorderLayout.PAGE_END);
	}

	public static class DragDropList extends JList<Layer> {

		public DragDropList() {
			super(LayerManager.getLayers());
			setDragEnabled(true);
			setDropMode(DropMode.INSERT);

			setTransferHandler(new ListDropHandler());

			setFont(new Font(getFont().getName(), Font.PLAIN, 15));
			new DragListener(this);
		}

		private class DragListener {

			DragListener(final DragDropList list) {
				final DragSource ds = new DragSource();
				ds.createDefaultDragGestureRecognizer(list, DnDConstants.ACTION_MOVE, new DragGestureListener() {
					@Override
					public void dragGestureRecognized(DragGestureEvent dge) {
						StringSelection transferable = new StringSelection(Integer.toString(list.getSelectedIndex()));
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

				Layer layer = LayerManager.removeLayer(index);

				if (index == 0 && (dropTargetIndex == 0 || dropTargetIndex == 1)) {
					LayerManager.addLayer(index, layer);
					return false;
				}

				if (index < dropTargetIndex) {
					dropTargetIndex--;
				}

				LayerManager.addLayer(dropTargetIndex, layer);

				setModel(LayerManager.getLayers());

				setSelectedIndex(dropTargetIndex);
				return true;
			}
		}
	}
}
