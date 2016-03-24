package com.ezardlabs.lostsectormapeditor.sprites;

import com.ezardlabs.lostsectormapeditor.gui.GUI;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class SpriteManager {

	public static void showSpriteCutterDialog(File file) {
		JFrame frame = new JFrame();

		SpriteDetailsPanel sdp = new SpriteDetailsPanel();
		SpriteCutterPanel scp = new SpriteCutterPanel((int) (GUI.getInstance().getWidth() * 0.8), (int) (GUI.getInstance().getHeight() * 0.8));
		scp.setPreferredSize(new Dimension((int) (GUI.getInstance().getWidth() * 0.6), (int) (GUI.getInstance().getHeight() * 0.8)));
		try {
			scp.loadSpritesheet(file);
		} catch (IOException ignored) {
		}
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scp, sdp);
		frame.add(splitPane);
		frame.setPreferredSize(new Dimension((int) (GUI.getInstance().getWidth() * 0.8), (int) (GUI.getInstance().getHeight() * 0.8)));
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}
}
