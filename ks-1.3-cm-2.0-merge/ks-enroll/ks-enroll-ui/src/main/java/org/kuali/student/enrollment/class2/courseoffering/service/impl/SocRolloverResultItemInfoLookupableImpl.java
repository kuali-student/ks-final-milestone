/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by David Yin on 5/10/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class SocRolloverResultItemInfoLookupableImpl extends LookupableImpl {
    private transient CourseOfferingSetService courseOfferingSetService;

    public final static String SOC_ROLLOVER_RESULT_ID = "socRolloverResultId";
    //public final static String SOURCE_COURSE_OFFERING_ID = "sourceCourseOfferingId";

    final Logger logger = Logger.getLogger(SocRolloverResultItemInfoLookupableImpl.class);

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        List<SocRolloverResultItemInfo> socRolloverResultItemInfos = new ArrayList<SocRolloverResultItemInfo>();
        String resultId = fieldValues.get(SOC_ROLLOVER_RESULT_ID);

        try {
            socRolloverResultItemInfos = getCourseOfferingSetService().getSocRolloverResultItemsByResultId(resultId, getContextInfo());
        } catch (DoesNotExistException e) {
            logger.error("SocRolloverResultItemInfoLookupableImpl does not exist. ", e);
            throw new RuntimeException("SocRolloverResultItemInfoLookupableImpl does not exist. ", e);
        } catch (InvalidParameterException e) {
            logger.error("SocRolloverResultItemInfoLookupableImpl invalid parameter. ", e);
            throw new RuntimeException("SocRolloverResultItemInfoLookupableImpl invalid parameter. ", e);
        } catch (MissingParameterException e) {
            logger.error("SocRolloverResultItemInfoLookupableImpl missing parameter. ", e);
            throw new RuntimeException("SocRolloverResultItemInfoLookupableImpl missing parameter. ", e);
        } catch (OperationFailedException e) {
            logger.error("SocRolloverResultItemInfoLookupableImpl operation failed. ", e);
            throw new RuntimeException("SocRolloverResultItemInfoLookupableImpl operation failed. ", e);
        } catch (PermissionDeniedException e) {
            logger.error("SocRolloverResultItemInfoLookupableImpl permission denied. ", e);
            throw new RuntimeException("SocRolloverResultItemInfoLookupableImpl permission denied. ", e);
        }

        return socRolloverResultItemInfos;
    }

    public CourseOfferingSetService getCourseOfferingSetService() {
        if (courseOfferingSetService == null) {
            courseOfferingSetService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE, CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseOfferingSetService;
    }

    public ContextInfo getContextInfo() {
        return ContextBuilder.loadContextInfo();
    }
}
