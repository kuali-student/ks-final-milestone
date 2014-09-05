package org.kuali.student.ap.coursesearch.service.impl;

import org.apache.cxf.common.util.StringUtils;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.ap.coursesearch.CreditsFormatter;
import org.kuali.student.ap.coursesearch.dataobject.CourseDetailsPopoverWrapper;
import org.kuali.student.ap.coursesearch.dataobject.CourseDetailsWrapper;
import org.kuali.student.ap.coursesearch.util.CourseDetailsUtil;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.CourseSearchConstants;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.util.KsapHelperUtil;
import org.kuali.student.ap.utils.CourseLinkBuilder;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.core.search.infc.SearchResultRow;
import org.kuali.student.r2.lum.course.infc.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Inquiry Helper for the CourseDetails-InquiryView
 * @see org.kuali.rice.kns.inquiry.KualiInquirableImpl
 */
public class CourseDetailsInquiryHelperImpl2 extends KualiInquirableImpl {

    private static final long serialVersionUID = 4933435913745621395L;

    private static final Logger LOG = LoggerFactory.getLogger(CourseDetailsInquiryHelperImpl2.class);

    private final String COURSE_DETAILS_INQUIRY_VIEW = "CourseDetails-InquiryView";
    private final String COURSE_DETAILS_POPOVER_INQUIRY_VIEW = "CourseDetailsPopover-InquiryView";

    /**
     * @see org.kuali.rice.kns.inquiry.KualiInquirableImpl#retrieveDataObject(java.util.Map)
     */
    @Override
    public Object retrieveDataObject(@SuppressWarnings("rawtypes") Map fieldValues) {
        String studentId = KsapFrameworkServiceLocator.getUserSessionHelper().getStudentId();
        String viewId = (String) fieldValues.get(PlanConstants.PARAM_VIEW_ID);
        String courseId = (String) fieldValues.get(PlanConstants.PARAM_COURSE_ID);
        String termId = (String) fieldValues.get(PlanConstants.PARAM_TERM_ID);
        boolean loadActivityOfferings = fieldValues.get(PlanConstants.PARAM_OFFERINGS_FLAG) != null
                && Boolean.valueOf(fieldValues.get(PlanConstants.PARAM_OFFERINGS_FLAG).toString());

        if(viewId.equals(COURSE_DETAILS_INQUIRY_VIEW)){
            return retrieveCourseDetails(courseId,termId,studentId,loadActivityOfferings);
        }else if(viewId.equals(COURSE_DETAILS_POPOVER_INQUIRY_VIEW)){
            return retrieveCoursePopoverDetails(courseId);
        }
        return null;
    }

    /**
     * Retreive and compile data needed for the page into a single object
     *
     * @param courseId - Id of the course being displayed
     * @param termId -
     * @param studentId -
     * @param loadActivityOffering -
     * @return Compiled data object for inquiry page
     */
    public CourseDetailsWrapper retrieveCourseDetails(String courseId, String termId, String studentId,
                                                      boolean loadActivityOffering) {
        final Course course = KsapFrameworkServiceLocator.getCourseHelper().getCurrentVersionOfCourse(courseId);

        CourseDetailsWrapper courseDetails = retrieveCourseSummary(course);

        return courseDetails;
    }

    /**
     * Retrieve and compile data needed for the course popover on the course details page.
     *
     * @param courseId - Id of the course being displayed in popover
     * @return Compiled data object for popover
     */
    public CourseDetailsPopoverWrapper retrieveCoursePopoverDetails(String courseId){
        final Course course = KsapFrameworkServiceLocator.getCourseHelper().getCurrentVersionOfCourse(courseId);

        if(course == null) return null;

        CourseDetailsPopoverWrapper courseDetails = new CourseDetailsPopoverWrapper();

        courseDetails.setCourseId(course.getId());
        courseDetails.setCourseCode(course.getCode());
        courseDetails.setCourseCredits(CreditsFormatter.formatCredits(course));
        courseDetails.setCourseTitle(course.getCourseTitle());

        // courseDetails.setCourseRequisites(CourseDetailsUtil.getCourseRequisites(course));
        courseDetails.setCourseRequisitesMap(CourseDetailsUtil.getCourseRequisitesMap(course));

        // Load Term information
        courseDetails.setScheduledTerms(KsapFrameworkServiceLocator.getCourseHelper()
                .getScheduledTermsForCourse(course));
        courseDetails.setProjectedTerms(KsapFrameworkServiceLocator.getCourseHelper().getProjectedTermsForCourse(course));

        // Load Last Offered Term information if course is not scheduled
        if(courseDetails.getScheduledTerms().isEmpty()){
            Term lastOfferedTerm = KsapFrameworkServiceLocator.getCourseHelper().getLastOfferedTermForCourse(course);
            if (lastOfferedTerm != null){
                courseDetails.setLastOffered(lastOfferedTerm.getName());
            }
            else {
                // If no last offered is found set as null
                courseDetails.setLastOffered(null);
            }
        }else{
            courseDetails.setLastOffered(null);
        }

        //Load plan status information
        List<PlanItem> planItems = KsapFrameworkServiceLocator.getPlanHelper().loadStudentsPlanItemsForCourse(course);
        courseDetails.setPlanStatusMessage(KsapFrameworkServiceLocator.getPlanHelper().createPlanningStatusMessages(planItems));
        courseDetails.setBookmarkStatusMessage(KsapFrameworkServiceLocator.getPlanHelper().createBookmarkStatusMessages(planItems));

        return courseDetails;
    }

    /**
     * Populates course details with catalog information (title, id, code, description) and other summary information
     * @param courseId - Id of the course to retrieve
     * @return
     */
    public CourseDetailsWrapper retrieveCourseSummaryById(String courseId) {
        Course course = KsapFrameworkServiceLocator.getCourseHelper().getCurrentVersionOfCourse(courseId);
        return retrieveCourseSummary(course);
    }

    /**
     * Populates course details with catalog information (title, id, code, description) and other summary information
     *
     * @param course
     * @return
     */
    public CourseDetailsWrapper retrieveCourseSummary(Course course) {

        if (null == course) {
            return null;
        }

        CourseDetailsWrapper courseDetails = new CourseDetailsWrapper();

        // Load basic information
        courseDetails.setCourseId(course.getId());
        courseDetails.setCourseCode(course.getCode());
        courseDetails.setCourseCredits(CreditsFormatter.formatCredits(course));
        courseDetails.setCourseTitle(course.getCourseTitle());
        String subjectCode = course.getSubjectArea().trim();
        Map<String, String> subjectAreas = KsapFrameworkServiceLocator.getOrgHelper().getTrimmedSubjectAreas();
        courseDetails.setSubject(subjectAreas.get(subjectCode));

        // Load formated information
        courseDetails.setCourseDescription(getCourseDescription(course));
        courseDetails.setCourseRequisites(CourseDetailsUtil.getCourseRequisites(course));
        courseDetails.setCourseRequisitesMap(CourseDetailsUtil.getCourseRequisitesMap(course));
        courseDetails.setCourseGenEdRequirements(getCourseGenEdRequirements(course));

        // Load Term information
        courseDetails.setScheduledTerms(KsapFrameworkServiceLocator.getCourseHelper()
                .getScheduledTermsForCourse(course));
        courseDetails.setProjectedTerms(KsapFrameworkServiceLocator.getCourseHelper().getProjectedTermsForCourse(course));

        // Load Last Offered Term information if course is not scheduled
        if(courseDetails.getScheduledTerms().isEmpty()){
            Term lastOfferedTerm = KsapFrameworkServiceLocator.getCourseHelper().getLastOfferedTermForCourse(course);
            if (lastOfferedTerm != null){
                courseDetails.setLastOffered(lastOfferedTerm.getName());
            }
            else {
                // If no last offered is found set as null
                courseDetails.setLastOffered(null);
            }
        }else{
            courseDetails.setLastOffered(null);
        }

        //Load plan status information
        List<PlanItem> planItems = KsapFrameworkServiceLocator.getPlanHelper().loadStudentsPlanItemsForCourse(course);
        courseDetails.setPlannedMessage(KsapFrameworkServiceLocator.getPlanHelper().createPlanningStatusMessages(planItems));
        courseDetails.setBookmarkMessage(KsapFrameworkServiceLocator.getPlanHelper().createBookmarkStatusMessages(planItems));
        courseDetails.setBookmarked(KsapFrameworkServiceLocator.getCourseHelper().isCourseBookmarked(course, planItems));

        return courseDetails;
    }

    /**
     * Retrieve and format the Course description to be displayed on the page
     *
     * @param course - Course that is being displayed
     * @return Formatted course description
     */
    protected String getCourseDescription(Course course){
        String description = "";
        RichText richTextDescription = course.getDescr();
        if(!StringUtils.isEmpty(richTextDescription.getFormatted())){
            description = richTextDescription.getFormatted();
        }else if(!StringUtils.isEmpty(richTextDescription.getPlain())){
            description = richTextDescription.getPlain();
        }

        description = CourseLinkBuilder.makeLinks(description,KsapFrameworkServiceLocator.getContext().getContextInfo());

        return description;
    }

    /**
     * Retrieve and format the list of general education requirements to be displayed on the page
     *
     * @param course - Course that is being displayed
     * @return Formatted list of general education requirements
     */
    protected List<String> getCourseGenEdRequirements(Course course){
        List<String> courseGenEdRequirements = new ArrayList<String>();

        // Create search for requirements
        String independentVersionId = course.getVersion().getVersionIndId();
        SearchRequestInfo request = new SearchRequestInfo(
                CourseSearchConstants.KSAP_COURSE_SEARCH_GENERAL_EDUCATION_BY_COURSEID_KEY);
        request.addParam(CourseSearchConstants.SearchParameters.VERSION_IND_ID, independentVersionId);

        // Search for the requirements
        SearchResult result;
        try {
            result = KsapFrameworkServiceLocator.getSearchService().search(
                    request,
                    KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException(
                    "Invalid course ID or CLU lookup error", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException(
                    "Invalid course ID or CLU lookup error", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("CLU lookup error", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalArgumentException("CLU lookup error", e);
        }

        for (SearchResultRow row : result.getRows()) {
            String code = KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns.CLU_SET_ATTR_VALUE);
            String name = KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns.CLU_SET_NAME);
            courseGenEdRequirements.add(name+" ("+code+")");
        }

        return courseGenEdRequirements;
    }
}
