package org.apache.torque.mojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.core.db.torque.PropertyHandlingException;
import org.kuali.core.db.torque.Utils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class BeanPropertiesLoader {
	private static final Log log = LogFactory.getLog(BeanPropertiesLoader.class);

	Utils utils = new Utils();
	String location;
	String encoding;
	Object bean;
	boolean overrideExistingPropertyValues = true;
	boolean overrideSystemProperties = false;
	String description;

	public BeanPropertiesLoader() {
		this(null, null, null, null);
	}

	public BeanPropertiesLoader(Object bean, String location, String encoding, String description) {
		super();
		this.bean = bean;
		this.location = location;
		this.encoding = encoding;
		this.description = description;
	}

	public boolean isPropertiesExist() {
		return utils.isFileOrResource(location);
	}

	protected boolean isSkip(Map<String, Object> description, String key) {
		Object value = description.get(key);
		if (value != null && !isOverrideExistingPropertyValues()) {
			// The property is already set, don't override it unless they have asked us to
			log.debug("Skipping property " + key + " it is already set to " + value);
			return true;
		}
		Set<String> beanProperties = description.keySet();
		if (!beanProperties.contains(key)) {
			// This is not a property of the bean
			log.debug("Skipping property " + key + " as it is not a property of this bean");
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public void loadToBean() throws PropertyHandlingException {
		if (!utils.isFileOrResource(location)) {
			log.warn("No properties file located at " + location);
			return;
		} else {
			log.info("------------------------------------------------------------------------");
			log.info("Loading " + getDescription() + " properties from " + location);
			log.info("------------------------------------------------------------------------");
		}
		try {
			Properties properties = getProperties();
			if (!overrideSystemProperties) {
				properties.putAll(System.getProperties());
			}
			Set<String> keys = properties.stringPropertyNames();
			Map<String, Object> description = BeanUtils.describe(bean);
			for (String key : keys) {
				if (isSkip(description, key)) {
					continue;
				}
				// Extract the value and set it on the bean
				String newValue = properties.getProperty(key);
				log.info("Setting " + key + "=" + getLogValue(key, newValue));
				BeanUtils.copyProperty(bean, key, newValue);
			}
		} catch (Exception e) {
			throw new PropertyHandlingException(e);
		}
	}

	/**
	 * Don't display password'ish type properties
	 */
	protected String getLogValue(String key, String value) {
		int pos = key.toLowerCase().indexOf("password");
		if (pos == -1) {
			return value;
		} else {
			return StringUtils.repeat("*", value.length());
		}
	}

	/**
	 * Load the properties file into a Properties object
	 */
	public Properties getProperties() throws PropertyHandlingException {
		try {
			Reader reader = getReader();
			Properties properties = new Properties();
			properties.load(reader);
			return properties;
		} catch (Exception e) {
			throw new PropertyHandlingException(e);
		}
	}

	/**
	 * Return a Reader for reading in the properties file. First check the file system to see if the file exists. If
	 * not, return a Reader using Spring Resource loading
	 */
	protected Reader getReader() throws PropertyHandlingException {
		try {
			File file = new File(location);
			if (file.exists()) {
				return new InputStreamReader(new FileInputStream(file), getEncoding());
			}
			ResourceLoader loader = new DefaultResourceLoader();
			Resource resource = loader.getResource(location);
			return new InputStreamReader(resource.getInputStream(), getEncoding());
		} catch (Exception e) {
			throw new PropertyHandlingException(e);
		}
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	public boolean isOverrideExistingPropertyValues() {
		return overrideExistingPropertyValues;
	}

	public void setOverrideExistingPropertyValues(boolean override) {
		this.overrideExistingPropertyValues = override;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isOverrideSystemProperties() {
		return overrideSystemProperties;
	}

	public void setOverrideSystemProperties(boolean overrideSystemProperties) {
		this.overrideSystemProperties = overrideSystemProperties;
	}

}
