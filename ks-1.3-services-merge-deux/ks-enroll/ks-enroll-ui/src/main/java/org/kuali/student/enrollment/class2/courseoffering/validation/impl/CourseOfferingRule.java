package org.kuali.student.enrollment.class2.courseoffering.validation.impl;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.hibernate.mapping.Index;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
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
 * @deprecated This class is leftover from Core Slice. Delete when no longer needed or un deprecate if needed.
 */
@Deprecated
public class CourseOfferingRule extends MaintenanceDocumentRuleBase {
     private static final String COURSE_CODE_PROPERTY_PATH = "document.newMaintainableObject.dataObject.courseOfferingCode";

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
             GlobalVariables.getMessageMap().putError(
                COURSE_CODE_PROPERTY_PATH,"error.enroll.courseoffering.courseOfferingCode.notExists");
             isValid = false;
        }
        else{
             courseOfferingInfo.setCourseId(courseId);
        }

        // need to verify principalIds again since the user might modify the input after add it to the collection
        List<OfferingInstructorInfo> instructors = courseOfferingInfo.getInstructors();
        int index = 0;
        for(OfferingInstructorInfo instructor : instructors){
            String principalId = instructor.getPersonId();
            if (getIdentityService().getPrincipal(principalId) == null) {
                    GlobalVariables.getMessageMap().putError(
                     "document.newMaintainableObject.dataObject.instructors["+index+"]", "error.enroll.courseoffering.instructors.notExists", "The instructor: "+principalId+" does not exist.");
                isValid = false;
            }
            index++;
        }
        isValid &= super.processCustomRouteDocumentBusinessRules(document);
        isValid &= GlobalVariables.getMessageMap().hasNoErrors();

        return isValid;
    }

/*
    @Override
    public boolean processCustomAddCollectionLineBusinessRules(MaintenanceDocument document, String collectionName,
            PersistableBusinessObject line) {
        // need to verify principalId when add it to the collection
        List<OfferingInstructorInfo> instructors = courseOfferingInfo.getInstructors();
        int index = 0;
        for(OfferingInstructorInfo instructor : instructors){
            String principalId = instructor.getPersonId();
            if (getIdentityService().getPrincipal(principalId) == null) {
                System.out.println(">>>Error: Fail to retrieve an instructor with instructorId equal to "+principalId);
                    GlobalVariables.getMessageMap().putError(
                     "instructors("+principalId+")", "error.enroll.courseoffering.instructors.notExists", "The instructor: "+principalId+" does not exist.");
                isValid = false;
            }
            index++;
        }
    }
*/

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
