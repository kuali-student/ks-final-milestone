/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * A really freaking simple properties filter that returns a properties map for a subset of a properties file. Give this
 * method the file name of the properties file, and then give it the prefix to filter on. The prefix gets a "." appended to
 * it on filtering.
 * <p>
 * For example, if the properties are:
 * <ul>
 * <li>prop1.test1.value1</li>
 * <li>prop1.test2.value2</li>
 * <li>prop2.test1.value1</li>
 * <li>prop1butnottherealprop1.test3.value3</li>
 * </ul>
 * Then filtering on "prop1" returns a map with values:
 * <ul>
 * <li>test1.value1</li>
 * <li>test2.value2</li>
 * </ul>
 * </p>
 * 
 * @author Kuali Student Team (ks.team2@kuali.org)
 */
public class PropertiesFactory {

    Properties properties = new Properties();

    /**
     * This constructs a PropertiesFactory.
     * 
     * @param filename
     *            the filename of the properties file as determined by {@link ClassLoader#getSystemResource(String)}.
     * @throws IOException
     */
    public PropertiesFactory(String filename) throws IOException {
        this(ClassLoader.getSystemResource(filename).openStream());
    }

    /**
     * This constructs a PropertiesFactory.
     * 
     * @param file
     *            the properties file
     * @throws FileNotFoundException
     * @throws IOException
     */
    public PropertiesFactory(File file) throws FileNotFoundException, IOException {
        this(new FileInputStream(file));
    }

    /**
     * This constructs a PropertiesFactory.
     * 
     * @param in
     *            an InputStream to read the properties from
     * @throws IOException
     */
    public PropertiesFactory(InputStream in) throws IOException {
        try {
            properties.load(in);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {}
            }
        }
    }

    /**
     * This method filters the properties based on the prefix given as described above. The prefix will be appended with a
     * "." delimiter and the returned values will not have either the prefix or the delimiter.
     * 
     * @param prefix
     *            the prefix to filter on
     * @return a map of the properties minus their prefix. This list is really String,Object, oh well.
     */
    public Map<Object, Object> getProperties(String prefix) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        for (Object key : properties.keySet()) {
            String realPrefix = prefix + ".";
            String keyString = key.toString();
            if (keyString.startsWith(realPrefix)) {
                map.put(keyString.substring(realPrefix.length()), properties.get(key));
            }
        }

        return map;
    }

    /**
     * A static version of the properties filter. <code><pre>
     *     &lt;bean id=&quot;test&quot; class=&quot;org.kuali.student.common.util.PropertiesFactory&quot; factory-method=&quot;getProperties&quot; &gt;
     *         &lt;constructor-arg value=&quot;file.properties&quot;/&gt;
     *         &lt;constructor-arg value=&quot;prefix1&quot;/&gt;
     *     &lt;/bean&gt;
     * </pre></code>
     * 
     * @param filename
     * @param prefix
     *            the prefix to filter on
     * @return a map of the properties minus their prefix. This list is really String,Object, oh well.
     * @throws IOException
     */
    public static Map<Object, Object> getProperties(String filename, String prefix) throws IOException {
        Properties props = new Properties();
        InputStream in = null;
        URL propFile = ClassLoader.getSystemResource(filename);
        // System.out.println("\t===PROPFACT: "+propFile);
        try {
            props.load(in = propFile.openStream());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {}
            }
        }

        Map<Object, Object> map = new HashMap<Object, Object>();
        for (Object key : props.keySet()) {
            String realPrefix = prefix + ".";
            String keyString = key.toString();
            if (keyString.startsWith(realPrefix)) {
                map.put(keyString.substring(realPrefix.length()), props.get(key));
            }
        }

        return map;
    }

}
