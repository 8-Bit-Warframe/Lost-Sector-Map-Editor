package com.ezardlabs.lostsectormapeditor.project;

import com.ezardlabs.lostsectormapeditor.Main;
import com.ezardlabs.lostsectormapeditor.project.DirectoryWatcher.FileChangeListener;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.prefs.Preferences;

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
				Preferences.userNodeForPackage(Main.class).put("project_current", directory.getAbsolutePath());
				new DirectoryWatcher(directory, new FileChangeListener() {
					@Override
					public void onFileCreated(File file) {
						projectPanel.setProject(currentProject);

					}

					@Override
					public void onFileModified(File file) {
					}

					@Override
					public void onFileDeleted(File file) {
						projectPanel.setProject(currentProject);
					}
				}).processEvents();
			} catch (IOException ignored) {
			}

		}
	}

	public static Project getCurrentProject() {
		return currentProject;
	}

	public static ProjectPanel getProjectPanel() {
		return projectPanel;
	}
}
