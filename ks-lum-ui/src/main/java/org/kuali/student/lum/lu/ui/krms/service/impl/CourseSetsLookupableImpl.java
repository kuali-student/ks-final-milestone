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
package org.kuali.student.lum.lu.ui.krms.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.student.common.uif.service.impl.KSLookupableImpl;
import org.kuali.student.lum.lu.ui.krms.dto.CluSetWrapper;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Lookupable Implementation for Course Sets
 *
 * @author Kuali Student Team
 */
public class CourseSetsLookupableImpl extends KSLookupableImpl {

    private CluService cluService;

    @Override
    public List<?> performSearch(LookupForm lookupForm, Map<String, String> searchCriteria, boolean bounded) {

        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();

        String name = searchCriteria.get("name");
        if (StringUtils.isNotBlank(name) && !name.isEmpty()) {
            SearchParamInfo nameParam = new SearchParamInfo();
            nameParam.setKey("cluset.queryParam.optionalName");
            nameParam.getValues().add(name);
            queryParamValueList.add(nameParam);
        }

        String description = searchCriteria.get("descr");
        if (StringUtils.isNotBlank(description) && !description.isEmpty()){
            SearchParamInfo descriptionParam = new SearchParamInfo();
            descriptionParam.setKey("cluset.queryParam.optionalDescription");
            descriptionParam.getValues().add(description);
            queryParamValueList.add(descriptionParam);
        }

        SearchParamInfo reusableParam = new SearchParamInfo();
        reusableParam.setKey("cluset.queryParam.optionalReusable");
        reusableParam.getValues().add(Boolean.TRUE.toString());
        queryParamValueList.add(reusableParam);
        SearchParamInfo cluSetTypeParam = new SearchParamInfo();
        cluSetTypeParam.setKey("cluset.queryParam.optionalType");
        cluSetTypeParam.getValues().add(CluServiceConstants.CLUSET_TYPE_CREDIT_COURSE);
        queryParamValueList.add(cluSetTypeParam);
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("cluset.search.generic");
        searchRequest.setParams(queryParamValueList);

        List<CluSetWrapper> cluSetInfos = new ArrayList<CluSetWrapper>();
        try {
            SearchResultInfo clus = getCluService().search(searchRequest, ContextUtils.createDefaultContextInfo());
            for (SearchResultRowInfo result : clus.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                CluSetWrapper cluSetInfo = new CluSetWrapper();
                for (SearchResultCellInfo cell : cells) {
                    if ("cluset.resultColumn.cluSetId".equals(cell.getKey())) {
                        cluSetInfo.setId(cell.getValue());
                    } else if ("cluset.resultColumn.name".equals(cell.getKey())) {
                        cluSetInfo.setName(cell.getValue());
                    } else if ("cluset.resultColumn.description".equals(cell.getKey())) {
                        cluSetInfo.setDescr(cell.getValue());
                    } else if ("cluset.resultColumn.type".equals(cell.getKey())) {
                        cluSetInfo.setTypeKey(cell.getValue());
                    }
                }
                cluSetInfos.add(cluSetInfo);
            }
        } catch (Exception e) {

        }
        return cluSetInfos;
    }

    protected CluService getCluService() {
        if (cluService == null) {
            cluService = GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE, CluServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return cluService;
    }

}
