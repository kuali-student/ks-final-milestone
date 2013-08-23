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
package org.kuali.student.common.spring;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test that the factory bean works as designed to return a serializable proxy that can be used to retrieve the bean from the application context.
 * 
 * @author Kuali Student Team 
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations="classpath:test-spring-serializable-proxy-factory-bean.xml")
public class TestSerializableProxyFactoryBean {
    private static final Logger log = LoggerFactory
            .getLogger(TestSerializableProxyFactoryBean.class);

    @Autowired
    private ApplicationContext applicationContext;
    
    /**
     * 
     */
    public TestSerializableProxyFactoryBean() {
        
    }
    
    @Test
    public void testFactoryBean() throws IOException, ClassNotFoundException {
        
        Assert.assertNotNull(applicationContext);
        
        Object bean = applicationContext.getBean("serializableList");
        
        Assert.assertNotNull(bean);
        
        boolean aList = false;
        
        if (bean instanceof List) { 
            aList = true;
        }
        Assert.assertEquals(true, aList);
        
        List list = (List)SpringProxyTestUtils.testBeanSerialization(bean, 211L);
        
        for (int i = 0; i < 100000; i++) {
            list.add("A" + i);
        }
        int expectedElements = list.size();
        
        List copy = (List)SpringProxyTestUtils.testBeanSerialization(list, 211L);
        
        Assert.assertEquals(expectedElements, copy.size());
    }
}
