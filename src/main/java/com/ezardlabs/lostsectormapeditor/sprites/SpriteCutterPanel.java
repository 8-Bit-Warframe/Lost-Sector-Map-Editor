package com.ezardlabs.lostsectormapeditor.sprites;

import com.ezardlabs.lostsectormapeditor.gui.Panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class SpriteCutterPanel extends Panel {
	private Point cursor;
	private BufferedImage image;
	private float scale = 1;
	private IntegerPoint topLeft;
	private double zoom = 1;
	private Color background = Color.decode("#FF00DC");
	Rectangle selected;

	public SpriteCutterPanel(int initialWidth, int initialHeight) {
		setDoubleBuffered(true);
		setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				Dimension d = getPreferredSize();
				d.width = (int) (d.width / zoom);
				d.height = (int) (d.height / zoom);
				zoom -= e.getPreciseWheelRotation() * 0.25;
				if (zoom < 0.75) {
					zoom = 0.75;
				} else {
					d.width = (int) (d.width * zoom);
					d.height = (int) (d.height * zoom);
					setPreferredSize(d);
					revalidate();
				}
			}
		});
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				cursor = e.getPoint();
				IntegerPoint temp = getCoordinateOnOriginalImage(e.getPoint());
				selected = new Rectangle(topLeft.getX(), topLeft.getY(), temp.getX() + 16 - topLeft.getX(), temp.getY() + 16 - topLeft.getY());
				repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				cursor = e.getPoint();
				repaint();
			}
		});
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				topLeft = getCoordinateOnOriginalImage(e.getPoint());
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				IntegerPoint temp = getCoordinateOnOriginalImage(e.getPoint());
				selected = new Rectangle(topLeft.getX(), topLeft.getY(), temp.getX() + 16 - topLeft.getX(), temp.getY() + 16 - topLeft.getY());
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		});
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				scale = (float) getWidth() / (float) image.getWidth();
				if ((float) image.getHeight() * scale > (float) getHeight()) {
					scale = (float) getHeight() / (float) image.getHeight();
				}
			}
		});
		setPreferredSize(new Dimension(initialWidth, initialHeight));
	}

	public void loadSpritesheet(File spritesheet) throws IOException {
		image = ImageIO.read(spritesheet);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(background);
		g.fillRect(0, 0, getWidth(), getHeight());

		if (image != null) {
			g.drawImage(image, 0, 0, (int) (image.getWidth() * scale), (int) (image.getHeight() * scale), this);
		}
		if (cursor != null) {
			g.setColor(Color.BLUE);
			IntegerPoint p = getGridPosition(cursor);
			g.drawRect(p.getX(), p.getY(), (int) (16 * scale), (int) (16 * scale));
		}
		if (selected != null) {
			g.setColor(Color.GREEN);
			((Graphics2D) g).setStroke(new BasicStroke(scale));
			g.drawRect((int) (selected.getX() * scale), (int) (selected.getY() * scale), (int) (selected.getWidth() * scale), (int) (selected.getHeight() * scale));
		}
	}

	private IntegerPoint getGridPosition(Point point) {
		IntegerPoint coord = getCoordinateOnOriginalImage(point);
		return new IntegerPoint((int) (coord.getX() * scale), (int) (coord.getY() * scale));
	}

	private IntegerPoint getCoordinateOnOriginalImage(Point point) {
		return new IntegerPoint((int) ((point.getX()) / (image.getWidth() * scale) * image.getWidth() / 16) * 16, (int) ((point.getY()) / (image.getHeight() * scale) * image.getHeight() / 16) * 16);
	}

	private class IntegerPoint {
		private int x;
		private int y;

		IntegerPoint(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public void setX(int x) {
			this.x = x;
		}

		public void setY(int y) {
			this.y = y;
		}

		@Override
		public String toString() {
			return "IntegerPoint[x=" + x + ",y=" + y + "]";
		}
	}
}
