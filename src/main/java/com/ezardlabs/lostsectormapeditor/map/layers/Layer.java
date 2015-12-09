package com.ezardlabs.lostsectormapeditor.map.layers;

import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.DefaultListModel;

public class Layer {
	public static final DefaultListModel<Layer> layers = new DefaultListModel<>();

	private String name;
	private int width;
	private int height;

	public Layer(String name, int width, int height) {
		this.name = name;
		this.width = width;
		this.height = height;
	}

	public void draw(Graphics2D g, Point camera, double zoom) {

	}

	@Override
	public String toString() {
		return name;
	}
}
