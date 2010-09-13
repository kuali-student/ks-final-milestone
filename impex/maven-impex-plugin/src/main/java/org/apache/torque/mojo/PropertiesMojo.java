package org.apache.torque.mojo;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.kuali.core.db.torque.PropertyHandlingException;

public class PropertiesMojo extends BaseMojo {

	/**
	 * @parameter expression="${properties}"
	 */
	String properties;

	/**
	 * @parameter expression="${propertiesDescription}"
	 */
	String propertiesDescription;

	/**
	 * @parameter expression="${overridePluginConfiguration}" default-value="true"
	 */
	boolean overridePluginConfiguration;

	/**
	 * @parameter expression="${overrideSystemProperties}" default-value="false"
	 */
	boolean overrideSystemProperties;

	@Override
	protected void executeMojo() throws MojoExecutionException {
		try {
			BeanPropertiesLoader loader = new BeanPropertiesLoader(this, properties, getEncoding(), propertiesDescription);
			loader.setOverrideExistingPropertyValues(overridePluginConfiguration);
			loader.setOverrideSystemProperties(overrideSystemProperties);
			if (!StringUtils.isEmpty(properties) && !loader.isPropertiesExist()) {
				getLog().warn("Unable to locate properties at " + properties);
				return;
			}
			getLog().info("Loading properties from " + properties);
			loader.loadToBean();
		} catch (PropertyHandlingException e) {
			throw new MojoExecutionException("Error handling properties", e);
		}
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public String getPropertiesDescription() {
		return propertiesDescription;
	}

	public void setPropertiesDescription(String propertiesDescription) {
		this.propertiesDescription = propertiesDescription;
	}

	public boolean isOverridePluginConfiguration() {
		return overridePluginConfiguration;
	}

	public void setOverridePluginConfiguration(boolean overridePluginConfiguration) {
		this.overridePluginConfiguration = overridePluginConfiguration;
	}

	public boolean isOverrideSystemProperties() {
		return overrideSystemProperties;
	}

	public void setOverrideSystemProperties(boolean overrideSystemProperties) {
		this.overrideSystemProperties = overrideSystemProperties;
	}
}
