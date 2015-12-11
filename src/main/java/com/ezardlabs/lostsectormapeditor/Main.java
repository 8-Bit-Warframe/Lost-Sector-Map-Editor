package com.ezardlabs.lostsectormapeditor;

import com.ezardlabs.lostsectormapeditor.gui.GUI;
import com.ezardlabs.lostsectormapeditor.project.ProjectManager;

import java.io.File;
import java.util.prefs.Preferences;

public class Main {

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		Preferences prefs = Preferences.userNodeForPackage(Main.class);
		String temp;
		File f;
		if ((temp = prefs.get("project_current", null)) != null && (f = new File(temp)).exists() && f.isDirectory()) {
			GUI.getInstance();
			ProjectManager.openExistingProject(new File(temp));
		} else {
			GUI.getInstance().showNewOrOpenDialog();
		}
	}
}
