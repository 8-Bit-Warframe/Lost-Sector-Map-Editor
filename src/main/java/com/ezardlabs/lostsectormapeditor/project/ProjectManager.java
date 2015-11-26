package com.ezardlabs.lostsectormapeditor.project;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ProjectManager {
	private static Project currentProject;

	public static void createNewProject(File directory, String name) throws IOException {
		File projectFile = new File(directory + File.separator + ".demp");
		if (projectFile.createNewFile()) {
			currentProject = new Project(name);
			BufferedWriter writer = new BufferedWriter(new FileWriter(projectFile));
			writer.write(new Gson().toJson(currentProject));
			writer.close();
		}
	}

	public static void openExistingProject(File directory) {
		File projectFile = new File(directory + File.separator + ".demp");
		try {
			currentProject = new Gson().fromJson(new FileReader(projectFile), Project.class);
		} catch (FileNotFoundException ignored) {
		}
	}

	public static Project getCurrentProject() {
		return currentProject;
	}
}
