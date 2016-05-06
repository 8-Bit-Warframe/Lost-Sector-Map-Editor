package com.ezardlabs.lostsectormapeditor.map.layers;

import com.ezardlabs.lostsectormapeditor.gui.Panel;
import com.ezardlabs.lostsectormapeditor.map.layers.LayerList.LayerListComponentCreator;
import com.ezardlabs.lostsectormapeditor.map.layers.main.Layer;
import com.ezardlabs.lostsectormapeditor.map.layers.main.LayerManager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ListModel;

public abstract class AbstractLayerPanel<T extends AbstractLayer> extends Panel {

	public AbstractLayerPanel() {
		setLayout(new BorderLayout());

		JButton addLayer = new JButton("Add");
		JButton deleteLayer = new JButton("Delete");
		final JButton moveLayerUp = new JButton("Move up");
		final JButton moveLayerDown = new JButton("Move down");

		final LayerList<T> list = new LayerList<>(getListModel(), getComponentCreator());

//		list.setCellRenderer(getCellRenderer());
//		list.addListSelectionListener(new ListSelectionListener() {
//			@Override
//			public void valueChanged(ListSelectionEvent e) {
//				if (!e.getValueIsAdjusting()) {
//					moveLayerUp.setEnabled(list.getSelectedIndex() > 0);
//					moveLayerDown.setEnabled(list.getSelectedIndex() < list.getModel().getSize() - 1);
//				}
//			}
//		});

		add(list, BorderLayout.CENTER);

		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

		addLayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onAddLayerClicked();
			}
		});
		deleteLayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onDeleteLayerClicked(list.getSelectedIndex());
			}
		});
		moveLayerUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				Layer l = LayerManager.removeLayer(index);
				LayerManager.addLayer(index - 1, l);
				list.setSelectedIndex(index - 1);
			}
		});
		moveLayerDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				Layer l = LayerManager.removeLayer(index);
				LayerManager.addLayer(index + 1, l);
				list.setSelectedIndex(index + 1);
			}
		});

		buttons.add(addLayer, BorderLayout.LINE_START);
		buttons.add(deleteLayer, BorderLayout.LINE_END);
		buttons.add(moveLayerUp, BorderLayout.LINE_END);
		buttons.add(moveLayerDown, BorderLayout.LINE_END);

		add(buttons, BorderLayout.PAGE_END);
	}

	protected abstract ListModel<T> getListModel();

	protected abstract LayerListComponentCreator<T> getComponentCreator();

	protected abstract void onAddLayerClicked();

	protected abstract void onDeleteLayerClicked(int index);
}
