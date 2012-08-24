/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

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

	private static final String FILEURL_PREFIX = "file:";

	private String propertyFile;
	private String prefix;

	@Override
	public Object getObject() throws Exception {

		InputStream stream;

		if (propertyFile.startsWith(CLASSPATH_PREFIX)) {
			ClassPathResource resource = new ClassPathResource(propertyFile
					.substring(CLASSPATH_PREFIX.length()));
			stream = resource.getInputStream();
		} else if(propertyFile.toLowerCase().startsWith(FILEURL_PREFIX)){
			URL url = new URL(propertyFile);
			stream = url.openConnection().getInputStream();
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
