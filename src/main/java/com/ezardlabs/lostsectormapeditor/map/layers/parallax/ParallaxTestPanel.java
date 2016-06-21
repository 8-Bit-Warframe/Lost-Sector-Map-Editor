package com.ezardlabs.lostsectormapeditor.map.layers.parallax;

import com.ezardlabs.lostsectormapeditor.gui.Panel;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.util.Pair;

class ParallaxTestPanel extends Panel {
	private final double scale;
	private final ParallaxLayer[] layers;
	private final int[] maxX;
	private final Color background = Color.decode("#FF00DC");
	private final ArrayList<ArrayList<ImageInstance>> imageInstances = new ArrayList<>();
	private final ArrayList<Pair<ArrayList, ImageInstance>> instancesToDelete = new ArrayList<>();

	private class ImageInstance {
		int index;
		int xPos;
		ParallaxLayer layer;

		ImageInstance(int index, int xPos, ParallaxLayer layer) {
			this.index = index;
			this.xPos = xPos;
			this.layer = layer;
		}
	}

	ParallaxTestPanel(double scale, ParallaxLayer[] layers) {
		this.scale = scale;
		this.layers = layers;
		maxX = new int[layers.length];
		for (int i = 0; i < layers.length; i++) {
			imageInstances.add(new ArrayList<ImageInstance>());
		}
		setDoubleBuffered(true);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
//		g.clearRect(0, 0, getWidth(), getHeight());
		g.fillRect(0, 0, getWidth(), getHeight());
		for (int i = 0; i < maxX.length; i++) {
			if (maxX[i] < getWidth()) {
				imageInstances.get(i).add(new ImageInstance(i, maxX[i], layers[i]));
			}
		}
		Arrays.fill(maxX, 0);
		for (ArrayList<ImageInstance> iia : imageInstances) {
			for (ImageInstance ii : iia) {
				ii.xPos -= ii.layer.getSpeed();
				if (ii.xPos + ii.layer.getWidth() * scale < 0) {
					instancesToDelete.add(new Pair<ArrayList, ImageInstance>(iia, ii));
				} else {
					if (ii.xPos + ii.layer.getWidth() * scale > maxX[ii.index]) {
						maxX[ii.index] = (int) (ii.xPos + ii.layer.getWidth() * scale);
					}
					g.drawImage(ii.layer.getImage(), ii.xPos, ii.layer.getY(), (int) (ii.layer.getWidth() * scale), (int) (ii.layer.getHeight() * scale), this);
				}
			}
		}
		for (Pair<ArrayList, ImageInstance> p : instancesToDelete) {
			p.getKey().remove(p.getValue());
		}
		instancesToDelete.clear();
		repaint();
	}
}
