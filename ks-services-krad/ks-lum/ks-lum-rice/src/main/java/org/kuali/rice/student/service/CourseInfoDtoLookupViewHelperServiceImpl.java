package org.kuali.rice.student.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;

import org.springframework.web.util.HtmlUtils;
import org.apache.commons.lang.StringUtils;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kns.authorization.BusinessObjectRestrictions;
import org.kuali.rice.kns.uif.service.impl.LookupViewHelperServiceImpl;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.student.dto.CourseInfoDto;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultCell;
import org.kuali.student.core.search.dto.SearchResultRow;
import org.kuali.student.lum.lu.service.LuService;

public class CourseInfoDtoLookupViewHelperServiceImpl extends LookupViewHelperServiceImpl {
    private LuService luService = null;
    /**
     * This overridden method ...
     * 
     * @see org.kuali.rice.kns.uif.service.impl.LookupViewHelperServiceImpl#performSearch(java.util.Map, boolean)
     */
    @Override
    public Collection<?> performSearch(Map<String, String> criteriaFieldsForLookup, boolean bounded) {
        String courseTitle = criteriaFieldsForLookup.get("courseTitle");
        String courseCode = criteriaFieldsForLookup.get("courseCode");
        String subjectArea = criteriaFieldsForLookup.get("subjectArea");
        
        List<SearchParam> queryParamValues = new ArrayList<SearchParam>();
        SearchParam searchParam = new SearchParam();
        searchParam.setKey("lu.queryParam.luOptionalLongName");
        searchParam.setValue(courseTitle);
        queryParamValues.add(searchParam);
       
        searchParam = new SearchParam();
        searchParam.setKey("lu.queryParam.luOptionalCode");
        searchParam.setValue(courseCode);
        queryParamValues.add(searchParam);
        
        searchParam = new SearchParam();
//        searchParam.setKey("lu.queryParam.luOptionalStudySubjectArea");
        searchParam.setKey("lu.queryParam.luOptionalDivision");
        searchParam.setValue(subjectArea);
        queryParamValues.add(searchParam);
        
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setSearchKey("lu.search.generic");
        searchRequest.setParams(queryParamValues);
        try {
                getTheLuService();
                SearchResult results = luService.search(searchRequest);
                List<CourseInfoDto> courseLookupResults = new ArrayList<CourseInfoDto>();
                List<SearchResultRow> rows = results.getRows();
                for(SearchResultRow row : rows) {
                    CourseInfoDto courseLookupResult = new CourseInfoDto();
                    List<SearchResultCell> cells = row.getCells();                    
                    for(SearchResultCell cell : cells) {                        
                        if(cell.getKey().equals("lu.resultColumn.cluId")) {
                            courseLookupResult.setCourseId(cell.getValue());
                        }
                        else if(cell.getKey().equals("lu.resultColumn.luOptionalLongName")) {
                            courseLookupResult.setCourseTitle(cell.getValue());
                        }
                        else if(cell.getKey().equals("lu.resultColumn.luOptionalCode")) {
                            courseLookupResult.setCourseCode(cell.getValue());
                        }
                        else if(cell.getKey().equals("lu.resultColumn.luOptionalDivision")) {
                            courseLookupResult.setSubjectArea(cell.getValue());
                        }                        
                    }
                    courseLookupResults.add(courseLookupResult);
                }
                formResultTable(courseLookupResults);
                return courseLookupResults;
            
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    protected void formResultTable(List<CourseInfoDto> displayList){
//      List<String> pkNames = getDataObjectMetaDataService().listPrimaryKeyFieldNames(getDataObjectClass());
        List pkNames = new ArrayList <String>();
        pkNames.add("courseId");
        Person user = GlobalVariables.getUserSession().getPerson();
        for (Object object : displayList) {
            if (isResultReturnable(object)) {
                setAtLeastOneRowReturnable(true);
            }
            BusinessObjectRestrictions dataObjectRestrictions = getBusinessObjectAuthorizationService().getLookupResultRestrictions(object, user);
            String actionUrls = getActionUrls(object, pkNames, dataObjectRestrictions);
            if (StringUtils.isNotBlank(HtmlUtils.htmlUnescape(actionUrls).replace('\u00A0', '\u0020'))) {
                setAtLeastOneRowHasActions(true);
            }
            if (isAtLeastOneRowReturnable() && isAtLeastOneRowHasActions()) {
                break;
            }
        }
    }
    protected LuService getTheLuService() {
        if (this.luService == null) {
            this.luService = (LuService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/lu","LuService")); 
        }
        return this.luService;
    }
    
}
