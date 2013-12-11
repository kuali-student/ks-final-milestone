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

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.student.lum.lu.ui.krms.dto.CluInformation;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.ProgramServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.program.service.ProgramService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Lookupable Implementation for Programs
 *
 * @author Kuali Student Team
 */
public class ProgramInfoLookupableImpl extends LookupableImpl {
	private static final long serialVersionUID = 1L;	
	
    private transient CluService cluService;
    private ProgramService programService;

    public enum QueryParamEnum {
        ID("lu.queryParam.luOptionalId","id"),
        TITLE("lu.queryParam.luOptionalLongName", "title"),
        CODE("lu.queryParam.luOptionalCode", "code"),
        DESCRIPTION("lu.queryParam.luOptionalDescr", "description");

        private final String fieldValue;
        private final String queryKey;

        QueryParamEnum(String qKey, String fValue) {
            this.queryKey = qKey;
            this.fieldValue = fValue;
        }

        public String getFieldValue() {
            return fieldValue;
        }

        public String getQueryKey() {
            return queryKey;
        }
    }

    @Override
    public List<?> performSearch(LookupForm lookupForm, Map<String, String> searchCriteria, boolean bounded) {
        List <CluInformation> programInfoList = new ArrayList<CluInformation>();
        List<SearchParamInfo> searchParams = new ArrayList<SearchParamInfo>();
        SearchParamInfo qpv1 = new SearchParamInfo();
        qpv1.setKey("lu.queryParam.luOptionalType");
//        qpv1.getValues().add("kuali.lu.type.CreditCourse");
        qpv1.getValues().add("kuali.lu.type.credential.Baccalaureate");
        qpv1.getValues().add("kuali.lu.type.credential.Masters");
        qpv1.getValues().add("kuali.lu.type.credential.Professional");
        qpv1.getValues().add("kuali.lu.type.credential.Doctoral");
        qpv1.getValues().add("kuali.lu.type.credential.UndergraduateCertificate");
        qpv1.getValues().add("kuali.lu.type.credential.GraduateCertificate");
        qpv1.getValues().add("kuali.lu.type.credential.ContinuingEd");
        qpv1.getValues().add("kuali.lu.type.MajorDiscipline");
        qpv1.getValues().add("kuali.lu.type.Variation");
        qpv1.getValues().add("kuali.lu.type.MinorDiscipline");
        qpv1.getValues().add("kuali.lu.type.CoreProgram");
        qpv1.getValues().add("kuali.lu.type.Honors");
        qpv1.getValues().add("kuali.lu.type.LivingLearning");
        searchParams.add(qpv1);
        SearchParamInfo qpv2 = new SearchParamInfo();
        qpv2.setKey("lu.queryParam.luOptionalState");
        qpv2.getValues().add("Approved");
        qpv2.getValues().add("Active");
        qpv2.getValues().add("Retired");
        qpv2.getValues().add("Suspended");
        searchParams.add(qpv2);
        for (QueryParamEnum qpEnum : QueryParamEnum.values()) {
            String fieldValue = searchCriteria.get(qpEnum.getFieldValue());
            if ( ! isEmpty(fieldValue) ) {
                SearchParamInfo qpv = new SearchParamInfo();
                qpv.setKey(qpEnum.getQueryKey());
                qpv.getValues().add(fieldValue);
                searchParams.add(qpv);
            }
        }

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setParams(searchParams);
        searchRequest.setSearchKey("lu.search.current");

        try {
            SearchResultInfo searchResult = getCluService().search(searchRequest, ContextUtils.getContextInfo());
            CluInformation clu  = null;
            if (searchResult.getRows().size() > 0) {
                for(SearchResultRowInfo srrow : searchResult.getRows()){
                    clu = new CluInformation();
                    List<SearchResultCellInfo> srCells = srrow.getCells();
                    if(srCells != null && srCells.size() > 0){
                        for(SearchResultCellInfo srcell : srCells){
                            if (srcell.getKey().equals("lu.resultColumn.cluId")) {
                                clu.setCluId(srcell.getValue());
                            }
                            else if(srcell.getKey().equals("lu.resultColumn.luOptionalLongName")) {
                                clu.setTitle(srcell.getValue());
                            }
                             else if (srcell.getKey().equals("lu.resultColumn.luOptionalVersionIndId")){
                            clu.setVerIndependentId(srcell.getValue());
                             }
                            else if(srcell.getKey().equals("lu.resultColumn.luOptionalDescr")) {
                                RichTextInfo richTextInfo = new RichTextInfo();
                                richTextInfo.setPlain(srcell.getValue());
                                clu.setDescription(srcell.getValue());
                            }
                            else if(srcell.getKey().equals("lu.resultColumn.luOptionalState")) {
                                clu.setState(srcell.getValue());
                            }
                            else if(srcell.getKey().equals("lu.resultColumn.luOptionalCode")) {
                                clu.setCode(srcell.getValue());
                            }

                        }
                    }
                    programInfoList.add(clu);
                }

            }

            return programInfoList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Note: here I am using r1 CluService implementation!!!
    protected CluService getCluService() {
        if(cluService == null) {
            cluService = (CluService)GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE,CluServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.cluService;
    }


    protected ProgramService getProgramService() {
        if(programService == null) {
            programService = (ProgramService)GlobalResourceLoader.getService(new QName(ProgramServiceConstants.PROGRAM_NAMESPACE,"ProgramService"));
        }
        return this.programService;
    }
}
