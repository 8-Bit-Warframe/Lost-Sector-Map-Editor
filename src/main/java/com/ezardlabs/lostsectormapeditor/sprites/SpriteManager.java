package com.ezardlabs.lostsectormapeditor.sprites;

import com.ezardlabs.lostsectormapeditor.gui.GUI;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class SpriteManager {

	public static void showSpriteCutterDialog(File file) {
		JFrame frame = new JFrame();
		SpriteCutterPanel scp = new SpriteCutterPanel((int) (GUI.getInstance().getWidth() * 0.8), (int) (GUI.getInstance().getHeight() * 0.8));
		try {
			scp.loadSpritesheet(file);
		} catch (IOException ignored) {
		}
		frame.setContentPane(new JScrollPane(scp));
		frame.setPreferredSize(new Dimension((int) (GUI.getInstance().getWidth() * 0.8), (int) (GUI.getInstance().getHeight() * 0.8)));
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}
}
