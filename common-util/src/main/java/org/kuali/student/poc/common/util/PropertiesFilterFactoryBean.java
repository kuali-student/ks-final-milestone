package org.kuali.student.poc.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.ClassPathResource;

/**
 * A really simple properties filter that returns a properties object for a
 * subset of a properties file. Give this method the file name of the
 * properties file, and then give it the prefix to filter on. The prefix gets
 * a "." appended to it on
 * filtering.
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

    private String propertyFile;
    private String prefix;

    @Override
    public Object getObject() throws Exception {

        InputStream stream;

        if (propertyFile.startsWith(CLASSPATH_PREFIX)) {
            ClassPathResource resource = new ClassPathResource(propertyFile.substring(CLASSPATH_PREFIX.length()));
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
                } catch (IOException e) {}
            }
        }

        Properties outProperties = new Properties();
        int prefixLength = prefix.length();
        for (String key : inProperties.stringPropertyNames()) {
            if (key.startsWith(prefix)) {
                outProperties.setProperty(key.substring(prefixLength), inProperties.getProperty(key));
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
     *            the filename of the properties file, either a file system path or classpath:path
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

}
