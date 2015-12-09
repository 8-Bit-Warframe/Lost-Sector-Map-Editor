package com.ezardlabs.lostsectormapeditor.map;

import com.ezardlabs.lostsectormapeditor.map.layers.Layer;
import com.ezardlabs.lostsectormapeditor.map.layers.LayerManager;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Map {
	private static final int TILE_SIZE = 16;
	private int width;
	private int height;

	public Map(int width, int height) {
		this.width = width;
		this.height = height;
		LayerManager.getLayers().addElement(new Layer("Main", width, height));
	}

	public void draw(Graphics2D g, Point camera, double zoom) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width * TILE_SIZE, height * TILE_SIZE);

		g.translate(camera.x, camera.y);
		g.scale(zoom, zoom);

		g.setColor(Color.DARK_GRAY);
		for (int i = 0; i <= width; i++) {
			g.drawLine(i * TILE_SIZE, 0, i * TILE_SIZE, width * TILE_SIZE);
		}
		for (int i = 0; i <= height; i++) {
			g.drawLine(0, i * TILE_SIZE, height * TILE_SIZE, i * TILE_SIZE);
		}
		for (int i = 0; i < LayerManager.getLayers().size(); i++) {
			LayerManager.getLayers().get(i).draw(g, camera, zoom);
		}
	}
}
