package org.apache.torque.mojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
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
	boolean override = true;
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

	public boolean exists() {
		return utils.isFileOrResource(location);
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
			properties.putAll(System.getProperties());
			Set<String> keys = properties.stringPropertyNames();
			Map<String, Object> description = BeanUtils.describe(bean);
			for (String key : keys) {
				Object value = description.get(key);
				if (value != null && !isOverride()) {
					// The property is already set, don't override it unless they have asked us to
					log.debug("Skipping property " + key + " it is already set to " + value);
					continue;
				}
				Set<String> beanProperties = description.keySet();
				if (!beanProperties.contains(key)) {
					// This is not a property of the bean
					log.debug("Skipping property " + key + " as it is not a property of this bean");
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

	protected String getLogValue(String key, String value) {
		int pos = key.toLowerCase().indexOf("password");
		if (pos == -1) {
			return value;
		} else {
			return StringUtils.repeat("*", value.length());
		}
	}

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

	protected Reader getReader() throws FileNotFoundException, UnsupportedEncodingException, IOException {
		File file = new File(location);
		if (file.exists()) {
			return new InputStreamReader(new FileInputStream(file), getEncoding());
		}
		ResourceLoader loader = new DefaultResourceLoader();
		Resource resource = loader.getResource(location);
		return new InputStreamReader(resource.getInputStream(), getEncoding());
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

	public boolean isOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
