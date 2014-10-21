/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.student.lum.lu.ui.course.keyvalues;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.cm.course.form.wrapper.CourseCreateUnitsContentOwner;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.cm.course.service.CourseMaintainable;
import org.kuali.student.r1.core.subjectcode.service.SubjectCodeService;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;


/**
 * Return all organizations by a specific subject code.
 *
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
public class OrgsBySubjectCodeValuesFinder extends UifKeyValuesFinderBase {

    private static final Logger LOG = LoggerFactory.getLogger(OrgsBySubjectCodeValuesFinder.class);
    private SubjectCodeService subjectCodeService;
    private boolean blankOption;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> departments = new ArrayList<KeyValue>();

        final MaintenanceDocumentForm form = (MaintenanceDocumentForm) model;

        final SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("subjectCode.search.orgsForSubjectCode");

        final CourseInfo course = ((CourseInfoWrapper) form.getDocument().getNewMaintainableObject().getDataObject()).getCourseInfo();
        searchRequest.addParam("subjectCode.queryParam.code", course.getSubjectArea());

        final CourseMaintainable maintainable = (CourseMaintainable) form.getDocument().getNewMaintainableObject();
        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) form.getDocument().getNewMaintainableObject().getDataObject();

        List<KeyValue> returnedOrgs = getAvailableOrgs(courseInfoWrapper);

        if (!returnedOrgs.isEmpty()){
            departments.addAll(returnedOrgs);
        }

        if (LOG.isDebugEnabled()){
            LOG.debug("Returning {}", departments);
        }

        return departments;
    }

    public List<KeyValue> getAvailableOrgs(CourseInfoWrapper courseInfoWrapper){

        final SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("subjectCode.search.orgsForSubjectCode");

        String orgCode = courseInfoWrapper.getCourseInfo().getSubjectArea();

        searchRequest.addParam("subjectCode.queryParam.code", orgCode);

        List<KeyValue> departments = new ArrayList<KeyValue>();

        try {
            for (final SearchResultRowInfo result
                     : getSubjectCodeService().search(searchRequest, ContextUtils.createDefaultContextInfo()).getRows()) {
                String subjectCodeId = "";
                String subjectCodeOptionalLongName = "";

                for (final SearchResultCellInfo resultCell : result.getCells()) {
                    if ("subjectCode.resultColumn.orgId".equals(resultCell.getKey())) {
                        subjectCodeId = resultCell.getValue();
                    } else if ("subjectCode.resultColumn.orgLongName".equals(resultCell.getKey())) {
                        subjectCodeOptionalLongName = resultCell.getValue();
                    }
                }

                boolean alreadyExists = false;
                for (CourseCreateUnitsContentOwner unitsContentOwner : courseInfoWrapper.getUnitsContentOwner()){
                    if (StringUtils.equals(unitsContentOwner.getOrgId(),subjectCodeId)){
                        alreadyExists = true;
                        break;
                    }
                }

                if (!alreadyExists){
                    departments.add(new ConcreteKeyValue(subjectCodeId, subjectCodeOptionalLongName));
                }

            }
            LOG.debug("Returning {}", departments);

            return departments;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void setBlankOption(final boolean blankOption) {
            this.blankOption = blankOption;
        }

    public boolean getBlankOption() {
            return blankOption;
        }

	protected SubjectCodeService getSubjectCodeService() {
		if (subjectCodeService == null) {
			subjectCodeService = GlobalResourceLoader.getService(new QName(CourseServiceConstants.NAMESPACE_SUBJECTCODE, SubjectCodeService.class.getSimpleName()));
		}
		return subjectCodeService;
	}	

}
