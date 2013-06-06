package org.kuali.student.cm.course.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.cm.course.form.CourseJointInfoDisplay;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

public class CourseJointInfoLookupableImpl extends LookupableImpl {

	private static final long serialVersionUID = 730938705006420306L;
	
	private CluService cluService;
	
	@Override
	protected List<?> getSearchResults(LookupForm form,
			Map<String, String> searchCriteria, boolean unbounded) {
		List<CourseJointInfoDisplay> courseJointInfoDisplays = new ArrayList<CourseJointInfoDisplay>();
		
        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        String courseTitle = searchCriteria.get("courseTitle");
        String subjectArea = searchCriteria.get("courseCode");
        String description = searchCriteria.get("descr.plain");
        
        if (StringUtils.isNotBlank(courseTitle)) {
            SearchParamInfo courseTitleParam = new SearchParamInfo();
            courseTitleParam.setKey("lu.queryParam.luOptionalLongName");
            courseTitleParam.getValues().add(courseTitle);
            queryParamValueList.add(courseTitleParam);
        }
        else if (StringUtils.isNotBlank(subjectArea)){
            SearchParamInfo courseCodeParam = new SearchParamInfo();
            courseCodeParam.setKey("lu.queryParam.luOptionalCode");
            courseCodeParam.getValues().add(subjectArea);
            queryParamValueList.add(courseCodeParam);
        }
        else if (StringUtils.isNotBlank(description) && !description.isEmpty()){
            SearchParamInfo descriptionParam = new SearchParamInfo();
            descriptionParam.setKey("lu.queryParam.luOptionalDescr");
            descriptionParam.getValues().add(description);
            queryParamValueList.add(descriptionParam);
        }
        
        SearchParamInfo typeParam = new SearchParamInfo();
        typeParam.setKey("lu.queryParam.luOptionalType");
        typeParam.getValues().add("kuali.lu.type.CreditCourse");
        
        SearchParamInfo stateParam = new SearchParamInfo();
        stateParam.setKey("lu.queryParam.luOptionalState");
        List<String> states = new ArrayList<String>();
        states.add("Draft");
        states.add("Submitted");
        states.add("Withdrawn");
        states.add("Approved");
        states.add("Active");
        stateParam.setValues(states);
        queryParamValueList.add(stateParam);

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("lu.search.generic");
        searchRequest.setParams(queryParamValueList);
        SearchResultInfo clus = null;
        try {
            clus = getCluService().search(searchRequest, ContextUtils.getContextInfo());
            for (SearchResultRowInfo result : clus.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                CourseJointInfoDisplay courseJointInfoDisplay = new CourseJointInfoDisplay();
                for (SearchResultCellInfo cell : cells) {
                    if ("lu.resultColumn.cluId".equals(cell.getKey())) {
                    	courseJointInfoDisplay.setCourseId(cell.getValue());
                    } else if ("lu.resultColumn.luOptionalLongName".equals(cell.getKey())) {
                    	courseJointInfoDisplay.setName(cell.getValue());
                    } else if ("lu.resultColumn.luOptionalCode".equals(cell.getKey())) {
                    	courseJointInfoDisplay.setCourseCode(cell.getValue());
                    	//courseJointInfo.setSubjectArea(subjectCode);
                        //courseJointInfo.setCourseNumberSuffix(subjectCode);
                    }
                    else if ("lu.resultColumn.luOptionalDescr".equals(cell.getKey())) {
                    	RichTextInfo descr = new RichTextInfo();
                    	descr.setPlain(cell.getValue());
                    	courseJointInfoDisplay.setDescr(descr);
                    }
                }
                courseJointInfoDisplays.add(courseJointInfoDisplay);
            }
        } catch (Exception e) {

        }
        return courseJointInfoDisplays;
	}	
	
	private CluService getCluService() {
		if (cluService == null) {
			cluService = GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE, CluService.class.getSimpleName()));
		}
		return cluService;
	}

}
