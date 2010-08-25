package org.apache.torque.mojo;

import java.io.File;
import java.io.IOException;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.settings.Settings;

/**
 * Convert an Ant impex schema XML file into a maven-impex-plugin schema XML file
 * 
 * @goal morphschema
 * @phase generate-sources
 */
public class MorphSchemaMojo extends BaseMojo {

	/**
	 * When <code>true</code>, skip the execution.
	 * 
	 * @since 1.0
	 * @parameter expression="${skip}" default-value="false"
	 */
	private boolean skip;

	/**
	 * Setting this parameter to <code>true</code> will force the execution of this mojo, even if it would get skipped
	 * usually.
	 * 
	 * @parameter expression="${forceMojoExecution}" default-value=false
	 * @required
	 */
	private boolean forceMojoExecution;

	/**
	 * @parameter expression="${encoding}" default-value="${project.build.sourceEncoding}"
	 */
	private String encoding;

	/**
	 * @parameter default-value="${settings}"
	 * @required
	 * @since 1.0
	 * @readonly
	 */
	private Settings settings;

	/**
	 * @parameter default-value="${session}"
	 * @required
	 * @readonly
	 */
	private MavenSession mavenSession;

	/**
	 * The XML file describing the database schema
	 * 
	 * @parameter expression="${newSchemaXMLFile}"
	 *            default-value="${project.build.directory}/generated-impex/${project.artifactId}.xml"
	 * @required
	 */
	private File newSchemaXMLFile;

	/**
	 * The path to the directory where the schema XML files are located
	 * 
	 * @parameter expression="${oldSchemaXMLFile}" default-value="${basedir}/src/main/impex/schema.xml"
	 * @required
	 */
	private File oldSchemaXMLFile;

	protected void executeMojo() throws MojoExecutionException {
		getLog().info("------------------------------------------------------------------------");
		getLog().info("Converting schema XML file");
		getLog().info("------------------------------------------------------------------------");
		try {
			MorphRequest request = new MorphRequest(oldSchemaXMLFile, newSchemaXMLFile);
			Morpher morpher = new SchemaMorpher(request, getProject().getArtifactId());
			morpher.executeMorph(getEncoding());
		} catch (IOException e) {
			throw new MojoExecutionException("Unexpected error while attempting to morph " + oldSchemaXMLFile.getAbsolutePath(), e);
		}
	}

	public File getNewSchemaXMLFile() {
		return newSchemaXMLFile;
	}

	public void setNewSchemaXMLFile(File newSchemaXMLFile) {
		this.newSchemaXMLFile = newSchemaXMLFile;
	}

	public File getOldSchemaXMLFile() {
		return oldSchemaXMLFile;
	}

	public void setOldSchemaXMLFile(File oldSchemaXMLFile) {
		this.oldSchemaXMLFile = oldSchemaXMLFile;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public boolean isForceMojoExecution() {
		return forceMojoExecution;
	}

	public void setForceMojoExecution(boolean forceMojoExecution) {
		this.forceMojoExecution = forceMojoExecution;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public MavenSession getMavenSession() {
		return mavenSession;
	}

	public void setMavenSession(MavenSession mavenSession) {
		this.mavenSession = mavenSession;
	}
}
