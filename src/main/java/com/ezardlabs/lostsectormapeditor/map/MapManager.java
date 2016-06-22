package com.ezardlabs.lostsectormapeditor.map;

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
			mapPanel.invalidate();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void saveMap() {
		if (map != null) {
			File projectFile = new File(mapFile);
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(projectFile));
				writer.write(new Gson().toJson(map));
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Map getMap() {
		return map;
	}
}
