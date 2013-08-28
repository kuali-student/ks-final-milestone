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

package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingManagementViewHelperServiceImpl;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.core.class1.search.ActivityOfferingSearchServiceImpl;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class retrieves Formats based on the course and returns a key-value pair list of
 * the Activity Type name and the Activity Id
 *
 * @author andrewlubbers
 *
 */
public class FormatsForCreateAOKeyValues extends UifKeyValuesFinderBase implements Serializable {

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        CourseOfferingManagementForm coForm = (CourseOfferingManagementForm) model;
        CourseOfferingManagementViewHelperServiceImpl helperService = ((CourseOfferingManagementViewHelperServiceImpl)coForm.getView().getViewHelperService());

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        CourseOfferingInfo selectedCourseOffering = coForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo();

        try {
            SearchRequestInfo sr = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.FO_BY_CO_ID_SEARCH_KEY);
            sr.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.CO_ID, selectedCourseOffering.getId());

            SearchResultInfo results = helperService.getSearchService().search(sr, null);

            for(SearchResultRowInfo row:results.getRows()){
                String foId = null;
                String foName = null;


                for(SearchResultCellInfo cell:row.getCells()){
                    if(ActivityOfferingSearchServiceImpl.SearchResultColumns.FO_ID.equals(cell.getKey())){
                        foId = cell.getValue();
                    }else if(ActivityOfferingSearchServiceImpl.SearchResultColumns.FO_NAME.equals(cell.getKey())){
                        foName = cell.getValue();
                    }
                }
                keyValues.add(new ConcreteKeyValue(foId, foName));

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return keyValues;
    }

    // Get the first Format Offering Key from the database by using the search service to get all FOs for a CO and picking the first one.
    // This will be used as the default value for the Format Offering dropdown in the Add Activity popover,
    // and to determine the default value for the Activity Type and Cluster dropdowns to ensure there are no mismatches.
    public String getFirstKey(ViewModel model){
        CourseOfferingManagementForm coForm = (CourseOfferingManagementForm) model;
        CourseOfferingManagementViewHelperServiceImpl helperService = ((CourseOfferingManagementViewHelperServiceImpl)coForm.getView().getViewHelperService());
        String foId = null;
        CourseOfferingInfo selectedCourseOffering = coForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo();
        try {
            SearchRequestInfo sr = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.FO_BY_CO_ID_SEARCH_KEY);
            sr.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.CO_ID, selectedCourseOffering.getId());

            SearchResultInfo results = helperService.getSearchService().search(sr, null);

                for(SearchResultCellInfo cell:results.getRows().get(0).getCells()){
                    if(ActivityOfferingSearchServiceImpl.SearchResultColumns.FO_ID.equals(cell.getKey())){
                        foId = cell.getValue();
                    }
                }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return foId;
    }






}
