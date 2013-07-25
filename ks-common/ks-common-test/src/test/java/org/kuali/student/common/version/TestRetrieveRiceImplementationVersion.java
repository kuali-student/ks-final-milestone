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
package org.kuali.student.common.version;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Test how to retrieve the rice version from its manifest.
 *
 * @author Kuali Student Team 
 *
 */
public class TestRetrieveRiceImplementationVersion {
    private static final Logger log = LoggerFactory
            .getLogger(TestRetrieveRiceImplementationVersion.class);

    /**
     * 
     */
    public TestRetrieveRiceImplementationVersion() {
    }
    
    @Test
    public void testRetrieveRiceVersion () {
        
        String version = GlobalResourceLoader.class.getPackage().getImplementationVersion();
        
        Assert.assertNotNull(version);
    }
}
