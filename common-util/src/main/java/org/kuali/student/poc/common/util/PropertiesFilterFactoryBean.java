package org.kuali.student.poc.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.ClassPathResource;

/**
 * A really simple properties filter that returns a properties object for a
 * subset of a properties file. Give this method the file name of the properties
 * file, and then give it the prefix to filter on. The prefix gets a "."
 * appended to it on filtering.
 * <p>
 * For example, if the properties are:
 * <ul>
 * <li>prop1.test1=value1</li>
 * <li>prop1.test2=value2</li>
 * <li>prop2.test1=value1</li>
 * <li>prop1butnottherealprop1.test3=value3</li>
 * </ul>
 * Then filtering on "prop1" returns a Properties object with values:
 * <ul>
 * <li>test1=value1</li>
 * <li>test2=value2</li>
 * </ul>
 * </p>
 * 
 * @author Kuali Student Team (ks.team2@kuali.org)
 */
public class PropertiesFilterFactoryBean implements FactoryBean {

	private static final String CLASSPATH_PREFIX = "classpath:";

	private static final String placeholderPrefix = "${";

	private static final String placeholderSuffix = "}";

	private String propertyFile;
	private String prefix;

	@Override
	public Object getObject() throws Exception {

		InputStream stream;

		if (propertyFile.startsWith(CLASSPATH_PREFIX)) {
			ClassPathResource resource = new ClassPathResource(propertyFile
					.substring(CLASSPATH_PREFIX.length()));
			stream = resource.getInputStream();
		} else {
			stream = new FileInputStream(propertyFile);
		}

		Properties inProperties = new Properties();
		try {
			inProperties.load(stream);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
				}
			}
		}

		Properties outProperties = new Properties();
		int prefixLength = prefix.length();
		for (String key : inProperties.stringPropertyNames()) {
			if (key.startsWith(prefix)) {
				String stringValue = resolvePlaceholder(key, inProperties);
				String resolvedProperty = this.parseStringValue(stringValue, inProperties, new HashSet<String>());
				outProperties.setProperty(key.substring(prefixLength),
						resolvedProperty);
			}
		}

		return outProperties;
	}

	@Override
	public Class<?> getObjectType() {
		return Properties.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public String getPropertyFile() {
		return propertyFile;
	}

	/**
	 * @param propertyFile
	 *            the filename of the properties file, either a file system path
	 *            or classpath:path
	 */
	public void setPropertyFile(String propertyFile) {
		this.propertyFile = propertyFile;
	}

	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix
	 *            The prefix to filter on, a '.' will be appended.
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix + ".";
	}

	//Pilfered from PropertyPlaceholderConfigurer
	/**
	 * Parse the given String value recursively, to be able to resolve nested
	 * placeholders (when resolved property values in turn contain placeholders
	 * again).
	 * 
	 * @param strVal
	 *            the String value to parse
	 * @param props
	 *            the Properties to resolve placeholders against
	 * @param visitedPlaceholders
	 *            the placeholders that have already been visited during the
	 *            current resolution attempt (used to detect circular references
	 *            between placeholders). Only non-null if we're parsing a nested
	 *            placeholder.
	 * @throws BeanDefinitionStoreException
	 *             if invalid values are encountered
	 * @see #resolvePlaceholder(String, java.util.Properties, int)
	 */
	protected String parseStringValue(String strVal, Properties props,
			Set<String> visitedPlaceholders) throws BeanDefinitionStoreException {

		StringBuffer buf = new StringBuffer(strVal);

		int startIndex = strVal.indexOf(PropertiesFilterFactoryBean.placeholderPrefix);
		while (startIndex != -1) {
			int endIndex = buf.indexOf(PropertiesFilterFactoryBean.placeholderSuffix, startIndex
					+ PropertiesFilterFactoryBean.placeholderPrefix.length());
			if (endIndex != -1) {
				String placeholder = buf.substring(startIndex
						+ PropertiesFilterFactoryBean.placeholderPrefix.length(), endIndex);
				if (!visitedPlaceholders.add(placeholder)) {
					throw new BeanDefinitionStoreException(
							"Circular placeholder reference '" + placeholder
									+ "' in property definitions");
				}
				String propVal = resolvePlaceholder(placeholder, props);
				if (propVal != null) {
					// Recursive invocation, parsing placeholders contained in
					// the
					// previously resolved placeholder value.
					propVal = parseStringValue(propVal, props,
							visitedPlaceholders);
					buf.replace(startIndex, endIndex
							+ PropertiesFilterFactoryBean.placeholderSuffix.length(), propVal);
					startIndex = buf.indexOf(PropertiesFilterFactoryBean.placeholderPrefix, startIndex
							+ propVal.length());
				} else {
					// Proceed with unprocessed value.
					startIndex = buf.indexOf(PropertiesFilterFactoryBean.placeholderPrefix, endIndex
							+ PropertiesFilterFactoryBean.placeholderSuffix.length());

				}
				visitedPlaceholders.remove(placeholder);
			} else {
				startIndex = -1;
			}
		}

		return buf.toString();
	}

	//Pilfered from PropertyPlaceholderConfigurer
	/**
	 * Resolve the given key as JVM system property, and optionally also as
	 * system environment variable if no matching system property has been
	 * found.
	 * 
	 * @param key
	 *            the placeholder to resolve as system property key
	 * @return the system property value, or <code>null</code> if not found
	 * @see #setSearchSystemEnvironment
	 * @see java.lang.System#getProperty(String)
	 * @see java.lang.System#getenv(String)
	 */
	protected String resolvePlaceholder(String key, Properties props) {
		try {
			String value = props.getProperty(key);
			if (value == null) {
				value = System.getProperty(key);
			}
			if (value == null) {
				value = System.getenv(key);
			}
			return value;
		} catch (Throwable ex) {
			return null;
		}
	}

}
