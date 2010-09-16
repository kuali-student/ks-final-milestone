package org.kuali.maven.mojo.properties;

import java.util.Properties;

import org.apache.commons.beanutils.BeanUtils;
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
	 * location eg <code>classpath:my.properties</code><br>
	 * 
	 * @parameter expression="${properties}" default-value="${user.home}/${project.artifactId}.properties"
	 */
	String properties;

	/**
	 * If set to true, values supplied in the properties files will override system properties.
	 * 
	 * @parameter expression="${overrideSystemProperties}" default-value="false"
	 */
	boolean overrideSystemProperties;

	public void executeMojo() throws MojoExecutionException {
		try {
			Properties projectProperties = getProject().getProperties();
			PropertiesLoader loader = new PropertiesLoader();
			loader.setContextProperties(projectProperties);
			loader.setLocation(properties);
			BeanUtils.copyProperties(loader, this);
			Properties properties = loader.getProperties();
			projectProperties.putAll(properties);
		} catch (Exception e) {
			throw new MojoExecutionException("Error handling properties", e);
		}
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

	public String getProperties() {
		return properties;
	}

	public void setProperties(String location) {
		this.properties = location;
	}

}
