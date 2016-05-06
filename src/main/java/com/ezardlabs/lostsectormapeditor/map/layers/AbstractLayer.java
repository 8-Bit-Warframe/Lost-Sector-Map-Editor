package com.ezardlabs.lostsectormapeditor.map.layers;

import java.awt.Graphics2D;
import java.awt.Point;

public abstract class AbstractLayer {
	protected String name;
	protected int width;
	protected int height;

	public AbstractLayer(String name, int width, int height) {
		this.name = name;
		this.width = width;
		this.height = height;
	}

	public abstract void draw(Graphics2D g, Point camera, double zoom);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public String toString() {
		return name;
	}
}
