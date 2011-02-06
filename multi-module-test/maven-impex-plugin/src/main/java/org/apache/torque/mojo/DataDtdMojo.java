package org.apache.torque.mojo;

import java.io.File;
import java.io.IOException;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.torque.task.TorqueDataModelTask;
import org.kuali.core.db.torque.Utils;

/**
 * Generates a DTD for the database tables from a schema XML file
 *
 * @goal datadtd
 * @phase generate-sources
 */
public class DataDtdMojo extends DataModelTaskMojo {
    /** The context property for the name of the project. */
    public static final String PROJECT_CONTEXT_PROPERTY = "project";

    /**
     * @parameter expression="${antCompatibilityMode}" antCompatibilityMode="false"
     */
    boolean antCompatibilityMode;

    /**
     * Only used if antCompatibilityMode is set to true. If so, the dtd that gets generated will be copied to
     *
     * @parameter expression="${copyToFile}" default-value="${basedir}/src/main/impex/data.dtd"
     */
    String copyToFile;

    /**
     * Included here as a simple property to facilitate generating DTD's for other artifacts
     *
     * @parameter expression="${artifactId}" default-value="${project.artifactId}"
     */
    String artifactId;

    /**
     * The directory in which the DTD will be generated
     *
     * @parameter property="outputDir" expression="${outputDir}"
     * default-value="${project.build.directory}/generated-impex"
     */
    @SuppressWarnings("unused")
    private String dummy1;

    /**
     * The location where the report file will be generated, relative to outputDir.
     *
     * @parameter property="reportFile" expression="${reportFile}"
     * default-value="../reports/report.${project.artifactId}.datadtd.generation"
     */
    @SuppressWarnings("unused")
    private String dummy2;

    /**
     * The location where the context property file for velocity will be generated.
     *
     * @parameter property="contextPropertiesPath" expression="${contextPropertiesPath}"
     * default-value="${project.build.directory}/reports/context.datadtd.properties"
     */
    @SuppressWarnings("unused")
    private String dummy3;

    /**
     * The name of the project
     *
     * @parameter expression="${projectName}" default-value="impex"
     */
    private String projectName;

    /**
     * The name of the schema.xml file to process
     *
     * @parameter expression="${schemaXMLFile}"
     * default-value="${project.build.directory}/generated-impex/${project.artifactId}.xml"
     * @required
     */
    private String schemaXMLFile;

    /**
     * Returns the context properties for the Texen task.
     *
     * @return The PropertiesConfiguration containing all context properties, not null.
     */
    @Override
    protected PropertiesConfiguration getMojoContextProperties() {
        PropertiesConfiguration configuration = new PropertiesConfiguration();
        configuration.addProperty(PROJECT_CONTEXT_PROPERTY, getProjectName());
        configuration.addProperty("version", getProject().getVersion());
        return configuration;
    }

    protected void showConfig() {
        getLog().info("Schema XML File: " + schemaXMLFile);
        getLog().info("Ant Compatibility Mode: " + antCompatibilityMode);
        getLog().info("Output Directory: " + getOutputDir());
    }

    /**
     * Configures the Texen task wrapped by this mojo
     */
    @Override
    protected void configureTask() throws MojoExecutionException {
        TorqueDataModelTask task = new TorqueDataModelTask();
        setAntTask(task);
        super.configureTask();
        boolean exists = new Utils().isFileOrResource(getSchemaXMLFile());
        if (!exists) {
            throw new MojoExecutionException("Unable to locate: " + getSchemaXMLFile());
        }
        task.setXmlFile(getSchemaXMLFile());

    }

    /**
     * Returns the path to the control template.
     *
     * @return "data/Control.vm"
     */
    @Override
    protected String getControlTemplate() {
        return "data/Control.vm";
    }

    /**
     * Returns the name of the project
     *
     * @return the name of the project.
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Sets the name of the project
     *
     * @param project
     * the name of the project.
     */
    public void setProjectName(final String projectName) {
        this.projectName = projectName;
    }

    /**
     * Returns the name of the xml file to process.
     *
     * @return the name of the xml file to process.
     */
    public String getSchemaXMLFile() {
        return schemaXMLFile;
    }

    /**
     * Sets the name of the xml file to process.
     *
     * @param project
     * the name of the xml file to process.
     */
    public void setSchemaXMLFile(final String xmlFile) {
        this.schemaXMLFile = xmlFile;
    }

    @Override
    public void executeMojo() throws MojoExecutionException {
        getLog().info("------------------------------------------------------------------------");
        getLog().info("Generating database DTD");
        getLog().info("------------------------------------------------------------------------");
        showConfig();
        super.executeMojo();
        if (antCompatibilityMode) {
            File srcFile = new File(getOutputDir() + FS + getArtifactId() + ".dtd");
            File dstFile = new File(copyToFile);
            getLog().info("Creating " + dstFile.getAbsolutePath() + " for Ant compatibility mode");
            try {
                FileUtils.copyFile(srcFile, dstFile);
            } catch (IOException e) {
                throw new MojoExecutionException("Error copying file", e);
            }
        }
    }

    public boolean isAntCompatibilityMode() {
        return antCompatibilityMode;
    }

    public void setAntCompatibilityMode(final boolean antCompatibilityMode) {
        this.antCompatibilityMode = antCompatibilityMode;
    }

    public String getCopyToFile() {
        return copyToFile;
    }

    public void setCopyToFile(final String copyToFile) {
        this.copyToFile = copyToFile;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(final String artifactId) {
        this.artifactId = artifactId;
    }
}
