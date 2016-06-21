package com.ezardlabs.lostsectormapeditor.map.layers.parallax;

import javax.swing.DefaultListModel;

public class ParallaxLayerManager {
	private static final DefaultListModel<ParallaxLayer> layers = new DefaultListModel<>();
	private static final ParallaxLayerPanel layerPanel = new ParallaxLayerPanel();

	public static void addLayer(ParallaxLayer layer) {
		layers.addElement(layer);
	}

	public static void addLayer(int index, ParallaxLayer layer) {
		layers.add(index, layer);
	}

	public static ParallaxLayer removeLayer(int index) {
		return layers.remove(index);
	}

	public static DefaultListModel<ParallaxLayer> getLayers() {
		return layers;
	}

	public static int getNumLayers() {
		return layers.size();
	}

	public static ParallaxLayerPanel getLayerPanel() {
		return layerPanel;
	}

	public static void clearLayers() {
		layers.clear();
	}
}
