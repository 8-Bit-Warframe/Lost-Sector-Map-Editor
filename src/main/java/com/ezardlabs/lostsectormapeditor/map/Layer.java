package com.ezardlabs.lostsectormapeditor.map;

import java.awt.Graphics2D;

public class Layer {
	private int index;
	private int width;
	private int height;

	Layer(int index, int width, int height) {
		this.index = index;
		this.width = width;
		this.height = height;
	}

	public void draw(Graphics2D g, int width, int height) {

	}
}
