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
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.common.uif.service.impl.KSLookupableImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.service.CourseService;
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
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        List<CluSetInfo> cluSetInfos = new ArrayList<CluSetInfo>();
        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        String name = fieldValues.get("name");
        String description = fieldValues.get("descr");
        if (StringUtils.isNotBlank(name) && !name.isEmpty()) {
            SearchParamInfo nameParam = new SearchParamInfo();
            nameParam.setKey("cluset.queryParam.optionalName");
            nameParam.getValues().add(name);
            queryParamValueList.add(nameParam);
        }
        else if (StringUtils.isNotBlank(description) && !description.isEmpty()){
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
        SearchResultInfo clus = null;
        try {
            clus = getCluService().search(searchRequest, ContextUtils.createDefaultContextInfo());
            for (SearchResultRowInfo result : clus.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                CluSetInfo cluSetInfo = new CluSetInfo();
                for (SearchResultCellInfo cell : cells) {
                    if ("cluset.resultColumn.cluSetId".equals(cell.getKey())) {
                        cluSetInfo.setId(cell.getValue());
                    } else if ("cluset.resultColumn.name".equals(cell.getKey())) {
                        cluSetInfo.setName(cell.getValue());
                    } else if ("cluset.resultColumn.description".equals(cell.getKey())) {
                        RichTextInfo richTextInfo = new RichTextInfo();
                        richTextInfo.setPlain(cell.getValue());
                        cluSetInfo.setDescr(richTextInfo);
                    }
                    else if ("cluset.resultColumn.type".equals(cell.getKey())) {
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
