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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.kuali.student.r2.common.messages.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kuali Student Team 
 *
 */
public final class SpringProxyTestUtils {
    private static final Logger log = LoggerFactory
            .getLogger(SpringProxyTestUtils.class);

    /**
     * 
     */
    private SpringProxyTestUtils() {
        // TODO Auto-generated constructor stub
    }
    
    public static Object testBeanSerialization(Object bean, long expectedSize) throws IOException, ClassNotFoundException {
        
        File tempFile = File.createTempFile("proxy", "dat");

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tempFile));
        
        oos.writeObject(bean);
        oos.flush();
        oos.close();
        
        // check the size of that file
        
        long serializedSizeInBytes = FileUtils.sizeOf(tempFile);
        
        Assert.assertEquals(expectedSize, serializedSizeInBytes);
        
        ObjectInputStream iis = new ObjectInputStream(new FileInputStream(tempFile));
        
        Object deserialized =  iis.readObject();
        
        iis.close();
        
        return deserialized;
        
    }
    
 public static Object testBeanSerialization(Object bean, String expectedToStringValue, long expectedSize) throws IOException, ClassNotFoundException {
        
        File tempFile = File.createTempFile("proxy", "dat");

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tempFile));
        
        oos.writeObject(bean);
        oos.flush();
        oos.close();
        
        // check the size of that file
        
        long serializedSizeInBytes = FileUtils.sizeOf(tempFile);
        
        Assert.assertEquals(expectedSize, serializedSizeInBytes);
        
        ObjectInputStream iis = new ObjectInputStream(new FileInputStream(tempFile));
        
        Object generated =  iis.readObject();
        
        iis.close();
        
        Assert.assertEquals(expectedToStringValue, generated.toString());
        
        return generated;
    }
}
