package org.apache.torque.mojo;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.torque.mojo.morph.FileMorphRequest;
import org.apache.torque.mojo.morph.MorphRequest;
import org.apache.torque.mojo.morph.Morpher;
import org.apache.torque.mojo.morph.VersionTableMorpher;
import org.codehaus.plexus.util.FileUtils;

/**
 * Morph the xml inside KS_DB_VERSION.xml so it has Maven friendly placeholders
 * for version info
 * 
 * @goal morphversiontable
 * @phase generate-sources
 */
public class MorphVersionTableMojo extends AbstractMorphSingleMojo {

	/**
	 * The XML file that will contain the new information
	 * 
	 * @parameter expression="${newVersionXMLFile}" default-value=
	 *            "${project.build.directory}/generated-impex/xml/KS_DB_VERSION.xml"
	 * @required
	 */
	private File newVersionXMLFile;

	/**
	 * The XML file that contains the old information
	 * 
	 * @parameter expression="${oldVersionXMLFile}" default-value=
	 *            "${project.build.directory}/generated-impex/xml/KS_DB_VERSION.xml"
	 * @required
	 */
	private File oldVersionXMLFile;

	protected void beforeExecution() {
		setNewFile(newVersionXMLFile);
		setOldFile(oldVersionXMLFile);
	}

	@Override
	protected boolean isMorphNeeded() {
		// Only reason
		if (!getOldFile().exists()) {
			getLog().debug("file:" + getOldFile().getAbsolutePath() + " does not exist");
			return false;
		} else {
			return true;
		}
	}

	@Override
	protected void executeMojo() throws MojoExecutionException {
		getLog().info("------------------------------------------------------------------------");
		getLog().info("Converting version table XML file");
		getLog().info("------------------------------------------------------------------------");
		try {
			FileUtils.forceMkdir(new File(FileUtils.getPath(getNewFile().getAbsolutePath())));
			MorphRequest request = new FileMorphRequest(getOldFile(), getNewFile());
			request.setName(getOldFile().getName());
			request.setEncoding(getEncoding());
			Morpher morpher = getMorpher(request, getProject().getArtifactId());
			morpher.executeMorph();
		} catch (IOException e) {
			throw new MojoExecutionException("Unexpected error while attempting to morph " + getOldFile().getAbsolutePath(), e);
		}
	}

	protected Morpher getMorpher(MorphRequest request, String artifactId) {
		VersionTableMorpher morpher = new VersionTableMorpher(request, artifactId);
		morpher.setProjectVersion(getProject().getVersion());
		return morpher;
	}

	/**
	 * @return the newVersionXMLFile
	 */
	public File getNewVersionXMLFile() {
		return newVersionXMLFile;
	}

	/**
	 * @param newVersionXMLFile
	 *            the newVersionXMLFile to set
	 */
	public void setNewVersionXMLFile(final File newVersionXMLFile) {
		this.newVersionXMLFile = newVersionXMLFile;
	}

	/**
	 * @return the oldVersionXMLFile
	 */
	public File getOldVersionXMLFile() {
		return oldVersionXMLFile;
	}

	/**
	 * @param oldVersionXMLFile
	 *            the oldVersionXMLFile to set
	 */
	public void setOldVersionXMLFile(final File oldVersionXMLFile) {
		this.oldVersionXMLFile = oldVersionXMLFile;
	}
}
