package com.ezardlabs.lostsectormapeditor.map;

import com.ezardlabs.lostsectormapeditor.map.layers.main.Layer;
import com.ezardlabs.lostsectormapeditor.map.layers.main.LayerManager;
import com.ezardlabs.lostsectormapeditor.map.layers.parallax.ParallaxLayer;
import com.ezardlabs.lostsectormapeditor.map.layers.parallax.ParallaxLayerManager;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MapManager {
	private static final MapPanel mapPanel = new MapPanel();
	private static Map map;
	private static String mapFile;

	public static void createNewMap(int width, int height) {
		map = new Map(width, height);
		mapPanel.invalidate();
	}

	public static MapPanel getMapPanel() {
		return mapPanel;
	}

	public static void openMap(String file) {
		try {
			MapManager.map = new Gson().fromJson(new FileReader(new File(file)), Map.class);
			MapManager.mapFile = file;

			LayerManager.clearLayers();
			for (Layer layer : map.getLayers()) {
				LayerManager.addLayer(layer);
			}
			ParallaxLayerManager.clearLayers();
			for (ParallaxLayer layer : map.getParallaxLayers()) {
				ParallaxLayerManager.addLayer(layer);
			}
			ParallaxLayerManager.loadAllParallaxImages();

			mapPanel.invalidate();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void saveMap() {
		if (mapFile != null) {
			saveMap(mapFile);
		}
	}

	public static Map getMap() {
		return map;
	}

	public static void saveMap(String fileName) {
		if (map != null) {
			Layer[] layers = new Layer[LayerManager.getNumLayers()];
			LayerManager.getLayers().copyInto(layers);
			map.setLayers(layers);

			ParallaxLayer[] parallaxLayers = new ParallaxLayer[ParallaxLayerManager.getNumLayers()];
			ParallaxLayerManager.getLayers().copyInto(parallaxLayers);
			map.setParallaxLayers(parallaxLayers);

			File file = new File(fileName);
			try {
				if (file.exists() || file.createNewFile()) {
					BufferedWriter writer = new BufferedWriter(new FileWriter(file));
					writer.write(new Gson().toJson(map));
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
