package org.kuali.student.enrollment.class2.courseoffering.validation.impl;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.document.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.common.search.dto.*;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.lu.service.LuServiceConstants;


/**
 * Created by IntelliJ IDEA.
 * User: huangb
 * Date: 9/20/11
 * Time: 6:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class CourseOfferingRule extends MaintenanceDocumentRuleBase {
     private static final String COURSE_ID_PROPERTY_PATH = "courseId";

     private transient LuService luService;
     private transient IdentityService identityService;

    /**
     * @see org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase#processCustomRouteDocumentBusinessRules(org.kuali.rice.kns.document.MaintenanceDocument)
     */
    @Override
    protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
        boolean isValid = true;

        // retrieve and verify a courseId based on course Code, and set it to the courseOfferingInfo.
        CourseOfferingInfo courseOfferingInfo = (CourseOfferingInfo)document.getDocumentDataObject();
        String courseCode =  courseOfferingInfo.getCourseOfferingCode();
        String courseId = retrieveCourseIdFromCourseCode(courseCode);
        if (courseId == null) {
            System.out.println(">>>Error: Fail to retrieve the courseId with courseCode equal to "+courseCode);
//               GlobalVariables.getMessageMap().putError(
//                    COURSE_ID_PROPERTY_PATH,"??ERROR_KEY??", "Error: Failed to retrieve a course with courseId equal to "+courseId);
             isValid = false;
        }
        else{
             courseOfferingInfo.setCourseId(courseId);
        }

        // need to verify principalId
        List<OfferingInstructorInfo> instructors = courseOfferingInfo.getInstructors();
        for(OfferingInstructorInfo instructor : instructors){
            String principalId = instructor.getPersonId();
            if (getIdentityService().getPrincipal(principalId) == null) {
                System.out.println(">>>Error: Fail to retrieve an instructor with instructorId equal to "+principalId);
////               GlobalVariables.getMessageMap().putError(
////                    COURSE_ID_PROPERTY_PATH,"??ERROR_KEY??", "Error: Failed to retrieve a course with courseId equal to "+courseId);
                isValid = false;
            }
        }
        isValid &= super.processCustomRouteDocumentBusinessRules(document);
        isValid &= GlobalVariables.getMessageMap().hasNoErrors();

        return isValid;
    }

    //Note: here I am using r1 LuService implementation!!!
    protected LuService getLuService() {
        if(luService == null) {
            luService = (LuService)GlobalResourceLoader.getService(new QName(LuServiceConstants.LU_NAMESPACE,"LuService"));
        }
        return this.luService;
    }

    protected IdentityService getIdentityService() {
		if ( identityService == null ) {
			identityService = KimApiServiceLocator.getIdentityService();
		}
		return identityService;
	}

    /*
     * return courseId for a specified courseCode
     */
    private String retrieveCourseIdFromCourseCode (String courseCode){
        Map<String,String> fieldValues = new HashMap<String, String>();
        fieldValues.put("code",courseCode);
        List courseIds = getSearchResults(null, fieldValues, false);
        if(courseIds.size() != 1){
            return null;
        }
        else{
            return (String) courseIds.get(0);
        }
    }
    /*
     * Use LuService for a general search to retrieve the courseId based on a specified course code in the search criteria
     */
    private List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        List <String> courseIds = new ArrayList<String>();
        List<SearchParam> searchParams = new ArrayList<SearchParam>();
        SearchParam qpv1 = new SearchParam();
        qpv1.setKey("lu.queryParam.luOptionalType");
        qpv1.setValue("kuali.lu.type.CreditCourse");
        searchParams.add(qpv1);
        SearchParam qpv2 = new SearchParam();
        qpv1.setKey("lu.queryParam.luOptionalCode");
        qpv1.setValue(fieldValues.get("code"));
        searchParams.add(qpv2);

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
                                String courseId = srcell.getValue();
                                courseIds.add(courseId);
                            }
                        }
                    }
                }
            }

            return courseIds;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
