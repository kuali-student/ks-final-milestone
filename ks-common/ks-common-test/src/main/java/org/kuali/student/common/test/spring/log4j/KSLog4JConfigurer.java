/*
 * Copyright 2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.common.test.spring.log4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Log4jConfigurer;

import java.io.FileNotFoundException;

/**
 * 
 * When used within a <b>static</b> code block in a unit test it allows the
 * developer to override the log4j.properties used when executing the test.
 * 
 * This is useful because for performance reasons running tests in jenkins use
 * the <b>WARN</b> log level. But when developing a test seeing <b>DEBUG</b>
 * level events is very useful.
 * 
 * @author Kuali Student Team
 * 
 */
public final class KSLog4JConfigurer {

    private KSLog4JConfigurer() {
        // intentionally private
    }

    /**
     * Initialize the org.slf4j.Logger for the given class.
     * 
     * If a System Property of <em>log4j.properties</em> exists that file will be used to configure <b>log4j</b>
     * 
     * @param clazz the class to configure the logger for.
     * 
     * @return the initialized org.slf4j.Logger for the class specified.
     */
    public static final Logger getLogger(Class<?> clazz) {
        
        return getLogger(clazz, "log4j.properties");
    }

    /**
     * Initialize the org.slf4j.Logger for the given class.
     * 
     * If a System Property of the name given by the systemPropertyName parameter exists that file will be used to configure <b>log4j</b>
     * 
     * @param clazz the class to configure the logger for.
     * @param systemPropertyName The name of the system property to look for to contain the absolute path to the alternate log4j.properties file.
     * @return the initialized org.slf4j.Logger for the class specified.
     */
    public static final Logger getLogger(Class<?> clazz,
            String systemPropertyName) {

        String log4jconfig = System.getProperty(systemPropertyName);

        boolean exception = false;

        if (log4jconfig != null)
            try {
                Log4jConfigurer.initLogging(log4jconfig, 35);
            } catch (FileNotFoundException e) {

                exception = true;
            }

        Logger log = LoggerFactory.getLogger(clazz);

        if (exception)
            log.error("Missing Configuration File: " + log4jconfig
                    + ", For system property: " + systemPropertyName);
        
        return log;

    }

}
