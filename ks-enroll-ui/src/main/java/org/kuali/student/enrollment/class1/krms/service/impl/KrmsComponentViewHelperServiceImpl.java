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
 * Created by Paul on 2012/12/10
 */
package org.kuali.student.enrollment.class1.krms.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class KrmsComponentViewHelperServiceImpl extends ViewHelperServiceImpl {

    private CluService cluService;

    private ContextInfo contextInfo;

    public List<KrmsSuggestDisplay> getCourseNamesForSuggest(String moduleName) {
        List<KrmsSuggestDisplay> displays = new ArrayList<KrmsSuggestDisplay>();
        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        SearchParamInfo stateKeyParam = new SearchParamInfo();
        stateKeyParam.setKey("lu.queryParam.luOptionalState");
        List<String> stateValues = new ArrayList<String>();
        stateValues.add("Active");
        stateValues.add("Approved");
        stateKeyParam.setValues(stateValues);
        queryParamValueList.add(stateKeyParam);
        SearchParamInfo cluCodeParam = new SearchParamInfo();
        cluCodeParam.setKey("lu.queryParam.luOptionalCode");
        cluCodeParam.getValues().add(moduleName);
        queryParamValueList.add(cluCodeParam);
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("lu.search.current.quick");
        searchRequest.setParams(queryParamValueList);
        SearchResultInfo clus = null;
        try {
            clus = getCluService().search(searchRequest, getContextInfo());
            for (SearchResultRowInfo result : clus.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                KrmsSuggestDisplay display = new KrmsSuggestDisplay();
                for (SearchResultCellInfo cell : cells) {
                    if ("lu.resultColumn.cluId".equals(cell.getKey())) {
                        display.setId(cell.getValue());
                    } else if ("lu.resultColumn.luOptionalCode".equals(cell.getKey())) {
                        display.setDisplayName(cell.getValue());
                    }
                }
                displays.add(display);
            }
        } catch (Exception e) {

        }
        return displays;
    }

    private CluService getCluService() {
        if (cluService == null) {
            cluService = (CluService) GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE, CluServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return cluService;
    }

    private ContextInfo getContextInfo() {
        if (null == contextInfo) {
            //TODO - get real ContextInfo
            contextInfo = TestHelper.getContext1();
        }
        return contextInfo;
    }

}
