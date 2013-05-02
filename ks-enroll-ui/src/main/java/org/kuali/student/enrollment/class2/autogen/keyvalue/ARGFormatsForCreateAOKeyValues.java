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

package org.kuali.student.enrollment.class2.autogen.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class2.autogen.form.ARGCourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.autogen.service.impl.ARGCourseOfferingManagementViewHelperServiceImpl;
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
public class ARGFormatsForCreateAOKeyValues extends UifKeyValuesFinderBase implements Serializable {

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        ARGCourseOfferingManagementForm coForm = (ARGCourseOfferingManagementForm) model;
        ARGCourseOfferingManagementViewHelperServiceImpl helperService = ((ARGCourseOfferingManagementViewHelperServiceImpl)coForm.getView().getViewHelperService());

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






}
