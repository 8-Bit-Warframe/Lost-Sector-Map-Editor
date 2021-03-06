package com.ezardlabs.lostsectormapeditor.map.layers.parallax;

import com.ezardlabs.lostsectormapeditor.map.layers.AbstractLayer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ParallaxLayer extends AbstractLayer {
	private transient BufferedImage image;
	private String imageLocation;
	private float speed;
	private int y;
	private int width;
	private int height;

	public ParallaxLayer(String name, String imageLocation, int y, float speed) {
		super(name);
		try {
			image = ImageIO.read(new File(imageLocation));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Image does not exist");
		}
		this.imageLocation = imageLocation;
		this.y = y;
		this.speed = speed;
		this.width = image.getWidth();
		this.height = image.getHeight();
	}

	@Override
	public void draw(Graphics2D g, Point camera, double zoom) {

	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void loadImage() {
		try {
			image = ImageIO.read(new File(imageLocation));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Image does not exist");
		}
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
