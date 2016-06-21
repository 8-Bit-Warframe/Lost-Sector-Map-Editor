package com.ezardlabs.lostsectormapeditor.project;

import com.ezardlabs.lostsectormapeditor.PreferenceManager;
import com.ezardlabs.lostsectormapeditor.map.layers.main.Layer;
import com.ezardlabs.lostsectormapeditor.map.layers.main.LayerManager;
import com.ezardlabs.lostsectormapeditor.map.layers.parallax.ParallaxLayer;
import com.ezardlabs.lostsectormapeditor.map.layers.parallax.ParallaxLayerManager;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
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
				LayerManager.clearLayers();
				for (Layer layer : currentProject.getLayers()) {
					LayerManager.addLayer(layer);
				}
				ParallaxLayerManager.clearLayers();
				for (ParallaxLayer layer : currentProject.getParallaxLayers()) {
					ParallaxLayerManager.addLayer(layer);
				}
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
		if (currentProject != null) {
			Layer[] layers = new Layer[LayerManager.getNumLayers()];
			LayerManager.getLayers().copyInto(layers);
			currentProject.setLayers(layers);

			ParallaxLayer[] parallaxLayers = new ParallaxLayer[ParallaxLayerManager.getNumLayers()];
			ParallaxLayerManager.getLayers().copyInto(parallaxLayers);
			currentProject.setParallaxLayers(parallaxLayers);

			File projectFile = new File(currentProject.getDirectory() + File.separator + ".demp");
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
}
