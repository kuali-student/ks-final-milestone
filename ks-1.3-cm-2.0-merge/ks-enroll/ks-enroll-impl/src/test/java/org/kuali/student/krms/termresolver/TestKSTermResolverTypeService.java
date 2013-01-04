/**
 * Copyright 2011 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.krms.termresolver;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.impl.type.KrmsTypeResolver;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ks-krms-test-context.xml"})
@Ignore
public class TestKSTermResolverTypeService {
        private KrmsTypeResolver typeResolver;

        public ContextInfo callContext = null;
        @Resource(name = "acadRecordService")
        private AcademicRecordService academicRecordService;
        private CourseRegistrationService courseRegistrationService;
        private OrganizationService organizationService;

        @Before
        public void setUp() {
            callContext = new ContextInfo();
        }


        @Test
        public void testCourseRegistrationServiceNotNull() {
            assertNotNull(courseRegistrationService);
        }

    @Test
    public void testOrganizationServiceNotNull() {
        assertNotNull(organizationService);
    }

    @Test
    public void testAcadRecordServiceNotNull() {
        assertNotNull(academicRecordService);
    }
        @Test
        public void testloadTermResolver() {

            KSTermResolverTypeService termResolver = new KSTermResolverTypeService();
            TermResolverDefinition termResolverDefinition = null ;
            TermResolver<?> term = termResolver.loadTermResolver(termResolverDefinition);
        }



    }
