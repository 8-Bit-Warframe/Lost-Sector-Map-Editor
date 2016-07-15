package com.ezardlabs.lostsectormapeditor.map.layers.parallax;

import com.ezardlabs.lostsectormapeditor.gui.GUI;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;

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

	public static void showTestWindow() {
		int maxHeight = 0;
		for (int i = 0; i < layers.size(); i++) {
			if (layers.get(i).getHeight() > maxHeight) {
				maxHeight = layers.get(i).getHeight();
			}
		}
		JDialog dialog = new JDialog(GUI.getInstance(), "Parallax Test", true);
		ParallaxLayer[] layerArray = new ParallaxLayer[layers.size()];
		layers.copyInto(layerArray);
		ParallaxTestPanel ptp = new ParallaxTestPanel((GUI.getInstance().getHeight() * 0.8) / maxHeight, layerArray);
		ptp.setSize(new Dimension((int) (GUI.getInstance().getWidth() * 0.8), (int) (GUI.getInstance().getHeight() * 0.8)));
		dialog.add(ptp);
		dialog.setSize(new Dimension((int) (GUI.getInstance().getWidth() * 0.8), (int) (GUI.getInstance().getHeight() * 0.8)));
		dialog.setLocation((int) (GUI.getInstance().getWidth() * 0.1), (int) (GUI.getInstance().getHeight() * 0.1));
		dialog.setResizable(false);
		dialog.setVisible(true);
	}

	public static void loadAllParallaxImages() {
		for (int i = 0; i < layers.size(); i++) {
			layers.get(i).loadImage();
		}
	}
}
