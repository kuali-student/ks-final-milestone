/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 *
 * Created by Charles on 8/9/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service.facade;

import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.service.TypeService;

import javax.annotation.Resource;

/**
 * Impl for CSR
 *
 * @author Kuali Student Team
 */
public class CSRServiceFacadeImpl implements CSRServiceFacade {
    @Resource(name="coService")
    private CourseOfferingService coService;

    @Resource(name="typeService")
    private TypeService typeService;

    public void setCoService(CourseOfferingService coService) {
        this.coService = coService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    @Override
    public void cancelActivityOffering(String aoId, ContextInfo context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void suspendActivityOffering(String aoId, ContextInfo context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void reinstateActivityOffering(String aoId, ContextInfo context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
