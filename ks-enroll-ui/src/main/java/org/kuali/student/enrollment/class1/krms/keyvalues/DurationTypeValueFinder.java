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
 * Created by Paul on 2013/01/11
 */
package org.kuali.student.enrollment.class1.krms.keyvalues;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class1.krms.dto.PropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.RuleEditor;
import org.kuali.student.enrollment.class1.krms.form.KrmsComponentsForm;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class DurationTypeValueFinder extends UifKeyValuesFinderBase {

    private AtpService atpService;
    private LRCService lrcService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> labels = new ArrayList<KeyValue>();
        labels.add(new ConcreteKeyValue("", ""));
        labels.add(new ConcreteKeyValue(AtpServiceConstants.DURATION_YEAR_TYPE_KEY, "Year"));
        labels.add(new ConcreteKeyValue(AtpServiceConstants.DURATION_FOUR_YEARS_TYPE_KEY, "Four Years"));
        labels.add(new ConcreteKeyValue(AtpServiceConstants.DURATION_HALF_SEMESTER_TYPE_KEY, "Half Semester"));
        labels.add(new ConcreteKeyValue(AtpServiceConstants.DURATION_SESSION_TYPE_KEY, "Session"));
        labels.add(new ConcreteKeyValue(AtpServiceConstants.DURATION_PERIOD_TYPE_KEY, "Period"));
        labels.add(new ConcreteKeyValue(AtpServiceConstants.DURATION_MINI_MESTER_TYPE_KEY, "Mini-Mester"));
        labels.add(new ConcreteKeyValue(AtpServiceConstants.DURATION_WEEK_TYPE_KEY, "Week"));
        labels.add(new ConcreteKeyValue(AtpServiceConstants.DURATION_TERM_TYPE_KEY, "Term"));
        labels.add(new ConcreteKeyValue(AtpServiceConstants.DURATION_SEMESTER_TYPE_KEY, "Semester"));
        labels.add(new ConcreteKeyValue(AtpServiceConstants.DURATION_TBD_TYPE_KEY, "TBD"));

        return labels;
    }
//    @Override
//    public List<KeyValue> getKeyValues(ViewModel model) {
//        List<KeyValue> keyValues = new ArrayList<KeyValue>();
//        try {
//            SearchRequestInfo searchRequest = new SearchRequestInfo();
//            searchRequest.setSearchKey("atp.search.advancedAtpSearch");
//            List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
//            SearchParamInfo param = new SearchParamInfo();
//            param.setKey("atp.advancedAtpSearchParam.atpType");  //"atp.advancedAtpSearchParam.optionalAtpIds
//
//           List<String> typeValues = new ArrayList<String>();
//            typeValues.add(AtpServiceConstants.DURATION_YEAR_TYPE_KEY);
//            typeValues.add(AtpServiceConstants.DURATION_FOUR_YEARS_TYPE_KEY);
//            typeValues.add(AtpServiceConstants.DURATION_HALF_SEMESTER_TYPE_KEY);
//            typeValues.add(AtpServiceConstants.DURATION_SESSION_TYPE_KEY);
//            typeValues.add(AtpServiceConstants.DURATION_PERIOD_TYPE_KEY);
//            typeValues.add(AtpServiceConstants.DURATION_MONTH_TYPE_KEY);
//            typeValues.add(AtpServiceConstants.DURATION_TERM_TYPE_KEY);
//            typeValues.add(AtpServiceConstants.DURATION_HOURS_TYPE_KEY);
//            typeValues.add(AtpServiceConstants.DURATION_WEEK_TYPE_KEY);
//            param.setValues(typeValues);
//            queryParamValueList.add(param);
//            searchRequest.setParams(queryParamValueList);
//            SearchResultInfo searchResult = this.getAtpService().search(searchRequest, getContextInfo());
//
//            for (SearchResultRowInfo result : searchResult.getRows()) {
//                List<SearchResultCellInfo> cells = result.getCells();
//                for (SearchResultCellInfo cell : cells) {
//                    keyValues.add(new ConcreteKeyValue(cell.getKey(), cell.getValue()));
//                }
//
//            }
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
//        return keyValues;
//    }

    private AtpService getAtpService() {
        if (atpService == null)
        {
            QName qname = new QName(AtpServiceConstants.NAMESPACE,AtpServiceConstants.SERVICE_NAME_LOCAL_PART);
            atpService = (AtpService) GlobalResourceLoader.getService(qname);
        }
        return atpService;
    }
    private LRCService getLRCService() {
        if (lrcService == null)
        {
            QName qname = new QName(LrcServiceConstants.NAMESPACE,LrcServiceConstants.SERVICE_NAME_LOCAL_PART);
            lrcService = (LRCService) GlobalResourceLoader.getService(qname);
        }
        return lrcService;
    }
    private ContextInfo getContextInfo() {
        return ContextBuilder.loadContextInfo();
    }
}
