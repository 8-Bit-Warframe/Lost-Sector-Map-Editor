package com.ezardlabs.lostsectormapeditor.project;

import com.ezardlabs.lostsectormapeditor.PreferenceManager;
import com.ezardlabs.lostsectormapeditor.map.Map;
import com.ezardlabs.lostsectormapeditor.map.MapManager;
import com.ezardlabs.lostsectormapeditor.map.layers.main.LayerManager;
import com.ezardlabs.lostsectormapeditor.map.layers.parallax.ParallaxLayer;
import com.ezardlabs.lostsectormapeditor.map.layers.parallax.ParallaxLayerManager;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ProjectManager {
	private static Project currentProject;
	private static final ProjectPanel projectPanel = new ProjectPanel();

	public static void createNewProject(File directory, String name) throws IOException {
		File projectFile = new File(directory + File.separator + ".demp");
		if (projectFile.createNewFile()) {
			currentProject = new Project(name, directory);
			BufferedWriter writer = new BufferedWriter(new FileWriter(projectFile));
			writer.write(new Gson().toJson(currentProject));
			writer.close();
			openExistingProject(directory);
		}
	}

	public static void openExistingProject(File directory) {
		File projectFile = new File(directory + File.separator + ".demp");
		if (projectFile.exists()) {
			try {
				currentProject = new Gson().fromJson(new FileReader(projectFile), Project.class);
				projectPanel.setProject(currentProject);
				PreferenceManager.putString(PreferenceManager.PROJECT_CURRENT, directory.getAbsolutePath());
//				new DirectoryWatcher(directory, new FileChangeListener() {
//					@Override
//					public void onFileCreated(File file) {
//						projectPanel.setProject(currentProject);
//
//					}
//
//					@Override
//					public void onFileModified(File file) {
//					}
//
//					@Override
//					public void onFileDeleted(File file) {
//						projectPanel.setProject(currentProject);
//					}
//				}).processEvents();
			} catch (IOException ignored) {
			}

		}
	}

	public static void saveCurrentProject() throws IOException {
		if (currentProject != null) {File projectFile = new File(currentProject.getDirectory() + File.separator + ".demp");
			BufferedWriter writer = new BufferedWriter(new FileWriter(projectFile));
			writer.write(new Gson().toJson(currentProject));
			writer.close();
		}
	}

	public static Project getCurrentProject() {
		return currentProject;
	}

	public static ProjectPanel getProjectPanel() {
		return projectPanel;
	}

	public static void refresh() {
		projectPanel.setProject(currentProject);
	}

	public static void openMap(String file) {
		try {
			MapManager.setMap(new Gson().fromJson(new FileReader(new File(file)), Map.class));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
