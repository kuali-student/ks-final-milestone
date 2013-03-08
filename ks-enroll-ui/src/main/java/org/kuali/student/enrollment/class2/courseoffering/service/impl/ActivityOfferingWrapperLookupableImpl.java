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
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.class1.search.CourseOfferingManagementSearchImpl;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.service.SearchService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class ActivityOfferingWrapperLookupableImpl extends LookupableImpl {

    private SearchService searchService;

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        List<ActivityOfferingWrapper> activityOfferingWrappers = new ArrayList<ActivityOfferingWrapper>();

        String termId = fieldValues.get("termId");
        String courseOfferingCode = fieldValues.get("courseOfferingCode");

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
                SearchResultInfo searchResult = getSearchService().search(searchRequest, ContextUtils.createDefaultContextInfo());

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
                    List<ActivityOfferingInfo> aos = getCourseOfferingService().getActivityOfferingsByCourseOffering(coId,ContextUtils.createDefaultContextInfo());
                    for (ActivityOfferingInfo activityOffering : aos) {
                        CO_AO_RG_ViewHelperServiceImpl helper = new CO_AO_RG_ViewHelperServiceImpl();
                        ActivityOfferingWrapper wrapper = helper.convertAOInfoToWrapper(activityOffering);
                        activityOfferingWrappers.add(wrapper);
                    }
                }

            } catch (Exception e){
                throw new RuntimeException(e);
            }

        } else if (StringUtils.isNotBlank(fieldValues.get("aoInfo.id"))){

            QueryByCriteria qbc = buildQueryByCriteria(fieldValues);
            try{
                List<ActivityOfferingInfo> activityOfferings = getCourseOfferingService().searchForActivityOfferings(qbc, ContextUtils.createDefaultContextInfo());
                for (ActivityOfferingInfo activityOffering : activityOfferings) {
                    ActivityOfferingWrapper wrapper = new ActivityOfferingWrapper(activityOffering);
                    activityOfferingWrappers.add(wrapper);
                }
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
        }

        return activityOfferingWrappers;
    }

    private QueryByCriteria buildQueryByCriteria(Map<String, String> fieldValues){
        String aoId = fieldValues.get(ActivityOfferingConstants.ACTIVITY_OFFERING_WRAPPER_ID);

        List<Predicate> predicates = new ArrayList<Predicate>();
        if (StringUtils.isNotBlank(aoId)) {
            predicates.add(PredicateFactory.equalIgnoreCase("id", aoId));
        }

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));
        QueryByCriteria qbc = qbcBuilder.build();

        return qbc;
    }

    private boolean hasCriteria(Map<String, String> fieldValues){
        return StringUtils.isNotBlank(fieldValues.get(ActivityOfferingConstants.ACTIVITY_OFFERING_WRAPPER_ID));
    }

    public CourseOfferingService getCourseOfferingService() {
        return CourseOfferingResourceLoader.loadCourseOfferingService();
    }

    protected SearchService getSearchService() {
        if(searchService == null) {
            searchService = (SearchService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "search", SearchService.class.getSimpleName()));
        }
        return searchService;
    }
}
