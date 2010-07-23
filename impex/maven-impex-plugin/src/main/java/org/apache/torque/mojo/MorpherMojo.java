package org.apache.torque.mojo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.project.MavenProject;

/**
 * Some base logic for converting files
 */
public abstract class MorpherMojo extends AbstractMojo {
	protected static final String FS = System.getProperty("file.separator");

	/**
	 * The Maven project this plugin runs in.
	 * 
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	private MavenProject project;

	protected void writeContents(String filename, String contents) throws IOException {
		OutputStream out = null;
		try {
			out = new FileOutputStream(filename);
			IOUtils.write(contents, out, "UTF-8");
		} catch (IOException e) {
			throw e;
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	protected String getContents(String filename) throws IOException {
		InputStream in = null;
		try {
			in = new FileInputStream(filename);
			return IOUtils.toString(in, "UTF-8");
		} catch (IOException e) {
			throw e;
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	/**
	 * Sets the maven project.
	 * 
	 * @param project
	 *            the maven project where this plugin runs in.
	 */
	public void setProject(MavenProject project) {
		this.project = project;
	}

	/**
	 * Returns the maven project.
	 * 
	 * @return The maven project where this plugin runs in.
	 */
	public MavenProject getProject() {
		return project;
	}

}
