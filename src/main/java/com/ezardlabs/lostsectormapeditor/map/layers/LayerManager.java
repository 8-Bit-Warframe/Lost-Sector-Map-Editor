package com.ezardlabs.lostsectormapeditor.map.layers;

import javax.swing.DefaultListModel;

public class LayerManager {
	private static final DefaultListModel<Layer> layers = new DefaultListModel<>();
	private static final LayerPanel layerPanel = new LayerPanel();

	public static void addLayer(Layer layer) {
		layers.addElement(layer);
	}

	public static void addLayer(int index, Layer layer) {
		layers.add(index, layer);
	}

	public static Layer removeLayer(int index) {
		return layers.remove(index);
	}

	public static DefaultListModel<Layer> getLayers() {
		return layers;
	}

	public static LayerPanel getLayerPanel() {
		return layerPanel;
	}
}