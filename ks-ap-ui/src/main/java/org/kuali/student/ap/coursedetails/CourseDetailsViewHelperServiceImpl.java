package org.kuali.student.ap.coursedetails;

import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.coursesearch.dataobject.ActivityOfferingDetailsWrapper;
import org.kuali.student.ap.coursesearch.dataobject.CourseOfferingDetailsWrapper;
import org.kuali.student.ap.coursesearch.dataobject.CourseTermDetailsWrapper;
import org.kuali.student.ap.coursesearch.dataobject.FormatOfferingDetailsWrapper;
import org.kuali.student.ap.coursesearch.form.CourseSectionDetailsForm;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 6/9/14
 * Time: 1:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class CourseDetailsViewHelperServiceImpl extends ViewHelperServiceImpl implements CourseDetailsViewHelperService {

    @Override
    public void loadCourseSectionDetails(UifFormBase form, String courseId) {
        load((CourseSectionDetailsForm) form, courseId);
    }
//
    private void load(CourseSectionDetailsForm form, String courseId) {
        CourseInfo courseInfo= KsapFrameworkServiceLocator.getCourseHelper().getCourseInfo(courseId);
        form.setCourseTitle(courseInfo.getCourseTitle());
        form.setCourseCode(courseInfo.getCode());
        List<String> termIds = KsapFrameworkServiceLocator.getCourseHelper().getScheduledTermsForCourse(courseInfo);
        form.setCourseTermDetailsWrappers(getScheduledTerms(termIds, courseId));
    }

    private List<CourseTermDetailsWrapper> getScheduledTerms(List<String> scheduledTermsList, String courseId) {

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

            List<String> courseIds = new ArrayList<String>();
            courseIds.add(courseId);
            Map<String, List<CourseOfferingDetailsWrapper>> courseOfferingsByTerm = processCourseOfferingsByTerm(courseIds, terms);


            for (Term scheduledTermId : scheduledTermsListSorted) {

                CourseTermDetailsWrapper courseTerm = new CourseTermDetailsWrapper();
                courseTerm.setTermName(scheduledTermId.getName());
                courseTerm.setTermId(scheduledTermId.getId());
                courseTerm.setCourseOfferingDetailsWrappers(courseOfferingsByTerm.get(scheduledTermId.getId()));


                courseTermDetailsList.add(courseTerm);
            }
        }
        return courseTermDetailsList;
    }

    private Map<String, List<CourseOfferingDetailsWrapper>> processCourseOfferingsByTerm(List<String> courseIds, List<Term> terms) {
        List<CourseOfferingInfo> courseOfferings = KsapFrameworkServiceLocator.getCourseHelper().getCourseOfferingsForCoursesAndTerms(courseIds, terms);
        Map<String, List<CourseOfferingDetailsWrapper>> map = new HashMap<String, List<CourseOfferingDetailsWrapper>>();

        for (CourseOfferingInfo offering : courseOfferings) {
            String termId = offering.getTermId();
            List<CourseOfferingDetailsWrapper> offeringsByTerm = map.get(termId);
            if (offeringsByTerm == null)
                offeringsByTerm = new ArrayList<CourseOfferingDetailsWrapper>();

            CourseOfferingDetailsWrapper courseOfferingDetailsWrapper = new CourseOfferingDetailsWrapper(offering);
            try {
                List<FormatOfferingInfo> formatOfferingList = KsapFrameworkServiceLocator.getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingDetailsWrapper.getCourseOfferingId(), KsapFrameworkServiceLocator.getContext().getContextInfo());
                List<FormatOfferingDetailsWrapper> formatOfferingDetailsWrappers = new ArrayList<FormatOfferingDetailsWrapper>();

                Map<String, List<ActivityOfferingDetailsWrapper>> aosByFormat = getAOData(offering.getId());

                for (FormatOfferingInfo formatOffering : formatOfferingList) {
                    FormatOfferingDetailsWrapper formatOfferingDetailsWrapper = new FormatOfferingDetailsWrapper(formatOffering);

                    formatOfferingDetailsWrapper.setActivityOfferingDetailsWrappers(aosByFormat.get(formatOffering.getId()));

                    formatOfferingDetailsWrappers.add(formatOfferingDetailsWrapper);
                }

                courseOfferingDetailsWrapper.setFormatOfferingDetailsWrappers(formatOfferingDetailsWrappers);
            } catch (DoesNotExistException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InvalidParameterException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (MissingParameterException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (OperationFailedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (PermissionDeniedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }


            offeringsByTerm.add(courseOfferingDetailsWrapper);
            map.put(termId, offeringsByTerm);
        }
        return map;
    }

    private Map<String, List<ActivityOfferingDetailsWrapper>> getAOData(String courseOfferingId) {
        Map<String, List<ActivityOfferingDetailsWrapper>> aoMapByFormatOffering = new HashMap<String, List<ActivityOfferingDetailsWrapper>>();
        List<ActivityOfferingInfo> activityOfferings = null;
        try {
            activityOfferings = KsapFrameworkServiceLocator.getCourseOfferingService().getActivityOfferingsByCourseOffering(courseOfferingId, KsapFrameworkServiceLocator.getContext().getContextInfo());

        } catch (DoesNotExistException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvalidParameterException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (MissingParameterException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (OperationFailedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (PermissionDeniedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        for (ActivityOfferingInfo activityOffering : activityOfferings) {
            ActivityOfferingDetailsWrapper wrapper = new ActivityOfferingDetailsWrapper(activityOffering);
            String formatOfferingId = activityOffering.getFormatOfferingId();
            List<ActivityOfferingDetailsWrapper> aosByFormat = aoMapByFormatOffering.get(formatOfferingId);
            if (aosByFormat == null) {
                aosByFormat = new ArrayList<ActivityOfferingDetailsWrapper>();
            }
            aosByFormat.add(wrapper);
            aoMapByFormatOffering.put(formatOfferingId, aosByFormat);
        }
        return aoMapByFormatOffering;
    }
}
