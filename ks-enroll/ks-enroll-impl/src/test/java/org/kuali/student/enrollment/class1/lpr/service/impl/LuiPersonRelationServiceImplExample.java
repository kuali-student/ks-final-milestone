/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.lpr.service.impl;

import org.junit.Test;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;


/**
 * @Author sambit
 */

public class LuiPersonRelationServiceImplExample {


    public LuiPersonRelationService lprService;


    public void setLprService(LuiPersonRelationService lprService) {
        this.lprService = lprService;
    }


//  @Test
    public void testCreateBulkRelationshipsForPerson() {
        ApplicationContext appContext =
                new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml", "testContext.xml"});
        lprService = (LuiPersonRelationService) appContext.getBean("lprService");

        try {
            List<String> createResults = lprService.createBulkRelationshipsForPerson("123", null, null, null, null, null);
            assertNull(createResults);
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }


    }


}