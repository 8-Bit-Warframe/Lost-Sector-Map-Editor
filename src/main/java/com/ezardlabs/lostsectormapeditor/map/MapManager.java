package com.ezardlabs.lostsectormapeditor.map;

import com.ezardlabs.lostsectormapeditor.map.layers.main.Layer;
import com.ezardlabs.lostsectormapeditor.map.layers.main.LayerManager;
import com.ezardlabs.lostsectormapeditor.map.layers.parallax.ParallaxLayer;
import com.ezardlabs.lostsectormapeditor.map.layers.parallax.ParallaxLayerManager;
import com.ezardlabs.lostsectormapeditor.project.ProjectManager;
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
	private static File file;

	public static void createNewMap(String name, int width, int height) {
		MapManager.file = new File(ProjectManager.getCurrentProject().getDirectory() + File.separator + name + ".lsmap");
		map = new Map(width, height);
		saveMap();
		mapPanel.invalidate();
	}

	public static MapPanel getMapPanel() {
		return mapPanel;
	}

	public static void openMap(String file) {
		System.out.println(file);
		try {
			File f = new File(file);
			MapManager.map = new Gson().fromJson(new FileReader(f), Map.class);
			MapManager.file = f;

			LayerManager.clearLayers();
			if (map.getLayers() != null) {
				for (Layer layer : map.getLayers()) {
					LayerManager.addLayer(layer);
				}
			}
			ParallaxLayerManager.clearLayers();
			if (map.getParallaxLayers() != null) {
				for (ParallaxLayer layer : map.getParallaxLayers()) {
					ParallaxLayerManager.addLayer(layer);
				}
			}
			ParallaxLayerManager.loadAllParallaxImages();

			mapPanel.invalidate();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void saveMap() {
		if (file != null) {
			saveMap(file);
		}
	}

	public static Map getMap() {
		return map;
	}

	public static void saveMap(File file) {
		if (map != null) {
			Layer[] layers = new Layer[LayerManager.getNumLayers()];
			LayerManager.getLayers().copyInto(layers);
			map.setLayers(layers);

			ParallaxLayer[] parallaxLayers = new ParallaxLayer[ParallaxLayerManager.getNumLayers()];
			ParallaxLayerManager.getLayers().copyInto(parallaxLayers);
			map.setParallaxLayers(parallaxLayers);

			try {
				if (file.exists() || file.createNewFile()) {
					BufferedWriter writer = new BufferedWriter(new FileWriter(file));
					writer.write(new Gson().toJson(map));
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			ProjectManager.refresh();
		}
	}
}
