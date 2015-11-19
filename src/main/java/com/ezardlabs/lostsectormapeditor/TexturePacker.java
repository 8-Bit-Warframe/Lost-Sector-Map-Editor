package com.ezardlabs.lostsectormapeditor;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.imageio.ImageIO;

public class TexturePacker {

	public static void pack(File dir) {
		ArrayList<File> imageFiles = new ArrayList<File>();
		if (dir.isDirectory()) {
			File[] files = dir.listFiles(new ImageFilenameFilter());

			imageFiles.addAll(Arrays.asList(files));
		} else {
			System.out.println("Error: Could not find directory '" + dir.getPath() + "'");
			return;
		}
		TreeSet<ImageName> imageNameSet = new TreeSet<ImageName>(new ImageNameComparator());
		for (File f : imageFiles) {
			try {
				BufferedImage image = ImageIO.read(f);
				String path = f.getPath().substring(0, f.getPath().lastIndexOf(".")).replace("\\", "/");
				imageNameSet.add(new ImageName(image, path));
			} catch (IOException e) {
				System.out.println("Could not open file: '" + f.getAbsoluteFile() + "'");
			}
		}

		run(1, 1, imageNameSet);
	}

	private static void run(int width, int height, TreeSet<ImageName> imageNameSet) {
		Texture texture = new Texture(width, height);
		for (ImageName imageName : imageNameSet) {
			if (!texture.addImage(imageName.image, imageName.name)) {
				run(width * 2, height * 2, imageNameSet);
				return;
			}
		}
		texture.write();
	}

	private static class ImageName {
		public BufferedImage image;
		public String name;

		public ImageName(BufferedImage image, String name) {
			this.image = image;
			this.name = name;
		}
	}

	private static class ImageNameComparator implements Comparator<ImageName> {
		public int compare(ImageName image1, ImageName image2) {
			int area1 = image1.image.getWidth() * image1.image.getHeight();
			int area2 = image2.image.getWidth() * image2.image.getHeight();

			if (area1 != area2) {
				return area2 - area1;
			} else {
				return image1.name.compareTo(image2.name);
			}
		}
	}

	private static class ImageFilenameFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return name.toLowerCase().endsWith(".png");
		}
	}

	private static class Texture {
		private class Node {
			public Rectangle rect;
			public Node child[];
			public BufferedImage image;

			public Node(int x, int y, int width, int height) {
				rect = new Rectangle(x, y, width, height);
				child = new Node[2];
				child[0] = null;
				child[1] = null;
				image = null;
			}

			public boolean isLeaf() {
				return child[0] == null && child[1] == null;
			}

			// Algorithm from http://www.blackpawn.com/texts/lightmaps/
			public Node insert(BufferedImage image) {
				if (!isLeaf()) {
					Node newNode = child[0].insert(image);

					if (newNode != null) {
						return newNode;
					}

					return child[1].insert(image);
				} else {
					if (this.image != null) {
						return null; // occupied
					}

					if (image.getWidth() > rect.width || image.getHeight() > rect.height) {
						return null; // does not fit
					}

					if (image.getWidth() == rect.width && image.getHeight() == rect.height) {
						this.image = image; // perfect fit
						return this;
					}

					int dw = rect.width - image.getWidth();
					int dh = rect.height - image.getHeight();

					if (dw > dh) {
						child[0] = new Node(rect.x, rect.y, image.getWidth(), rect.height);
						child[1] = new Node(rect.x + image.getWidth(), rect.y, rect.width - image.getWidth(), rect.height);
					} else {
						child[0] = new Node(rect.x, rect.y, rect.width, image.getHeight());
						child[1] = new Node(rect.x, rect.y + image.getHeight(), rect.width, rect.height - image.getHeight());
					}

					return child[0].insert(image);
				}
			}
		}

		private BufferedImage image;
		private Graphics2D graphics;
		private Node root;
		private Map<String, Rectangle> rectangleMap;

		public Texture(int width, int height) {
			image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
			graphics = image.createGraphics();

			root = new Node(0, 0, width, height);
			rectangleMap = new TreeMap<String, Rectangle>();
		}

		public boolean addImage(BufferedImage image, String name) {
			Node node = root.insert(image);

			if (node == null) {
				return false;
			}

			rectangleMap.put(name, node.rect);
			graphics.drawImage(image, null, node.rect.x, node.rect.y);


			return true;
		}

		public void write() {
			try {
				ImageIO.write(image, "png", new File("atlas.png"));

				BufferedWriter atlas = new BufferedWriter(new FileWriter("atlas.txt"));

				for (Map.Entry<String, Rectangle> e : rectangleMap.entrySet()) {
					Rectangle r = e.getValue();
					String keyVal = e.getKey();
					keyVal = keyVal.substring(keyVal.lastIndexOf('/') + 1);
					atlas.write(keyVal + " = " + r.x + " " + r.y + " " + r.width + " " + r.height);
					atlas.newLine();
				}

				atlas.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}