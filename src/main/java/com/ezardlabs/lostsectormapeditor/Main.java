package com.ezardlabs.lostsectormapeditor;

import com.ezardlabs.lostsectormapeditor.gui.GUI;
import com.ezardlabs.lostsectormapeditor.project.ProjectManager;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		String temp;
		File f;
		if ((temp = PreferenceManager.getString(PreferenceManager.PROJECT_CURRENT)) != null && (f = new File(temp)).exists() && f.isDirectory()) {
			GUI.getInstance();
			ProjectManager.openExistingProject(new File(temp));
		} else {
			GUI.getInstance().showNewOrOpenDialog();
		}
	}
}
