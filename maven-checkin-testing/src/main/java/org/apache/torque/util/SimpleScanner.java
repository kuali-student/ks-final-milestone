package org.apache.torque.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.plexus.util.DirectoryScanner;

/**
 * This class provides a simple method for scanning a directory for files that match include/exclude patterns
 */
public class SimpleScanner extends DirectoryScanner {
	private static final String FS = System.getProperty("file.separator");

	public SimpleScanner() {
		this(null, null, null);
	}

	public SimpleScanner(File baseDir, String include, String exclude) {
		super();
		if (baseDir != null) {
			setBasedir(baseDir);
		}
		if (include != null) {
			setIncludes(new String[] { include });
		}
		if (exclude != null) {
			setExcludes(new String[] { exclude });
		}
	}

	/**
	 * This method returns files that match an include pattern but do not match an exclude pattern
	 */
	public List<File> getFiles() {
		scan();
		String[] includedFiles = getIncludedFiles();
		List<File> files = new ArrayList<File>();
		for (String includedFile : includedFiles) {
			String filename = getBasedir().getAbsolutePath() + FS + includedFile;
			File file = new File(filename);
			files.add(file);
		}
		return files;
	}
}
