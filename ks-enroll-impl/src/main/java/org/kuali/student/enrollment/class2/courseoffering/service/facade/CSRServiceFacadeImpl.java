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
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
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

    @Resource(name="socService")
    private CourseOfferingSetService socService;

    @Resource(name="luiService")
    private LuiService luiService;

    public void setCoService(CourseOfferingService coService) {
        this.coService = coService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public void setSocService(CourseOfferingSetService socService) {
        this.socService = socService;
    }

    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    @Override
    public void cancelActivityOffering(String aoId, ContextInfo context) {
        LuiInfo aoLui = null;
        try {
            aoLui = luiService.getLui(aoId, context);
            aoLui.setStateKey(LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY);
            luiService.updateLui(aoLui.getId(), aoLui, context);
        } catch (Exception e) {
            new RuntimeException("Could not cancel AO(s) " + e);
        }
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
