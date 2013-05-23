/**
 * Copyright 2005-2012 The Kuali Foundation
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;

/**
 * This is the helper class for CourseView
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */

public class DatesKeyValuesFinder extends KeyValuesBase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DatesKeyValuesFinder.class);
    
    private boolean blankOption;

    private AtpService atpService;

    @Override
    public List<KeyValue> getKeyValues() {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        try {
            SearchRequestInfo searchRequest = new SearchRequestInfo();
            searchRequest.setSearchKey("atp.search.advancedAtpSearch");
            List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
            SearchParamInfo param = new SearchParamInfo();
            param.setKey("atp.advancedAtpSearchParam.atpType"); //"atp.advancedAtpSearchParam.optionalAtpIds

            List<String> typeValues = new ArrayList<String>();
            typeValues.add(AtpServiceConstants.ATP_SPRING_TYPE_KEY);
            typeValues.add(AtpServiceConstants.ATP_FALL_TYPE_KEY);
            typeValues.add(AtpServiceConstants.ATP_SUMMER_TYPE_KEY);
            typeValues.add(AtpServiceConstants.ATP_SESSION_1_TYPE_KEY);
            typeValues.add(AtpServiceConstants.ATP_SESSION_2_TYPE_KEY);
            typeValues.add(AtpServiceConstants.ATP_MINI_MESTER_1_A_TYPE_KEY);
            typeValues.add(AtpServiceConstants.ATP_MINI_MESTER_1_B_TYPE_KEY);
            param.setValues(typeValues);
            queryParamValueList.add(param);
            searchRequest.setParams(queryParamValueList);
            SearchResultInfo searchResult = this.getAtpService().search(searchRequest, getContextInfo());

            if(blankOption){
                keyValues.add(new ConcreteKeyValue("", ""));
            }
            
            for (SearchResultRowInfo result : searchResult.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                String id = "";
                String descrip = "";
                for (SearchResultCellInfo cell : cells) {
                    if (cell.getKey().contains("atp.resultColumn.atpId")) {
                        id = cell.getKey().toString();
                    }
                    if (cell.getKey().contains("atp.resultColumn.atpShortName")) {
                        descrip = cell.getValue().toString();
                    }
                }
                keyValues.add(new ConcreteKeyValue(id, descrip));
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return keyValues;
    }

    private AtpService getAtpService() {
        if (atpService == null)
        {
            QName qname = new QName(AtpServiceConstants.NAMESPACE, AtpServiceConstants.SERVICE_NAME_LOCAL_PART);
            atpService = (AtpService) GlobalResourceLoader.getService(qname);
        }
        return atpService;
    }

    private ContextInfo getContextInfo() {
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setAuthenticatedPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        contextInfo.setPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(Locale.getDefault().getLanguage());
        localeInfo.setLocaleRegion(Locale.getDefault().getCountry());
        contextInfo.setLocale(localeInfo);

        return contextInfo;
    }
    
    /**
     * @return the blankOption
     */
    public boolean isBlankOption() {
        return this.blankOption;
    }

    /**
     * @param blankOption the blankOption to set
     */
    public void setBlankOption(boolean blankOption) {
        this.blankOption = blankOption;
    }

}
