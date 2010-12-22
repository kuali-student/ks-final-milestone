package org.apache.torque.mojo;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.torque.mojo.morph.MorphRequest;
import org.apache.torque.mojo.morph.Morpher;
import org.apache.torque.mojo.morph.VersionTableMorpher;

/**
 * Morph the xml inside KS_DB_VERSION.xml so it has Maven friendly placeholders
 * for version info
 * 
 * @goal morphversiontable
 * @phase generate-sources
 */
public class MorphVersionTableMojo extends AbstractMorphSingleMojo {

	/**
	 * The XML file containing version information
	 * 
	 * @parameter expression="${newVersionXMLFile}" default-value=
	 *            "${project.build.directory}/generated-impex/xml/KS_DB_VERSION.xml"
	 * @required
	 */
	private File newVersionXMLFile;

	/**
	 * The path to the directory where the schema XML files are located
	 * 
	 * @parameter expression="${oldVersionXMLFile}"
	 *            default-value="${basedir}/src/main/impex/KS_DB_VERSION.xml"
	 * @required
	 */
	private File oldVersionXMLFile;

	protected void beforeExecution() {
		setNewFile(newVersionXMLFile);
		setOldFile(oldVersionXMLFile);
	}

	@Override
	protected void executeMojo() throws MojoExecutionException {
		getLog().info("------------------------------------------------------------------------");
		getLog().info("Converting version table XML file");
		getLog().info("------------------------------------------------------------------------");
		super.executeMojo();
	}

	protected Morpher getMorpher(MorphRequest request, String artifactId) {
		return new VersionTableMorpher(request, artifactId);
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
