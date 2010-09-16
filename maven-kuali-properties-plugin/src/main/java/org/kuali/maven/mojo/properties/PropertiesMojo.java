package org.kuali.maven.mojo.properties;

import org.apache.maven.plugin.MojoExecutionException;

/**
 * @goal properties
 */
public class PropertiesMojo extends BaseMojo {

	/**
	 * The format the properties files are in. Valid values are STANDARD, XML, KUALI
	 * 
	 * @parameter expression="${format}" default-value="STANDARD"
	 */
	String format;

	/**
	 * The location of a properties file to load. This can be a path on the file system, or a Spring style resource
	 * location eg <code>classpath:my.properties</code>
	 * 
	 * @parameter expression="${location}" default-value="${user.home}/${project.artifactId}.properties"
	 */
	String location;

	/**
	 * If set to true, values supplied in the properties files will override system properties.
	 * 
	 * @parameter expression="${overrideSystemProperties}" default-value="false"
	 */
	boolean overrideSystemProperties;

	public void executeMojo() throws MojoExecutionException {
	}

	public boolean isOverrideSystemProperties() {
		return overrideSystemProperties;
	}

	public void setOverrideSystemProperties(boolean overrideSystemProperties) {
		this.overrideSystemProperties = overrideSystemProperties;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
