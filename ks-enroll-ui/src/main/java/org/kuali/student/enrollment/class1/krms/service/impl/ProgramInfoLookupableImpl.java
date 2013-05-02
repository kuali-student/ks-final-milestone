package org.kuali.student.enrollment.class1.krms.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.ProgramServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.program.dto.CoreProgramInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.program.service.ProgramService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.isEmpty;

public class ProgramInfoLookupableImpl extends LookupableImpl {
	private static final long serialVersionUID = 1L;	
	
    private transient CluService cluService;
    private ProgramService programService;

    public enum QueryParamEnum {
        ID("lu.queryParam.luOptionalId","id"),
        TITLE("lu.queryParam.luOptionalLongName", "shortTitle"),
        CODE("lu.queryParam.luOptionalCode", "code"),
        DESCRIPTION("lu.queryParam.luOptionalDescr", "descr");

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
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        List <CoreProgramInfo> coreProgramInfoList = new ArrayList<CoreProgramInfo>();
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
            String fieldValue = fieldValues.get(qpEnum.getFieldValue());
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
            CoreProgramInfo coreProgramInfo = null;
            if (searchResult.getRows().size() > 0) {
                for(SearchResultRowInfo srrow : searchResult.getRows()){
                    coreProgramInfo = new CoreProgramInfo();
                    List<SearchResultCellInfo> srCells = srrow.getCells();
                    if(srCells != null && srCells.size() > 0){
                        for(SearchResultCellInfo srcell : srCells){
                            if (srcell.getKey().equals("lu.resultColumn.cluId")) {
                                coreProgramInfo.setId(srcell.getValue());
//                               CoreProgramInfo coreProgramInfo new  = getProgramService().getpget(courseId, ContextUtils.getContextInfo());
//                                coreProgramInfoList.add(course);
                            }
                            else if(srcell.getKey().equals("lu.resultColumn.luOptionalLongName")) {
                                coreProgramInfo.setLongTitle(srcell.getValue());
                            }
                            else if(srcell.getKey().equals("lu.resultColumn.luOptionalShortName")) {
                                coreProgramInfo.setShortTitle(srcell.getValue());
                            }
                            else if(srcell.getKey().equals("lu.resultColumn.luOptionalDescr")) {
                                RichTextInfo richTextInfo = new RichTextInfo();
                                richTextInfo.setPlain(srcell.getValue());
                                coreProgramInfo.setDescr(richTextInfo);
                            }
                            else if(srcell.getKey().equals("lu.resultColumn.luOptionalState")) {
                                coreProgramInfo.setStateKey(srcell.getValue());
                            }
                            else if(srcell.getKey().equals("lu.resultColumn.luOptionalCode")) {
                                coreProgramInfo.setCode(srcell.getValue());
                            }

                        }
                    }
                    coreProgramInfoList.add(coreProgramInfo);
                }

            }

            return coreProgramInfoList;
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
