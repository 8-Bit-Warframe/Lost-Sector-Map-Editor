package com.ezardlabs.lostsectormapeditor;

import com.ezardlabs.lostsectormapeditor.gui.GUI;
import com.ezardlabs.lostsectormapeditor.project.ProjectManager;

import java.io.File;
import java.util.prefs.Preferences;

public class Main {
	private GUI gui;

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		Preferences prefs = Preferences.userNodeForPackage(Main.class);
		String temp;
		File f;
		gui = new GUI();
		if ((temp = prefs.get("project_current", null)) != null && (f = new File(temp)).exists() && f.isDirectory()) {
			ProjectManager.openExistingProject(new File(temp));
		} else {
			gui.showNewOrOpenDialog();
		}
	}
}
