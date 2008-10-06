package org.kuali.student.poc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.ClassPathResource;

/**
 * Returns a properties object for properties with a specified prefix.
 * to read from only one property file:
 * @see org.kuali.student.poc.common.util.PropertiesFilterFactoryBean
 * 
 * This isn't a subclass because it may replace PropertiesFilterFactoryBean
 * 
 * It is not in org.kuali.student.poc.common.util to save rebuilding the common jar
 * that was released with the poc. @FIX_ME it should be copied to ...common.util
 *  
 * If an overrideProperty file is specified it will be read, if not the
 * default properties location will be read.
 * Because properties with a given prefix can be implementation specific
 * properties are not mixed as they are with
 * @see org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
 * 
 * Set either defaultPropertyFile or overridePropertyFile or both to the 
 * file name, file URL or classpath location of the properties file
 * 
 * Set the prefix to filter on. A "." is appended to it on filtering.
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
  
  @author gstruthers@berkeley.edu
 */
public class PropertiesOverrideFilterFactoryBean implements FactoryBean {

	private static final String CLASSPATH_PREFIX = "classpath:";

	private static final String FILEURL_PREFIX = "file:";

	private String defaultPropertyFile;		// classpath or implementation independent 
	private String overridePropertyFile;	// implementation specific
	private String prefix;					// property prefix to filter on

	/**
	 * @param propertyFile 
	 * examples 
	 * classpath:application.properties
	 * file:/usr/local/ks-poc/config/application.properties
	 * @return InputStream to a property file, NULL if can't be opened
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private InputStream createStream(String propertyFile) throws IOException, URISyntaxException {
		InputStream stream = null;

		if (propertyFile.startsWith(CLASSPATH_PREFIX)) {
			ClassPathResource resource = new ClassPathResource(propertyFile
					.substring(CLASSPATH_PREFIX.length()));
			stream = resource.getInputStream();
		} else if(propertyFile.toLowerCase().startsWith(FILEURL_PREFIX)){
			URL url = new URL(propertyFile);
			File file = new File(url.toURI());
			if(file.canRead()) {
				stream = url.openConnection().getInputStream();
			}
	    } else {
	    	File file = new File(propertyFile);
			if(file.canRead()) {
				stream = new FileInputStream(propertyFile);
			}
		}
		return stream;
	}
	/* Try to open both property file locations. Read override if it's there.
	 * Throw exception if neither can be read.
	 * Then create Properties that have specified prefix.
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	@Override
	public Object getObject() throws Exception {

		InputStream defaultInStream = createStream(defaultPropertyFile);
		InputStream overrideInStream = createStream(overridePropertyFile);
		InputStream inStream = null;
		if(overrideInStream != null) {
			inStream = overrideInStream;
		} else if (defaultInStream != null) {
			inStream = defaultInStream;
		} else {
			throw new Exception("Can't read property file, default file: " + 
					defaultPropertyFile + " override file " + overridePropertyFile);
		}
		Properties inProperties = new Properties();
		try {
			inProperties.load(inStream);
		} finally {
			if (defaultInStream != null) {
				try {
					defaultInStream.close(); 
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (overrideInStream != null) {
				try {
					overrideInStream.close(); 
				} catch (IOException e) {
					e.printStackTrace();
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

	public String getDefaultPropertyFile() {
		return defaultPropertyFile;
	}

	/**
	 * @param defaultPropertyFile
	 *            the filename of the properties file, either a file system path
	 *            or classpath:path
	 */
	public void setDefaultPropertyFile(String propertyFile) {
		this.defaultPropertyFile = propertyFile;
	}

	/**
	 * @return the overridePropertyFile
	 */
	public String getOverridePropertyFile() {
		return overridePropertyFile;
	}

	/**
	 * @param overridePropertyFile the overridePropertyFile to set
	 */
	public void setOverridePropertyFile(String overridePropertyFile) {
		this.overridePropertyFile = overridePropertyFile;
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

	/**
	 * Parse the given String value recursively, to be able to resolve nested
	 * placeholders (when resolved property values in turn contain placeholders
	 * again).
	 * @param strVal
	 * @param props
	 * @param visitedPlaceholders
	 * @return the resolved String
	 */
	protected String parseStringValue(String strVal, Properties props,
			Set<String> visitedPlaceholders) {

		if(strVal!=null&&strVal.indexOf("${")>-1){
			
			String begin = strVal.substring(0, strVal.indexOf("${"));
			String resolveString = strVal.substring(strVal.indexOf("${")+2,strVal.indexOf("}"));
			String end = strVal.substring(strVal.indexOf("}")+1);
			if(!visitedPlaceholders.add(resolveString)){
				return "";
			}
			String propVal = resolvePlaceholder(resolveString, props);
			String parsedString = parseStringValue(begin+propVal+end, props,
					visitedPlaceholders);
			visitedPlaceholders.add(resolveString);
			return parsedString;
		}
		return strVal;
	}

	/**
	 * Resolve the given key as a Property, JVM system property, and optionally also as
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
