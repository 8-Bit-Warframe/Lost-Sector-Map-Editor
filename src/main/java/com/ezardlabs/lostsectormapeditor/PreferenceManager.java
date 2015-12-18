package com.ezardlabs.lostsectormapeditor;

import java.util.prefs.Preferences;

public class PreferenceManager {
	public static final String PROJECT_CURRENT = "project_current";
	public static final String PROJECT_LAST_LOCATION = "project_last_location";
	public static final String IMPORT_LAST_LOCATION = "import_last_location";

	private static final Preferences prefs = Preferences.userNodeForPackage(Main.class);

	public static String getString(String key) {
		return getString(key, null);
	}

	public static String getString(String key, String def) {
		return prefs.get(key, def);
	}

	public static void putString(String key, String value) {
		prefs.put(key, value);
	}
}
