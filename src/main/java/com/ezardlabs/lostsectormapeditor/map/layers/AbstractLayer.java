package com.ezardlabs.lostsectormapeditor.map.layers;

import java.awt.Graphics2D;
import java.awt.Point;

public abstract class AbstractLayer {
	protected String name;

	public AbstractLayer(String name) {
		this.name = name;
	}

	public abstract void draw(Graphics2D g, Point camera, double zoom);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
