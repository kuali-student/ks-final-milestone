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
 * Created by Charles on 4/23/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.student.enrollment.class2.courseoffering.service.CreateSocViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.lum.course.service.CourseService;

import javax.xml.namespace.QName;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CreateSocViewHelperServiceImpl extends ViewHelperServiceImpl implements CreateSocViewHelperService {
    private AcademicCalendarService acalService = null;
    private CourseOfferingService coService = null;
    private CourseOfferingSetService socService = null;
    private CourseService courseService = null;

    @Override
    public SocInfo getMainSocForTerm(String termCode) throws Exception {
        _initServices();

        TermInfo mainTerm = getTermByTermCode(termCode);
        ContextInfo contextInfo = new ContextInfo();
        List<String> socIds = socService.getSocIdsByTerm(mainTerm.getId(), contextInfo);
        List<SocInfo> socInfos = socService.getSocsByIds(socIds, contextInfo);
        for (SocInfo socInfo: socInfos) {
            if (socInfo.getTypeKey().equals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY)) {
                return socInfo;
            }
        }
        return null;
    }

    @Override
    public SocInfo createSocForTerm(String termCode) throws Exception {
        _initServices();
        SocInfo soc = null;
        if (getMainSocForTerm(termCode) != null) {
            return null; // Already exists, so return null
        } else {
            TermInfo mainTerm = getTermByTermCode(termCode);
            SocInfo socInfo = new SocInfo();
            socInfo.setTypeKey(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY);
            socInfo.setStateKey(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY);
            socInfo.setTermId(mainTerm.getId());
            socInfo.setSchedulingStateKey(CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_NOT_STARTED);
            soc = socService.createSoc(mainTerm.getId(), socInfo.getTypeKey(), socInfo, new ContextInfo());
        }
        return soc;
    }

    @Override
    public TermInfo getTermByTermCode(String termCode) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        _initServices();

        // TODO: Find sensible way to rewrap exception that acal service may throw
        // Find the term (alas, I think it does approximate search)
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal(CourseOfferingConstants.ATP_CODE, termCode));
        QueryByCriteria criteria = qbcBuilder.build();

        // Do search.  In ideal case, terms returns one element, which is the desired term.
        List<TermInfo> terms = null;
        terms = acalService.searchForTerms(criteria, new ContextInfo());
        TermInfo mainTerm = null;
        if (terms == null || terms.isEmpty()) {
            throw new InvalidParameterException("Unable to find term for: " + termCode);
        } else {
            mainTerm = terms.get(0);
        }
        return mainTerm;
    }

    private void _initServices() {
        if (coService == null) {
            coService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }

        if (socService == null) {
            socService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE,
                    CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }

        if (courseService == null) {
            Object o = GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "course",
                    "CourseService"));
            courseService = (CourseService) o;
        }

        if (acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE,
                    AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
    }
}
