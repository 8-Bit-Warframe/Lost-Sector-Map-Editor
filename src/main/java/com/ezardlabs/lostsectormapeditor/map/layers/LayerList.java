package com.ezardlabs.lostsectormapeditor.map.layers;

import com.ezardlabs.lostsectormapeditor.gui.Panel;

import java.awt.Cursor;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class LayerList<T extends AbstractLayer> extends Panel {
	private ListModel<T> model;
	private LayerListComponentCreator<T> componentCreator;
	private int selectedIndex = -1;
	private JComponent[] items = new JComponent[0];

	LayerList(ListModel<T> model, LayerListComponentCreator<T> componentCreator) {
		setModel(model);
		setLayerListComponentCreator(componentCreator);

//		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	private void refreshList() {
		removeAll();
		repaint();

		items = new JComponent[model.getSize()];

		for (int i = 0; i < model.getSize(); i++) {
			items[i] = componentCreator.getLayerListComponent(this, model.getElementAt(i), i, i == selectedIndex);
			if (i != selectedIndex) {
				final int j = i;
				items[i].addFocusListener(new FocusListener() {
					@Override
					public void focusGained(FocusEvent e) {
						selectedIndex = j;
						refreshList();
					}

					@Override
					public void focusLost(FocusEvent e) {

					}
				});
				items[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				items[i].addMouseListener(new MouseListener() {
					@Override
					public void mouseClicked(MouseEvent e) {
						selectedIndex = j;
						refreshList();
					}

					@Override
					public void mousePressed(MouseEvent e) {
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
			items[i].setFocusable(true);
			items[i].setBorder(new EmptyBorder(0, 0, 5, 0));
			add(items[i]);
		}

		revalidate();
	}

	void setModel(ListModel<T> model) {
		this.model = model;
		model.addListDataListener(new ListDataListener() {
			@Override
			public void intervalAdded(ListDataEvent e) {
				refreshList();
			}

			@Override
			public void intervalRemoved(ListDataEvent e) {
				refreshList();
			}

			@Override
			public void contentsChanged(ListDataEvent e) {
				refreshList();
			}
		});
	}

	void setLayerListComponentCreator(LayerListComponentCreator<T> componentCreator) {
		this.componentCreator = componentCreator;
	}

	int getSelectedIndex() {
		return selectedIndex;
	}

	void setSelectedIndex(int index) {
		selectedIndex = index;
		refreshList();
	}

	public interface LayerListComponentCreator<T extends AbstractLayer> {
		JComponent getLayerListComponent(LayerList<T> list, T layer, int index, boolean isSelected);
	}
}
