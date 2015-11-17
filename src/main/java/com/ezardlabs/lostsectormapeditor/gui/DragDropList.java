package com.ezardlabs.lostsectormapeditor.gui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JList;
import javax.swing.TransferHandler;

public class DragDropList extends JList<String> {
	private DefaultListModel<String> model;

	public DragDropList() {
		super(new DefaultListModel<String>());
		model = (DefaultListModel<String>) getModel();
		setDragEnabled(true);
		setDropMode(DropMode.INSERT);

		setTransferHandler(new ListDropHandler(this));

		new DragListener(this);

		model.addElement("a");
		model.addElement("b");
		model.addElement("c");
	}

	public void setData(Object[] data) {
		model.clear();
		for (Object o : data) {
			model.addElement(o.toString());
		}
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
		private DragDropList list;

		ListDropHandler(DragDropList list) {
			this.list = list;
		}

		public boolean canImport(TransferHandler.TransferSupport support) {
			if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				return false;
			}
			JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
			return dl.getIndex() != -1;
		}

		public boolean importData(TransferHandler.TransferSupport support) {
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

			String s = model.remove(index);

			if (index == 0 && (dropTargetIndex == 0 || dropTargetIndex == 1)) {
				model.add(index, s);
				return false;
			}

			if (index < dropTargetIndex) {
				dropTargetIndex--;
			}

			model.add(dropTargetIndex, s);
			return true;
		}
	}
}