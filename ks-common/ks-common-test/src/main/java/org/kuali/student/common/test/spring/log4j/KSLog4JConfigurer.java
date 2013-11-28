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

import org.mortbay.log.Log;
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

    /**
     * Default name for the refresh value property key
     */
    public static final String LOG4J_PROPERTIES_REFRESH = "log4j.properties.refresh";
    
    /**
     * Default name for the log4j.properties property key
     */
    public static final String LOG4J_PROPERTIES = "log4j.properties";

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
        
        return getLogger(clazz, LOG4J_PROPERTIES, LOG4J_PROPERTIES_REFRESH);
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
            String systemPropertyName, String refreshPropertyName) {

        String log4jconfig = System.getProperty(systemPropertyName);
        
        Long refreshInterval;
        
        try {
            refreshInterval = Long.valueOf(System.getProperty(refreshPropertyName));
        } catch (NumberFormatException e1) {
            refreshInterval = null;
        }

        boolean exception = false;

        if (log4jconfig != null) {
            try {
                
                if (refreshInterval != null) {
                    Log4jConfigurer.initLogging(log4jconfig, refreshInterval);
                }
                else {
                    Log4jConfigurer.initLogging(log4jconfig);
                }
                
            } catch (FileNotFoundException e) {

                exception = true;
            }
        }

        Logger log = LoggerFactory.getLogger(clazz);

        if (log.isDebugEnabled()) {
            
            if (refreshInterval != null) 
                log.debug(String.format("Log4jConfigurer.initLogging(log4jconfig=%s, refreshInterval=%s)", log4jconfig, refreshInterval));
            else
                log.debug(String.format("Log4jConfigurer.initLogging(log4jconfig=%s)", log4jconfig));
                
        }
        
        if (exception)
            log.error("Missing Configuration File: " + log4jconfig
                    + ", For system property: " + systemPropertyName);
        
        return log;

    }

}
