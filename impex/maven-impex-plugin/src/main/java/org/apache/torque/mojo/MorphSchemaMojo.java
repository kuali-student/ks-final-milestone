package org.apache.torque.mojo;

import org.apache.maven.plugin.MojoExecutionException;

/**
 * Converts Ant impex data XML files into maven-impex-plugin data XML filse
 * 
 * @goal morphschema
 * @phase generate-sources
 */
public class MorphSchemaMojo extends MorpherMojo {
	/**
	 * The path to the directory where the schema XML files are located
	 * 
	 * @parameter expression="${schemaXMLFile}"
	 *            default-value="${basedir}/src/main/impex/schema.xml"
	 * @required
	 */
	private String schemaXMLFile;

	/**
	 * The directory in which the SQL will be generated.
	 * 
	 * @parameter expression="${outputDir}"
	 *            default-value="${project.build.directory}/generated-impex"
	 * @required
	 */
	private String outputDir;

	@Override
	public void execute() throws MojoExecutionException {
		getLog().info("------------------------------------------------------------------------");
		getLog().info("Converting schema XML file");
		getLog().info("------------------------------------------------------------------------");
		new SchemaMorpher().morphSchema(getProject(), getLog());
	}

	public String getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public String getSchemaXMLFile() {
		return schemaXMLFile;
	}

	public void setSchemaXMLFile(String schemaXMLFile) {
		this.schemaXMLFile = schemaXMLFile;
	}
}
