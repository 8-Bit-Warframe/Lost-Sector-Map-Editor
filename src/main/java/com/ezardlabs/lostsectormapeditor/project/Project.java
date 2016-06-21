package com.ezardlabs.lostsectormapeditor.project;

import com.ezardlabs.lostsectormapeditor.map.layers.main.Layer;
import com.ezardlabs.lostsectormapeditor.map.layers.parallax.ParallaxLayer;

import java.io.File;

class Project {
	private final String name;
	private final File directory;
	private Layer[] layers;
	private ParallaxLayer[] parallaxLayers;

	Project(String name, File directory) {
		this.name = name;
		this.directory = directory;
	}

	public String getName() {
		return name;
	}

	public File getDirectory() {
		return directory;
	}

	public void setLayers(Layer[] layers) {
		this.layers = layers;
	}

	public Layer[] getLayers() {
		return layers;
	}

	public void setParallaxLayers(ParallaxLayer[] parallaxLayers) {
		this.parallaxLayers = parallaxLayers;
	}

	public ParallaxLayer[] getParallaxLayers() {
		return parallaxLayers;
	}
}
