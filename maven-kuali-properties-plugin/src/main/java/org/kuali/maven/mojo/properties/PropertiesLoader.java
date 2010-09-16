package org.kuali.maven.mojo.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import org.codehaus.plexus.util.StringUtils;
import org.kuali.rice.core.config.ConfigContext;
import org.kuali.rice.core.config.JAXBConfigImpl;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class PropertiesLoader {
	ResourceLoader loader = new DefaultResourceLoader();
	String format;
	String location;
	boolean overrideSystemProperties;
	Properties contextProperties;
	String encoding;

	protected boolean exists() {
		if (StringUtils.isBlank(location)) {
			return false;
		}
		File file = new File(location);
		if (file.exists()) {
			return true;
		}
		Resource resource = loader.getResource(location);
		return resource.exists();
	}

	/**
	 * Return an InputStream for reading in a properties file. First check the file system to see if the file exists. If
	 * not, return a Reader using Spring Resource loading
	 */
	protected InputStream getInputStream() throws PropertyHandlingException {
		try {
			File file = new File(location);
			if (file.exists()) {
				return new FileInputStream(file);
			}
			Resource resource = loader.getResource(location);
			return resource.getInputStream();
		} catch (Exception e) {
			throw new PropertyHandlingException(e);
		}
	}

	/**
	 * Return a Reader for reading in the properties file.
	 */
	protected Reader getReader() throws PropertyHandlingException {
		try {
			String encoding = getEncoding();
			if (StringUtils.isBlank(encoding)) {
				encoding = System.getProperty("file.encoding");
			}
			return new InputStreamReader(getInputStream(), encoding);
		} catch (Exception e) {
			throw new PropertyHandlingException(e);
		}
	}

	protected Properties getKualiProperties() throws PropertyHandlingException {
		JAXBConfigImpl config = new JAXBConfigImpl(location, contextProperties);
		// Kuali logic overrides system properties by default unless you explicitly ask it not to
		boolean retainSystemProperties = !isOverrideSystemProperties();
		config.setSystemOverride(retainSystemProperties);
		try {
			config.parseConfig();
		} catch (IOException e) {
			throw new PropertyHandlingException(e);
		}
		ConfigContext.init(config);
		return config.getProperties();
	}

	protected Properties getStandardProperties() throws PropertyHandlingException {
		try {
			Reader reader = getReader();
			Properties properties = new Properties();
			properties.load(reader);
			return properties;
		} catch (Exception e) {
			throw new PropertyHandlingException(e);
		}
	}

	protected Properties getXmlProperties() throws PropertyHandlingException {
		try {
			Properties properties = new Properties();
			properties.loadFromXML(getInputStream());
			return properties;
		} catch (Exception e) {
			throw new PropertyHandlingException(e);
		}
	}

	public Properties getProperties() throws PropertyHandlingException {
		if (format.toUpperCase().equals(Format.KUALI.toString())) {
			return getKualiProperties();
		} else if (format.toUpperCase().equals(Format.STANDARD.toString())) {
			return getStandardProperties();
		} else if (format.toUpperCase().equals(Format.XML.toString())) {
			return getXmlProperties();
		} else {
			throw new PropertyHandlingException("Unknown properties format: '" + format + '"');
		}
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isOverrideSystemProperties() {
		return overrideSystemProperties;
	}

	public void setOverrideSystemProperties(boolean overrideSystemProperties) {
		this.overrideSystemProperties = overrideSystemProperties;
	}

	public Properties getContextProperties() {
		return contextProperties;
	}

	public void setContextProperties(Properties contextProperties) {
		this.contextProperties = contextProperties;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public ResourceLoader getLoader() {
		return loader;
	}

	public void setLoader(ResourceLoader loader) {
		this.loader = loader;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}
