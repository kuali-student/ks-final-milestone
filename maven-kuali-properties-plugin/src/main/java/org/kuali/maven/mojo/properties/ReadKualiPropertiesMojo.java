package org.kuali.maven.mojo.properties;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.project.MavenProject;
import org.kuali.rice.core.config.ConfigContext;
import org.kuali.rice.core.config.JAXBConfigImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * The read-properties goal reads property files in the Kuali format and stores them as project properties.
 * 
 * @goal read-properties
 */
public class ReadKualiPropertiesMojo extends AbstractMojo {

	/**
	 * @parameter default-value="${project}"
	 * @required
	 * @readonly
	 */
	MavenProject project;

	/**
	 * The properties files that will be used when reading properties.
	 * 
	 * @parameter expression="${files}"
	 */
	File[] files;

	/**
	 * The locations that will be used when reading properties.
	 * 
	 * @parameter expression="${locations}"
	 */
	String[] locations;

	/**
	 * If set to true then system properties will always be checked first, disregarding any values in the properties
	 * files. The default is false.
	 * 
	 * @parameter expression="${systemOverride}" default-value="false"
	 */
	boolean systemOverride;

	/**
	 * When <code>true</code>, skip the execution.
	 * 
	 * @since 1.0
	 * @parameter default-value="false"
	 */
	boolean skip;

	/**
	 * Setting this parameter to <code>true</code> will force the execution of this mojo, even if it would get skipped
	 * usually.
	 * 
	 * @parameter expression="${forceMojoExecution}" default-value=false
	 * @required
	 */
	boolean forceMojoExecution;

	/**
	 * <p>
	 * Determine if the mojo execution should get skipped.
	 * </p>
	 * This is the case if:
	 * <ul>
	 * <li>{@link #skip} is <code>true</code></li>
	 * <li>if the mojo gets executed on a project with packaging type 'pom' and {@link #forceMojoExecution} is
	 * <code>false</code></li>
	 * </ul>
	 * 
	 * @return <code>true</code> if the mojo execution should be skipped.
	 */
	protected boolean skipMojo() {
		if (skip) {
			getLog().info("Skip execution");
			return true;
		}

		if (!forceMojoExecution && project != null && "pom".equals(project.getPackaging())) {
			getLog().info("Skipping execution for project with packaging type 'pom'");
			return true;
		}

		return false;
	}

	protected List<String> getList() {
		List<String> list = new ArrayList<String>();
		if (getFiles() != null) {
			for (File file : getFiles()) {
				list.add(file.getAbsolutePath());
			}
		}
		if (getLocations() != null) {
			for (String location : getLocations()) {
				list.add(location);
			}
		}
		return list;
	}

	public void execute() throws MojoExecutionException {
		MavenLogAppender.startPluginLog(this);
		if (skipMojo()) {
			return;
		}
		List<String> locationsList = getList();
		if (locationsList.size() == 0) {
			return;
		}
		for (String location : locationsList) {
			getLog().info(location);
		}
		Properties baseProps = new Properties();
		baseProps.putAll(project.getProperties());
		baseProps.putAll(System.getProperties());
		JAXBConfigImpl config = new JAXBConfigImpl(locationsList, baseProps);
		config.setSystemOverride(isSystemOverride());
		try {
			config.parseConfig();
		} catch (IOException e) {
			throw new MojoExecutionException("Error parsing config", e);
		}
		ConfigContext.init(config);
		Properties properties = config.getProperties();
		List<String> list = new ArrayList<String>();
		for (String s : properties.stringPropertyNames()) {
			list.add(s + "=" + properties.getProperty(s));
		}
		Collections.sort(list);
		for (String s : list) {
			getLog().info(s);
		}
		config.getProperty("funky");
		MavenLogAppender.endPluginLog(this);
	}

	public File[] getFiles() {
		return files;
	}

	public void setFiles(File[] files) {
		this.files = files;
	}

	public String[] getLocations() {
		return locations;
	}

	public void setLocations(String[] locations) {
		this.locations = locations;
	}

	public MavenProject getProject() {
		return project;
	}

	public boolean isSystemOverride() {
		return systemOverride;
	}

	public void setSystemOverride(boolean systemOverride) {
		this.systemOverride = systemOverride;
	}
}
