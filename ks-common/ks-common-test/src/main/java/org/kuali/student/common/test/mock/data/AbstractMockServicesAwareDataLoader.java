/*
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.test.mock.data;

import java.util.Date;
import java.util.Map;

import org.kuali.student.common.mock.MockService;
import org.kuali.student.common.test.TestAwareDataLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Using this data loader it is possible to have the entire stack of mock
 * services reset their state back to the initial conditions in between unit
 * test method runs.
 * 
 * This allows for destructive cases to be tested without concern about the test
 * data state for subsequent tests.
 * 
 * @author Kuali Student Team
 * 
 */
public abstract class AbstractMockServicesAwareDataLoader  implements ApplicationContextAware, TestAwareDataLoader {
    
    private static final Logger log = LoggerFactory
            .getLogger(AbstractMockServicesAwareDataLoader.class);
    
    protected ApplicationContext applicationContext;

    protected boolean initialized;
    
    protected final ContextInfo context;
    
    public AbstractMockServicesAwareDataLoader() {
    
        context = new ContextInfo();
        
        context.setPrincipalId("123");
        context.setCurrentDate(new Date());
    }

    @Override
    public final void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
                this.applicationContext = applicationContext;
        
    }

    @Override
    public final void beforeTest() throws Exception {
        
        initializeData();
        
        initialized = true;
        
    }

    /**
     * Child classes implement this method to add data into the mock services.
     */
    protected abstract void initializeData() throws Exception;
    
    

    /**
     * @return the initialized
     */
    public final boolean isInitialized() {
        return initialized;
    }

    @Override
    public final void afterTest() throws Exception {
        
        Map<String, MockService> map = applicationContext.getBeansOfType(MockService.class);
        
        for (MockService mock : map.values()) {
        
            // clear all mock services of state at the end of the test
            mock.clear();
        }
        
    }
    
    
    
    
}
