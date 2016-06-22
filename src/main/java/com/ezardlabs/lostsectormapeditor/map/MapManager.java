package com.ezardlabs.lostsectormapeditor.map;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

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

	public static Map getMap() {
		return map;
	}
}
