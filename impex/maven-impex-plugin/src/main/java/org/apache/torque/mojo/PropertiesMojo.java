package org.apache.torque.mojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.torque.util.BeanPropertiesLoader;
import org.kuali.core.db.torque.PropertyHandlingException;

public class PropertiesMojo extends BaseMojo {

	/**
	 * Optional file containing Impex properties. See also <code>overridePluginConfiguration</code> and
	 * <code>overrideSystemProperties</code><br>
	 * <b>Default value is:</b> <code>${user.home}/impex.properties</code>
	 * 
	 * @parameter expression="${properties}"
	 */
	String properties = System.getProperty("user.home") + FS + "impex.properties";

	/**
	 * If true, properties found in the file will override properties specified in the plugin configuration
	 * 
	 * @parameter expression="${overridePluginConfiguration}" default-value="true"
	 */
	boolean overridePluginConfiguration;

	/**
	 * If true, properties found in the file will override properties specified as System properties
	 * 
	 * @parameter expression="${overrideSystemProperties}" default-value="false"
	 */
	boolean overrideSystemProperties;

	@Override
	protected void executeMojo() throws MojoExecutionException {
		loadPropertiesToMojo();
	}

	protected void loadPropertiesToMojo() throws MojoExecutionException {
		try {
			BeanPropertiesLoader loader = new BeanPropertiesLoader(this, properties, getEncoding(), "Impex");
			loader.setOverrideExistingPropertyValues(overridePluginConfiguration);
			loader.setOverrideSystemProperties(overrideSystemProperties);
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
