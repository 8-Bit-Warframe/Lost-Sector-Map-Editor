package com.ezardlabs.lostsectormapeditor.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Map {
	private static final int TILE_SIZE = 16;
	private Layer[] layers;
	private int width;
	private int height;

	public Map(int width, int height) {
		this.width = width;
		this.height = height;
		layers = new Layer[]{new Layer(0, width, height)};
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
		for (Layer l : layers) {
			l.draw(g, width, height);
		}
	}
}
