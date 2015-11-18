package com.ezardlabs.lostsectormapeditor.gui;

import com.ezardlabs.lostsectormapeditor.map.Map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

public class MapPanel extends JPanel {
	private Map map = new Map(100, 100);
	private Point camera = new Point();
	private double zoom = 1;

	private boolean dragging;
	private Point dragStart;
	private Point cameraStart;

	MapPanel() {
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					dragging = true;
					dragStart = e.getPoint();
					cameraStart = (Point) camera.clone();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					dragging = false;
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (dragging) {
					camera.x = cameraStart.x + (e.getX() - dragStart.x);
					camera.y = cameraStart.y + (e.getY() - dragStart.y);
					if (camera.x > 0) camera.x = 0;
					if (camera.y > 0) camera.y = 0;
					repaint();
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {

			}
		});
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				zoom -= e.getPreciseWheelRotation() * 0.25;
				repaint();
			}
		});
		setDoubleBuffered(true);
	}

	public void setMap(Map map) {
		this.map = map;
	}

	@Override
	public void paint(Graphics g) {
		if (map != null) {
			map.draw((Graphics2D) g, camera, zoom);
		}
	}
}