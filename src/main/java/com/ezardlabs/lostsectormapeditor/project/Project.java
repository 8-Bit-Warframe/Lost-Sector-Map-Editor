package com.ezardlabs.lostsectormapeditor.project;

import java.io.File;

class Project {
	private final String name;
	private final File directory;

	Project(String name, File directory) {
		this.name = name;
		this.directory = directory;
	}

	public String getName() {
		return name;
	}

	public File getDirectory() {
		return directory;
	}
}
