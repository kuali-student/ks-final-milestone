package org.kuali.student.ap.coursesearch.form;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.coursedetails.CourseDetailsHelper;
import org.kuali.student.ap.coursesearch.dataobject.CourseTermDetailsWrapper;
import org.kuali.student.ap.coursesearch.util.CollectionListPropertyEditorHtmlListType;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 6/6/14
 * Time: 8:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class CourseSectionDetailsForm extends UifFormBase {

    private String courseCode;
    private String courseTitle;
    private List<CourseTermDetailsWrapper> courseTermDetailsWrappers;

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public List<CourseTermDetailsWrapper> getCourseTermDetailsWrappers() {
        return courseTermDetailsWrappers;
    }

    public void setCourseTermDetailsWrappers(List<CourseTermDetailsWrapper> courseTermDetailsWrappers) {
        this.courseTermDetailsWrappers = courseTermDetailsWrappers;
    }

    public void load(String courseId) {
        CourseInfo courseInfo= KsapFrameworkServiceLocator.getCourseHelper().getCourseInfo(courseId);
        this.setCourseTitle(courseInfo.getCourseTitle());
        this.setCourseCode(courseInfo.getCode());
        List<String> termIds = KsapFrameworkServiceLocator.getCourseHelper().getScheduledTermsForCourse(courseInfo);
        this.setCourseTermDetailsWrappers(getScheduledTerms(termIds));
    }

    public List<CourseTermDetailsWrapper> getScheduledTerms(List<String> scheduledTermsList) {

        List<CourseTermDetailsWrapper> courseTermDetailsList = new ArrayList<CourseTermDetailsWrapper>();

        //Return only the scheduled terms
        if (scheduledTermsList != null && scheduledTermsList.size() > 0) {

            List<TermInfo> scheduledTerms;
            try {
                scheduledTerms = KsapFrameworkServiceLocator.getAcademicCalendarService().getTermsByIds(scheduledTermsList, KsapFrameworkServiceLocator.getContext().getContextInfo());
            } catch (DoesNotExistException e) {
                throw new IllegalArgumentException("ATP lookup error", e);
            } catch (InvalidParameterException e) {
                throw new IllegalArgumentException("ATP lookup error", e);
            } catch (MissingParameterException e) {
                throw new IllegalArgumentException("ATP lookup error", e);
            } catch (OperationFailedException e) {
                throw new IllegalStateException("ATP lookup error", e);
            } catch (PermissionDeniedException e) {
                throw new IllegalStateException("ATP lookup error", e);
            }

            //sort scheduledTermsListIds
            List<Term> terms = new ArrayList<Term>(scheduledTerms);
            List<Term> scheduledTermsListSorted = KsapFrameworkServiceLocator.getTermHelper().sortTermsByStartDate(terms, true);

            Integer displayLimit = Integer.valueOf(ConfigContext.getCurrentContextConfig().getProperty("ks.ap.search.terms.scheduled.limit"));

            //list greater than displayLimit, truncate
            if ( scheduledTermsListSorted.size() >  displayLimit )
                scheduledTermsListSorted = scheduledTermsListSorted.subList(0, displayLimit);

            for (Term scheduledTermId : scheduledTermsListSorted) {

                CourseTermDetailsWrapper courseTerm = new CourseTermDetailsWrapper();
                courseTerm.setTermName(scheduledTermId.getName());
                courseTerm.setTermId(scheduledTermId.getId());
                courseTermDetailsList.add(courseTerm);
            }
        }
        return courseTermDetailsList;
    }
}