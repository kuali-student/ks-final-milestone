/*
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.academicrecord.service;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.class2.academicrecord.service.impl.AcademicRecordServiceMapImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:acad-test-with-map-context.xml"})
public class AcademicRecordServiceTest {

    @Resource
    private AcademicRecordServiceDataLoader dataLoader;

    private ContextInfo contextInfo;
    private String principalId = "123";

    @org.junit.Before
    public void setUp() {
        principalId = "123";
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
    }

    @After
    public void cleanup() throws Exception {
        dataLoader.afterTest();
    }

    @Test
    public void testDataLoader() throws Exception {
        dataLoader.beforeTest();
    }
}
