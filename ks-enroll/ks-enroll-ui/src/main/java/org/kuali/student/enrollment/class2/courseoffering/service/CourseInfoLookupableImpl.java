package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.student.common.search.dto.*;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.course.service.CourseServiceConstants;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.lu.service.LuServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CourseInfoLookupableImpl extends LookupableImpl {
	private static final long serialVersionUID = 1L;	
	
    private transient LuService luService;
    private transient CourseService courseService;
    
	 @Override
	 protected List<?> getSearchResultsForEBO(Map<String, String> fieldValues, boolean unbounded) {
		 	List <CourseInfo> courseInfoList = new ArrayList<CourseInfo>();
		    String courseId = null;
	        List<SearchParam> searchParams = new ArrayList<SearchParam>();
	        SearchParam qpv1 = new SearchParam();
	        qpv1.setKey("lu.queryParam.luOptionalType");
//	        qpv1.setValue(ProgramClientConstants.CORE_PROGRAM);
//	        qpv1.setValue("kuali.lu.type.CoreProgram");
	        qpv1.setValue("kuali.lu.type.CreditCourse");
	        searchParams.add(qpv1);
	        
			SearchRequest searchRequest = new SearchRequest();
	        searchRequest.setParams(searchParams);
			searchRequest.setSearchKey("lu.search.mostCurrent.union");	
			
	        

			try {
				SearchResult searchResult = getLuService().search(searchRequest);

		        if (searchResult.getRows().size() > 0) {
		            for(SearchResultRow srrow : searchResult.getRows()){
		                List<SearchResultCell> srCells = srrow.getCells();
		                if(srCells != null && srCells.size() > 0){
		                    for(SearchResultCell srcell : srCells){
		                        if (srcell.getKey().equals("lu.resultColumn.cluId")) {
		                        	courseId = srcell.getValue();
		                        	CourseInfo course = getCourseService().getCourse(courseId);
		                        	courseInfoList.add(course);
		                        }
		                    }
		                }
		            }
		        }

		        return courseInfoList;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}	 
		 
			 
	 }
	 
	    //Note: here I am using r1 LuService implementation!!!
	    protected LuService getLuService() {
	        if(luService == null) {
	        	luService = (LuService)GlobalResourceLoader.getService(new QName(LuServiceConstants.LU_NAMESPACE,"LuService"));
	        }
	        return this.luService;
	    }
	    
	    protected CourseService getCourseService() {
	        if(courseService == null) {
	        	courseService = (CourseService)GlobalResourceLoader.getService(new QName(CourseServiceConstants.COURSE_NAMESPACE,"CourseService"));
	        }
	        return this.courseService;
	    }
}
