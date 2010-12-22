package org.apache.torque.mojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.torque.mojo.morph.MorphRequest;
import org.apache.torque.mojo.morph.Morpher;
import org.codehaus.plexus.util.FileUtils;

/**
 * Common logic for morphing one file to another file
 */
public abstract class AbstractMorphSingleMojo extends BaseMojo {

	/**
	 * The file that will contain the morphed contents
	 */
	private File newFile;

	/**
	 * The file containing the contents to be morphed
	 */
	private File oldFile;

	/**
	 * Add logic to the basic skip() method for skipping based on a morph being
	 * needed
	 */
	@Override
	protected boolean skipMojo() {
		// We may be skipping based on packaging of type 'pom'
		if (super.skipMojo()) {
			return true;
		}

		// If a morph is needed, we can't skip
		boolean morphNeeded = isMorphNeeded();
		if (morphNeeded) {
			return false;
		} else {
			getLog().info("Skipping morph.  Nothing has changed");
			return true;
		}
	}

	protected boolean isMorphNeeded() {
		getLog().info("oldFile=" + getOldFile().getAbsolutePath());
		getLog().info("newFile=" + getNewFile().getAbsolutePath());
		getLog().info("1");
		// The file they asked to morph does not exist
		if (!getOldFile().exists()) {
			getLog().info("file:" + getOldFile().getAbsolutePath() + " does not exist");
			return false;
		}
		getLog().info("2");

		// The new file does not exist, we need to morph
		if (!getNewFile().exists()) {
			return true;
		}
		getLog().info("3");

		// Extract the last modified timestamps
		long oldLastModified = getOldFile().lastModified();
		long newLastModified = getNewFile().lastModified();
		getLog().info("4");

		// If old file has been modified since the new file was last modified,
		// we need to morph
		return oldLastModified > newLastModified;
	}

	protected abstract Morpher getMorpher(MorphRequest request, String artifactId);

	@Override
	protected void executeMojo() throws MojoExecutionException {
		try {
			FileUtils.forceMkdir(new File(FileUtils.getPath(newFile.getAbsolutePath())));
			MorphRequest request = new MorphRequest(oldFile.getName(), new FileInputStream(oldFile), new FileOutputStream(newFile));
			request.setEncoding(getEncoding());
			Morpher morpher = getMorpher(request, getProject().getArtifactId());
			morpher.executeMorph();
		} catch (IOException e) {
			throw new MojoExecutionException("Unexpected error while attempting to morph " + oldFile.getAbsolutePath(), e);
		}
	}

	public File getNewFile() {
		return newFile;
	}

	public void setNewFile(File newFile) {
		this.newFile = newFile;
	}

	public File getOldFile() {
		return oldFile;
	}

	public void setOldFile(File oldFile) {
		this.oldFile = oldFile;
	}
}
