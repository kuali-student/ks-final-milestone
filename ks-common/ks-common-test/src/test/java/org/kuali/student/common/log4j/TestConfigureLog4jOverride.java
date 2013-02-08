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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.kuali.student.common.test.spring.log4j.KSLog4JConfigurer;
import org.slf4j.Logger;

/**
 * Test that the KSLog4JConfigurer works to let the log4j.properties to be overridden.
 * 
 * @author Kuali Student Team 
 *
 */
@RunWith (BlockJUnit4ClassRunner.class)
public class TestConfigureLog4jOverride {
    
    private static final Logger log;
    
    static {
        
        // setup the log4j override to the log4j properties file we added into the test resources
        // When a user calls the getLogger method they would be setting this via a -D option to the test launcher.
        System.setProperty("log4j.properties", "classpath:override-log4j-test.properties");
        
        log = KSLog4JConfigurer.getLogger(TestConfigureLog4jOverride.class);
    }
    

    /**
     * 
     */
    public TestConfigureLog4jOverride() {
        
    }
    
    @Test
    public void testme() {
        
        Assert.assertTrue("Failed to use override-log4j-test.properties to set the log level to debugging", log.isDebugEnabled());
        
        log.debug ("at debug level");
    }
}
