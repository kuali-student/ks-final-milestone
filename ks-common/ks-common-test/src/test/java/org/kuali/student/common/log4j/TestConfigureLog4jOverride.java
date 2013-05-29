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
package org.kuali.student.common.log4j;

import org.apache.log4j.LogManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.kuali.student.common.test.spring.log4j.KSLog4JConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test that the KSLog4JConfigurer works to let the log4j.properties to be overridden.
 * 
 * @author Kuali Student Team 
 *
 */
@RunWith (BlockJUnit4ClassRunner.class)
public class TestConfigureLog4jOverride { 
    
    private static Logger log;

    /**
     * 
     */
    public TestConfigureLog4jOverride() {
        
    }
    
    @AfterClass
    public static void afterClass() {
        
        /*
         * Used to go back to the default log4j.properties file so that subsequent tests will not be effected and continue to run at the
         * WARN log level.
         * 
         */
        System.getProperties().remove(KSLog4JConfigurer.LOG4J_PROPERTIES);
        System.getProperties().remove(KSLog4JConfigurer.LOG4J_PROPERTIES_REFRESH);
        
        LogManager.resetConfiguration();
        
        System.setProperty(KSLog4JConfigurer.LOG4J_PROPERTIES, "classpath:log4j.properties");
        
        log = KSLog4JConfigurer.getLogger(TestConfigureLog4jOverride.class);
    }

    @Test
    public void testStandardOverride() {
        
        LogManager.resetConfiguration();
        
        System.setProperty("log4j.configuration,", "src/test/resources/override-log4j-test.properties");
        
        log = LoggerFactory.getLogger(TestConfigureLog4jOverride.class);
        
        Assert.assertTrue(log.isDebugEnabled());
    }
    
    @Test
    public void testBasic() {
        
        System.getProperties().remove(KSLog4JConfigurer.LOG4J_PROPERTIES);
        System.getProperties().remove(KSLog4JConfigurer.LOG4J_PROPERTIES_REFRESH);
        
        LogManager.resetConfiguration();
        
        // setup the log4j override to the log4j properties file we added into the test resources
        // When a user calls the getLogger method they would be setting this via a -D option to the test launcher.
        System.setProperty(KSLog4JConfigurer.LOG4J_PROPERTIES, "classpath:override-log4j-test.properties");
        
        log = KSLog4JConfigurer.getLogger(TestConfigureLog4jOverride.class);
        
        Assert.assertTrue("Failed to use override-log4j-test.properties to set the log level to debugging", log.isDebugEnabled());
        
        log.debug ("at debug level");
    }
    
    @Test
    public void testRefresh() {
        
        System.getProperties().remove(KSLog4JConfigurer.LOG4J_PROPERTIES);
        System.getProperties().remove(KSLog4JConfigurer.LOG4J_PROPERTIES_REFRESH);
        
        LogManager.resetConfiguration();
        
        // setup the log4j override to the log4j properties file we added into the test resources
        // When a user calls the getLogger method they would be setting this via a -D option to the test launcher.
        System.setProperty(KSLog4JConfigurer.LOG4J_PROPERTIES, "classpath:override-log4j-test.properties");
        
        System.setProperty(KSLog4JConfigurer.LOG4J_PROPERTIES_REFRESH, "15");
        
        log = KSLog4JConfigurer.getLogger(TestConfigureLog4jOverride.class);
        
        Assert.assertTrue("Failed to use override-log4j-test.properties to set the log level to debugging", log.isDebugEnabled());
        
        log.debug ("at debug level");
    }
}
