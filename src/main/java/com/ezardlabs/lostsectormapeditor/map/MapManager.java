package com.ezardlabs.lostsectormapeditor.map;

public class MapManager {
	private static final MapPanel mapPanel = new MapPanel();
	private static Map map;

	public static void createNewMap(int width, int height) {
		map = new Map(width, height);
		mapPanel.invalidate();
	}

	public static MapPanel getMapPanel() {
		return mapPanel;
	}

	public static Map getMap() {
		return map;
	}
}
