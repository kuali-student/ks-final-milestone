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
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.class1.search.CourseOfferingManagementSearchImpl;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class provides a Lookupable implementation for Activity Offering Wrappers
 *
 * @author Kuali Student Team
 */
public class ActivityOfferingWrapperLookupableImpl extends LookupableImpl {

    @Override
    public boolean validateSearchParameters(LookupForm form, Map<String, String> searchCriteria){
        if (searchCriteria == null || searchCriteria.isEmpty() || StringUtils.isBlank(searchCriteria.get("termId")) || StringUtils.isBlank(searchCriteria.get("courseOfferingCode"))){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS,RiceKeyConstants.ERROR_CUSTOM,"Missing Course Offering code");
            return false;
        }
        return true;
    }

    @Override
    public List<?> performSearch(LookupForm lookupForm, Map<String, String> searchCriteria, boolean bounded) {
        boolean isValidCriteria = validateSearchParameters(lookupForm, searchCriteria);
        if (!isValidCriteria) {
            return new ArrayList<Object>();
        }

        List<ActivityOfferingWrapper> activityOfferingWrappers = new ArrayList<ActivityOfferingWrapper>();

        String termId = searchCriteria.get("termId");
        String courseOfferingCode = searchCriteria.get("courseOfferingCode");

        /**
         * Edit AO maintenace document uses this AO search to allow user to add colocated AOs.
         */
        if (StringUtils.isNotBlank(termId) && StringUtils.isNotBlank(courseOfferingCode)){

            SearchRequestInfo searchRequest = new SearchRequestInfo(CourseOfferingManagementSearchImpl.CO_MANAGEMENT_SEARCH.getKey());
            searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.COURSE_CODE, StringUtils.upperCase(courseOfferingCode));
            searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.ATP_ID, termId);
            searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.IS_EXACT_MATCH_CO_CODE_SEARCH, BooleanUtils.toStringTrueFalse(true));

            try {
                String coId = "";
                SearchResultInfo searchResult = CourseOfferingManagementUtil.getSearchService().search(searchRequest, ContextUtils.createDefaultContextInfo());

                if (searchResult.getRows().isEmpty()){
                    GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,"Invalid CourseOfferingCode " + courseOfferingCode);
                } else if (searchResult.getRows().size() > 1){
                    GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,"Multiple CourseOfferings entries found for " + courseOfferingCode);
                } else {
                    for(SearchResultCellInfo cellInfo : searchResult.getRows().get(0).getCells()){

                        if(CourseOfferingManagementSearchImpl.SearchResultColumns.CO_ID.equals(cellInfo.getKey())){
                            coId = cellInfo.getValue();
                            break;
                        }

                    }
                }

                if (StringUtils.isNotBlank(coId)){
                    List<ActivityOfferingInfo> aos = CourseOfferingManagementUtil.getCourseOfferingService().getActivityOfferingsByCourseOffering(coId,ContextUtils.createDefaultContextInfo());
                    for (ActivityOfferingInfo activityOffering : aos) {
                        CO_AO_RG_ViewHelperServiceImpl helper = new CO_AO_RG_ViewHelperServiceImpl();
                        ActivityOfferingWrapper wrapper = helper.convertAOInfoToWrapper(activityOffering);
                        wrapper.setCourseOfferingId(coId);
                        activityOfferingWrappers.add(wrapper);
                    }
                }

            } catch (Exception e){
                throw new RuntimeException(e);
            }

        }

        return activityOfferingWrappers;
    }

}
