package org.apache.torque.mojo;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.torque.mojo.morph.MorphRequest;
import org.apache.torque.mojo.morph.Morpher;
import org.apache.torque.mojo.morph.SchemaMorpher;

/**
 * Convert an Ant impex schema XML file into a maven-impex-plugin schema XML
 * file
 * 
 * @goal morphschema
 * @phase generate-sources
 */
public class MorphSchemaMojo extends AbstractMorphSingleMojo {

	/**
	 * The XML file describing the database schema
	 * 
	 * @parameter expression="${newSchemaXMLFile}" default-value=
	 *            "${project.build.directory}/generated-impex/${project.artifactId}.xml"
	 * @required
	 */
	private File newSchemaXMLFile;

	/**
	 * The path to the directory where the schema XML files are located
	 * 
	 * @parameter expression="${oldSchemaXMLFile}"
	 *            default-value="${basedir}/src/main/impex/schema.xml"
	 * @required
	 */
	private File oldSchemaXMLFile;

	@Override
	protected void beforeExecution() {
		setNewFile(newSchemaXMLFile);
		setOldFile(oldSchemaXMLFile);
	}

	protected void executeMojo() throws MojoExecutionException {
		getLog().info("------------------------------------------------------------------------");
		getLog().info("Converting schema XML file");
		getLog().info("------------------------------------------------------------------------");
		super.executeMojo();
	}

	protected Morpher getMorpher(MorphRequest request, String artifactId) {
		return new SchemaMorpher(request, artifactId);
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
}
